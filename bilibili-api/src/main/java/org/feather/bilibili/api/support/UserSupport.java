package org.feather.bilibili.api.support;

import org.feather.bilibili.domain.exception.ConditionException;
import org.feather.bilibili.service.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-01 11:13
 **/
@Component
public class UserSupport {

    public  Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if (userId<0){
            throw  new ConditionException("非法用户");
        }
        return  userId;

    }
}
