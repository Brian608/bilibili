package org.feather.bilibili.domain.annoation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-14 20:17
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Component
public @interface DataLimited {


}
