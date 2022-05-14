package org.feather.bilibili.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.feather.bilibili.api.support.UserSupport;
import org.feather.bilibili.constant.AuthRoleConstant;
import org.feather.bilibili.domain.UserMoment;
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
public class DataLimitedAspect {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(org.feather.bilibili.domain.annoation.DataLimited)")
    public  void  check(){

    }
    @Before("check()")
    public  void  doBefore(JoinPoint joinPoint){
        Long currentUserId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(currentUserId);
        Set<Long> userRoleSet = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for (Object  arg: args)
        if (arg instanceof UserMoment){
            UserMoment userMoment=(UserMoment) arg;
            String type = userMoment.getType();
            if (userRoleSet.contains(AuthRoleConstant.ROLE_LV0)&&!"0".equals(type)){
                throw  new ConditionException("参数异常");
            }
        }
    }



}
