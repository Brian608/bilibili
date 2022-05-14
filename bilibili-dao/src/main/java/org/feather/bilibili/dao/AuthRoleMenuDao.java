package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.auth.AuthRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-14 18:40
 **/
@Mapper
public interface AuthRoleMenuDao {

    List<AuthRoleMenu> getAuthRoleMenusByRoleIds(Set<Long> roleIdSet);

}
