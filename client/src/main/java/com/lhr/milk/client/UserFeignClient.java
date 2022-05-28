package com.lhr.milk.client;


import com.lhr.milk.model.model.user.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lhr
 * @Date:2022/5/19 10:04
 * @Version 1.0
 */
@Repository
@FeignClient("service-user")
public interface UserFeignClient {

    /**
     * 通过userId获取用户数据
     */
    @GetMapping("/login/user/email/getUserById/{userId}")
    UserInfo getUserById(@PathVariable("userId") long userId);

    /**
     * 刷新用户表的消费金额
     * @param userId
     * @param price
     */
    @GetMapping("/login/user/email/userPaySuccess/{userId}/{price}")
    void userPaySuccess(@PathVariable("userId") long userId,@PathVariable("price") Integer price);
}


