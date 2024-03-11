package com.zzz.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zzz.producer.config.ConfirmService;
import com.zzz.producer.entity.AccountInfo;
import com.zzz.producer.model.AccountChangeEvent;
import com.zzz.producer.service.AccountInfoService;
import com.zzz.producer.mapper.AccountInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangzhongzhen
 * @description 针对表【account_info】的数据库操作Service实现
 * @createDate 2024-03-10 22:20:18
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConfirmService confirmService;

    @Override
    @Transactional
    public void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent) throws Exception {

        //本地扣减金额
        int i = accountInfoMapper.updateAccountBalance(accountChangeEvent.getAccountNo(), accountChangeEvent.getAmount());
        if (i != 1) {
            throw new Exception("更新失败");
        }

        //记录本地事务日志表
        int j = accountInfoMapper.addAccountLog(accountChangeEvent.getAccountNo(), accountChangeEvent.getAmount(), accountChangeEvent.getTxNo());
        if (j != 1) {
            throw new Exception("插入失败");
        }

        //发送消息到队列
        log.info("发送消息：" + JSONObject.toJSONString(accountChangeEvent));
        rabbitTemplate.setConfirmCallback(confirmService);
        rabbitTemplate.setReturnsCallback(confirmService);
        rabbitTemplate.convertAndSend("account_exchange", "account1", JSONObject.toJSONString(accountChangeEvent), message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        }, new CorrelationData(accountChangeEvent.getTxNo()));
    }

}




