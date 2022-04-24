package org.feather.bilibili.api;

import org.feather.bilibili.bilibili.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-04-16 21:55
 **/
@RestController
public class UserApi {
    @Autowired
    private UserService userService;

}
