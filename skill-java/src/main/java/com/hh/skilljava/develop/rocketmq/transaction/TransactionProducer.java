package com.hh.skilljava.develop.rocketmq.transaction;

import com.hh.skilljava.develop.rocketmq.Producer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

/**
 * @author HaoHao
 * @date 2022/2/24 3:04 下午
 */
public class TransactionProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("group_name_t");
        producer.setNamesrvAddr(Producer.NAME_SERVER_ADDRESS);
        producer.setTransactionListener(transactionListener);
        producer.start();
        Message msg = new Message("ttttttt",
                "增加余额~".getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = producer.sendMessageInTransaction(msg, null);
        System.out.printf("发送消息成功~");
        producer.shutdown();
    }
}
