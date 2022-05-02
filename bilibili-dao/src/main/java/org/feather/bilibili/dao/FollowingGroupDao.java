package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.FollowingGroup;

import java.util.List;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-02 12:48
 **/
@Mapper
public interface FollowingGroupDao {
    
    FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);

    List<FollowingGroup> getByUserId(Long userId);

    void addFollowingGroup(FollowingGroup followingGroup);

    List<FollowingGroup> getUserFollowingGroups(Long userId);
}
