package org.feather.bilibili.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.feather.bilibili.api.support.UserSupport;
import org.feather.bilibili.domain.annoation.ApiLimitedRole;
import org.feather.bilibili.domain.auth.UserRole;
import org.feather.bilibili.domain.exception.ConditionException;
import org.feather.bilibili.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-14 20:26
 **/
@Order(1)
@Component
@Aspect
public class ApiLimitedRoleAspect {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(org.feather.bilibili.domain.annoation.ApiLimitedRole)")
    public  void  check(){

    }
    @Before("check() && @annotation(ApiLimitedRole)")
    public  void  doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole){
        Long currentUserId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(currentUserId);
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set<String> userRoleSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        userRoleSet.retainAll(limitedRoleCodeSet);
        if (!userRoleSet.isEmpty()){
            throw  new ConditionException("权限不足");
        }


    }



}
