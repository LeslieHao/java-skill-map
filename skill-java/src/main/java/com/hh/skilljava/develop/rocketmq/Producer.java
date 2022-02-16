package com.hh.skilljava.develop.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author HaoHao
 * @date 2022/2/16 2:48 下午
 */
public class Producer {

    public static final String NAME_SERVER_ADDRESS = "127.0.0.1:9876";

    public static final String topic = "TopicTest";
    /**
     * 支持异步发送消息,使用响应时间敏感的场景
     * 支持单项发送,这种方式主要用在不特别关心发送结果的场景，例如日志发送。
     *
     */
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("group_name");
        // name server 地址
        // 名称服务充当路由消息的提供者。生产者或消费者能够通过名字服务查找各主题相应的Broker IP列表。
        // 多个Namesrv实例组成集群，但相互独立，没有信息交换。
        producer.setNamesrvAddr(NAME_SERVER_ADDRESS);
        producer.start();

        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体

            Message msg = new Message(topic /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();

    }

}
