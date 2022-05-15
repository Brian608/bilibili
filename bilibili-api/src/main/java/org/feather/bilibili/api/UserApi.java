package org.feather.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import org.feather.bilibili.api.support.UserSupport;
import org.feather.bilibili.domain.JsonResponse;
import org.feather.bilibili.domain.PageResult;
import org.feather.bilibili.domain.User;
import org.feather.bilibili.domain.UserInfo;
import org.feather.bilibili.service.UserFollowingService;
import org.feather.bilibili.service.UserService;
import org.feather.bilibili.service.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserFollowingService userFollowingService;

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
    @PutMapping("/users")
    public  JsonResponse <String> updateUsers(@RequestBody User user) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);
        userService.updateUsers(user);
        return   JsonResponse.success();
    }
    @PutMapping("/user-info")
    public  JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo){
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfos(userInfo);
        return  JsonResponse.success();
    }
    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no, @RequestParam Integer size, String nick){
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no", no);
        params.put("size", size);
        params.put("nick", nick);
        params.put("userId", userId);
        PageResult<UserInfo> result = userService.pageListUserInfos(params);
        if(result.getTotal() > 0){
            List<UserInfo> checkedUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(checkedUserInfoList);
        }
        return new JsonResponse<>(result);
    }
    @PostMapping("user-dts")
    public  JsonResponse<Map<String,Object>> loginForDts(@RequestBody User user) throws Exception {
        Map<String, Object> map=userService.loginForDts(user);
        return  new JsonResponse<>(map);
    }
    @DeleteMapping("/refresh-tokens")
    public JsonResponse<String> logout(HttpServletRequest request){
        String refreshToken = request.getHeader("refreshToken");
        Long userId = userSupport.getCurrentUserId();
        userService.logout(refreshToken, userId);
        return JsonResponse.success();
    }

    @PostMapping("/access-tokens")
    public JsonResponse<String> refreshAccessToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("refreshToken");
        String accessToken = userService.refreshAccessToken(refreshToken);
        return new JsonResponse<>(accessToken);
    }

}
