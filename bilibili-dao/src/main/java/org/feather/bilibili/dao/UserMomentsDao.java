package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.UserMoment;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-03 15:58
 **/
@Mapper
public interface UserMomentsDao {


    void addUserMoments(UserMoment userMoment);
}
