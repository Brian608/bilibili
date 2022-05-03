package org.feather.bilibili.service.utils;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-03 15:35
 **/
public class RocketMQUtil {
    /**
     * 同步发送
     * @param producer
     * @param message
     */
    public  static  void  syncSendMsg(DefaultMQProducer producer, Message message) throws Exception{
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult);
    }

    public static  void asyncSendMessage(DefaultMQProducer producer,Message message) throws  Exception{
        int messageCount=2;
        CountDownLatch2 countDownLatch2=new CountDownLatch2(messageCount);
        for (int i = 0; i <messageCount ; i++) {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch2.countDown();
                    System.out.println(sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    countDownLatch2.countDown();
                    System.out.println("发送消息的时候发生了异常！"+e);
                    e.printStackTrace();
                }
            });
        }
        countDownLatch2.await(5, TimeUnit.SECONDS);
    }
}
