package org.feather.bilibili.service;

import org.feather.bilibili.constant.UserConstant;
import org.feather.bilibili.dao.UserFollowingDao;
import org.feather.bilibili.domain.FollowingGroup;
import org.feather.bilibili.domain.User;
import org.feather.bilibili.domain.UserFollowing;
import org.feather.bilibili.domain.UserInfo;
import org.feather.bilibili.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-02 12:50
 **/
@Service
public class UserFollowingService {
    @Autowired
    private UserFollowingDao userFollowingDao;
    @Autowired
    private  FollowingGroupService groupService;
    @Autowired
    private  UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public  void  addUserFollowings(UserFollowing userFollowing){
        Long groupId = userFollowing.getGroupId();
        if (groupId==null){
            FollowingGroup followingGroup = groupService.getBytype(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        }else {
            FollowingGroup followingGroup = groupService.getById(groupId);
            if (followingGroup==null){
                throw  new ConditionException("关注分组不存在！");
            }
        }
        Long followingId = userFollowing.getFollowingId();
      User user=  userService.getUserById(followingId);
      if (user==null){
          throw  new ConditionException("关注的用户不存在");
      }
      userFollowingDao.deleteUserFollowing(userFollowing.getUserId(),followingId);
      userFollowing.setCreateTime(new Date());
      userFollowingDao.addUserFollowing(userFollowing);
    }
    //step1 获取关注的用户列表
    //step2 根据关注用户的ID查询关注用户的基本信息
    //step3 将关注用户按关注分组进行分类
    public  List<FollowingGroup> getUserFollowings(Long userId){
     List<UserFollowing> list=   userFollowingDao.getUserFollowings(userId);
        Set<Long> followingIdSet = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(followingIdSet.size() > 0){
            userInfoList = userService.getUserInfoByUserIds(followingIdSet);
        }
        for(UserFollowing userFollowing : list){
            for(UserInfo userInfo : userInfoList){
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        List<FollowingGroup> groupList = groupService.getByUserId(userId);
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        for(FollowingGroup group : groupList){
            List<UserInfo> infoList = new ArrayList<>();
            for(UserFollowing userFollowing : list){
                if(group.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }

            }
            group.setFollowingUserInfoList(infoList);
            result.add(group);
        }
        return result;
    }
    //step 1获取当前用户粉丝列表
    //step2 根据粉丝的用户ID查询基本信息
    //step3 查询当前用户是否已经关注该粉丝
    public  List <UserFollowing> getUserFans(Long userId){
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);
        Set<Long> fanIdSet = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(fanIdSet.size() > 0){
            userInfoList = userService.getUserInfoByUserIds(fanIdSet);
        }
        List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);
        for(UserFollowing fan : fanList){
            for(UserInfo userInfo : userInfoList){
                if(fan.getUserId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(false);
                    fan.setUserInfo(userInfo);
                }
            }
            for(UserFollowing following : followingList){
                if(following.getFollowingId().equals(fan.getUserId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;

    }

    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        groupService.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return groupService.getUserFollowingGroups(userId);
    }

    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long userId) {
        List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(userId);
        for(UserInfo userInfo : userInfoList){
            userInfo.setFollowed(false);
            for(UserFollowing userFollowing : userFollowingList){
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(true);
                }
            }
        }
        return userInfoList;
    }
}
