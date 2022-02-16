package com.hh.skilljava.develop.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author HaoHao
 * @date 2022/2/16 2:47 下午
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        /*
         wmb push 模式批量推送是没有ack的，只会由服务端记录推送的位置。
         这会导致，如果客户端本地缓存的消息没有消费完就挂了，当客户端再次启动时这部分没有消费完的消息不会再被推送，即消息丢失。
         也就是说push模式只保证at-most-once模式。
         */

        // push 模式,消费者与服务端必须建立长连接,响应快(其实也是基于pull 模式的长轮询)
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_name");

        /*
           wmb pull模式,不丢消息,消费完成才会commit offset,保证 at-least-once
         */
        // pull 模式,拉取模式可以每次拉取建立连接,但是实时性会差
        DefaultMQPullConsumer pullConsumer = new DefaultMQPullConsumer("group_name");

        consumer.setNamesrvAddr(Producer.NAME_SERVER_ADDRESS);

        // 订阅
        consumer.subscribe(Producer.topic, "*");

        // 注册处理器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("consumer started~");
    }
}
