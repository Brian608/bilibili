package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.User;
import org.feather.bilibili.domain.UserInfo;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-04-16 21:59
 **/
@Mapper
public interface UserDao {

    User getUserByPhone(String phone);

    Integer addUser(User user);

    void addUserInfo(UserInfo userInfo);
}
