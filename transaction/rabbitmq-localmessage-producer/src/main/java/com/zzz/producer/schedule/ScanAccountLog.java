package com.zzz.producer.schedule;

import com.alibaba.fastjson.JSONObject;
import com.zzz.producer.config.ConfirmService;
import com.zzz.producer.entity.AccountInfoLog;
import com.zzz.producer.mapper.AccountInfoMapper;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zhangzhongzhen wrote on 2024/3/10
 * @version 1.0
 * @description:
 */
@Component
public class ScanAccountLog {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private ConfirmService confirmService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 定时扫描并处理失败的账户信息日志。
     * 初始延迟1秒后开始执行，之后每次执行间隔5秒。
     * 在方法执行过程中，会从数据库中查询出所有失败的账户信息日志，并尝试对这些日志进行处理，
     * 包括更新日志状态，并通过RabbitMQ发送相关信息。
     *
     * @Transactional 表明该方法是一个事务方法，确保操作的原子性。
     * @Scheduled 注解指定了该方法是一个定时任务，使用了Spring的定时任务框架。
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    @Transactional
    public void scan() {
        // 从数据库中查询所有失败的账户信息日志
        List<AccountInfoLog> accountInfoLogs = accountInfoMapper.selectFailAcount();
        // 当查询结果非空时，对每个失败的日志进行处理
        if (!CollectionUtils.isEmpty(accountInfoLogs)) {
            // 遍历查询结果，对每个失败的账户信息日志进行处理
            accountInfoLogs.forEach(accountInfoLog -> {
                // 打印定时任务执行信息
                System.out.println("定时任务执行");
                // 更新账户信息日志的状态为已处理
                accountInfoMapper.updAccountLog(accountInfoLog.getTxNo(), "Y");

                // 设置RabbitMQ的确认回调和返回回调
                rabbitTemplate.setConfirmCallback(confirmService);
                rabbitTemplate.setReturnsCallback(confirmService);
                // 将账户信息日志发送到RabbitMQ的指定队列，使用JSON格式序列化
                rabbitTemplate.convertAndSend("account_exchange", "account", JSONObject.toJSONString(accountInfoLog), message -> {
                    // 设置消息的持久化属性
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }, new CorrelationData(accountInfoLog.getTxNo()));
            });
        }
    }
}
