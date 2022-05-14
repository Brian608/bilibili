package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.auth.UserRole;

import java.util.List;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-14 18:33
 **/
@Mapper
public interface UserRoleDao {

    List<UserRole> getUserRoleByUserId(Long userId);

    void addUserRole(UserRole userRole);
}
