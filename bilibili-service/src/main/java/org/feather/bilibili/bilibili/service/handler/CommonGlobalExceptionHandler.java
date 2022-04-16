package org.feather.bilibili.bilibili.service.handler;

import org.feather.bilibili.bilibili.domain.JsonResponse;
import org.feather.bilibili.bilibili.domain.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-04-15 22:32
 **/
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {

    public JsonResponse<String> commonExceptionHandler(HttpRequest request,Exception e){
        String errMsg=e.getMessage();
        if (e instanceof ConditionException){
            String errCode=((ConditionException) e).getCode();
            return  new JsonResponse<>(errCode,errMsg);
        }else {
            return  new JsonResponse<>("500",errMsg);
        }
    }

}
