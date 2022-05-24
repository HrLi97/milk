package com.lhr.milk.user.controller;

import com.lhr.milk.common.result.Result;
import com.lhr.milk.model.vo.LoginVo;
import com.lhr.milk.user.service.EmailService;
import com.lhr.milk.user.service.UserInfoService;
import com.lhr.milk.user.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lhr
 * @Date:2022/5/22 13:37
 * @Version 1.0
 */
@Api(value = "test")
@RestController
@RequestMapping("/login/user/email")

public class EmailController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserInfoService userInfoService;

    //发送邮箱验证码进行登录
    @GetMapping("send/{email}")
    public Result sendCode(@PathVariable String email){

        String code = redisTemplate.opsForValue().get(email);
        if (!StringUtils.isEmpty(code)){
            return Result.ok();
        }
        code = RandomUtil.getSixBitRandom();
        //调用service方法，通过整合短信服务进行发送
        boolean isSend = emailService.send(email,code);
        //发送成功就放进reids设置时间
        if (isSend){
            redisTemplate.opsForValue().set(email,code,2,TimeUnit.MINUTES);
            return Result.ok();
        }else {
            return Result.fail().message("发送失败");
        }
    }

    /**
     * 确认登录接口
     * @param loginVo
     * @return
     */
    @ApiOperation("login")
    @PostMapping("userLogin")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String,Object> info = userInfoService.login(loginVo);
        return Result.ok(info);
    }
}
