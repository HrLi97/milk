package com.lhr.milk.user.controller;

import com.lhr.milk.common.result.Result;
import com.lhr.milk.common.utils.AuthContextHolder;
import com.lhr.milk.model.model.user.UserInfo;
import com.lhr.milk.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/27 13:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/user/")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户的消费信息 评论信息  订单信息
     * @param request
     * @return
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId(request);
        //通过userId去三个表查询消息
        Map<String, Object> resultMap = userInfoService.getUserInfo(userId);
        return Result.ok(resultMap);
    }

}
