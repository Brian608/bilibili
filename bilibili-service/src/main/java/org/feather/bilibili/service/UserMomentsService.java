package org.feather.bilibili.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.feather.bilibili.constant.UserMomentsConstant;
import org.feather.bilibili.dao.UserMomentsDao;
import org.feather.bilibili.domain.UserMoment;
import org.feather.bilibili.service.utils.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-03 15:57
 **/
@Service
public class UserMomentsService {
    @Autowired
    private UserMomentsDao userMomentsDao;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisTemplate redisTemplate;

    public void addUserMoments(UserMoment userMoment) throws Exception {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);
       DefaultMQProducer producer= (DefaultMQProducer) applicationContext.getBean("momentsProducer");
        Message message=new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.asyncSendMessage(producer,message);

    }

    public List<UserMoment> getUserSubscribedMoments(Long userId) {
        String key="subscribed-"+userId;
        String listStr = (String) redisTemplate.opsForValue().get(key);
        return JSONArray.parseArray(listStr,UserMoment.class);
    }
}
