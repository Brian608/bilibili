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

    public void addUser(User user) {
        String phone=user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号为空");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser!=null){
            throw  new ConditionException("该手机号已经被注册了");
        }
        Date date=new Date();
        String salt=String.valueOf(date.getTime());
        String password=user.getPassword();
        String rawPassword;
        try {
           rawPassword= RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw  new ConditionException("密码解密失败！");
        }
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setPassword(md5Password);
        user.setSalt(salt);
        user.setCreateTime(date);
        userDao.addUser(user);
        //添加用户信息
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_FEMALE);
        userInfo.setCreateTime(date);
        userDao.addUserInfo(userInfo);

    }
    public  User getUserByPhone(String phone){
        return  userDao.getUserByPhone(phone);
    }

    public String login(User user) {
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
        try {
            rawPassword=RSAUtil.decrypt(password);
        }catch ( Exception e){
            throw  new ConditionException("密码解密失败");
        }
        String salt = user.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Password.equals(dbUser.getPassword())){
                throw new ConditionException("密码解密失败！");
        }
       TokenUtil tokenUtil=new TokenUtil();
        return tokenUtil.generateToken(dbUser.getId());


    }
}
