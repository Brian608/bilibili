package org.feather.bilibili.service;

import org.feather.bilibili.dao.FollowingGroupDao;
import org.feather.bilibili.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-02 12:50
 **/
@Service
public class FollowingGroupService {
    @Autowired
    private FollowingGroupDao followingGroupDao;

    public FollowingGroup getBytype(String type){
        return  followingGroupDao.getByType(type);
    }

    public FollowingGroup getById(Long id){
        return  followingGroupDao.getById(id);
    }

    public List<FollowingGroup> getByUserId(Long userId) {
        return  followingGroupDao.getByUserId(userId);
    }
    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupDao.addFollowingGroup(followingGroup);
    }

    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupDao.getUserFollowingGroups(userId);
    }
}
