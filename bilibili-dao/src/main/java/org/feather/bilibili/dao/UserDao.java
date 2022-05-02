package org.feather.bilibili.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.User;
import org.feather.bilibili.domain.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    User getUserByUserId(Long userId);

    UserInfo getUserInfoByUserId(Long userId);

    void updateUsers(User user);

    void  updateUserInfos(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserIds(Set<Long> followingIdList);

    Integer pageCountUserInfos(Map<String,Object> params);

    List<UserInfo> pageListUserInfos(Map<String, Object> params);
}
