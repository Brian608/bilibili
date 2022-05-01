package org.feather.bilibili.api;

import org.feather.bilibili.api.support.UserSupport;
import org.feather.bilibili.domain.JsonResponse;
import org.feather.bilibili.domain.User;
import org.feather.bilibili.service.UserService;
import org.feather.bilibili.service.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserSupport userSupport;

    @GetMapping("/users")
    public  JsonResponse<User> getUserInfo(){
        Long userId = userSupport.getCurrentUserId();
      User user=  userService.getUserInfo(userId);
      return  new JsonResponse<>(user);
    }

    @GetMapping("rsa-psk")
    public JsonResponse <String> getRsaPublicKey(){
        return new JsonResponse<>(RSAUtil.getPublicKeyStr());
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody  User user) {
        userService.addUser(user);
        return JsonResponse.success();
    }
    @PostMapping("user-tokens")
    public JsonResponse<String> login(@RequestBody  User user) throws Exception {
        String token=userService.login(user);
        return  new JsonResponse<>(token);
    }

}
