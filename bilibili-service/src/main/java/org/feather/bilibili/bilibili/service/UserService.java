package org.feather.bilibili.bilibili.service;

import org.feather.bilibili.bilibili.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-04-16 21:54
 **/
@Service
public class UserService {
        @Autowired
       private UserDao userDao;
}
