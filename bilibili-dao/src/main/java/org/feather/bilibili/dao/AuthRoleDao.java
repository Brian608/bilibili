package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.auth.AuthRole;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-14 22:26
 **/
@Mapper
public interface AuthRoleDao {

    AuthRole getRoleByCode(String code);
}
