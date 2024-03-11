package com.zzz.producer.config;

import com.alibaba.fastjson.JSONObject;
import com.zzz.producer.mapper.AccountInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhongzhen wrote on 2024/3/10
 * @version 1.0
 * @description:
 */
@Component
@Slf4j
public class ConfirmService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息：" + returnedMessage.getMessage().toString());
        log.info("返回码：" + returnedMessage.getReplyCode());
        log.info("返回描述：" + returnedMessage.getMessage());
        log.info("交换机：" + returnedMessage.getExchange());
        log.info("路由key：" + returnedMessage.getRoutingKey());

        //方法执行，说明消息未投递成功，将日志表的状态修改为N
        String msg = new String(returnedMessage.getMessage().getBody());
        accountInfoMapper.updAccountLog((String) JSONObject.parseObject(msg).get("txNo"), "N");
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息标识：" + correlationData);
        log.info("发送成功确认：" + ack);
        log.info("错误原因：" + cause);

        //该方法在消息到达MQ服务器时都表示成功，并不能保证消息一定会被投递到目标 queue 里，所以这个方法不适合做确认
    }
}
