package com.lhr.milk.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.model.user.UserInfo;
import com.lhr.milk.model.vo.LoginVo;

import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/22 15:39
 * @Version 1.0
 */

public interface UserInfoService extends IService<UserInfo> {

    Map<String, Object> login(LoginVo loginVo);

    UserInfo findByOpenId(String openid);


}
