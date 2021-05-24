package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.QueryResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class Query {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name_4");
        producer.setNamesrvAddr("192.168.182.128:9876");
        producer.start();
        QueryResult queryResult = producer.queryMessage("TopicTest", "abc", 1000, System.currentTimeMillis() - 1_000_000_000, System.currentTimeMillis());
        System.out.println(queryResult.getMessageList());
        producer.shutdown();
    }
}
