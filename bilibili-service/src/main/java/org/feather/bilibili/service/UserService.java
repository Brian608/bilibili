package org.feather.bilibili.service;

import com.mysql.cj.util.StringUtils;
import org.feather.bilibili.constant.UserConstant;
import org.feather.bilibili.dao.UserDao;
import org.feather.bilibili.domain.User;
import org.feather.bilibili.domain.UserInfo;
import org.feather.bilibili.domain.exception.ConditionException;
import org.feather.bilibili.service.utils.MD5Util;
import org.feather.bilibili.service.utils.RSAUtil;
import org.feather.bilibili.service.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-04-16 21:54
 **/
@Service
public class UserService {
        @Autowired
       private UserDao userDao;

        @Transactional(rollbackFor = Exception.class)
    public void addUser(User user) {
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser != null){
            throw new ConditionException("该手机号已经注册！");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try{
            rawPassword = RSAUtil.decrypt(password);
        }catch (Exception e){
            throw new ConditionException("密码解密失败！");
        }
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userDao.addUser(user);
        //添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);

    }
    public  User getUserByPhone(String phone){
        return  userDao.getUserByPhone(phone);
    }

    public String login(User user) throws Exception {
        String phone=user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)){
            throw  new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser==null){
            throw  new ConditionException("当前用户不存在");
        }
        String password = user.getPassword();
        String rawPassword;
        try{
            rawPassword = RSAUtil.decrypt(password);
        }catch (Exception e){
            throw new ConditionException("密码解密失败！");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if(!md5Password.equals(dbUser.getPassword())){
            throw new ConditionException("密码错误！");
        }
        return TokenUtil.generateToken(dbUser.getId());
    }

    public User getUserInfo(Long userId) {
     User user=    userDao.getUserByUserId(userId);
     UserInfo userInfo=userDao.getUserInfoByUserId(userId);
     user.setUserInfo(userInfo);
     return user;
    }
}
