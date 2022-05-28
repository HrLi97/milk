package com.lhr.milk.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.admin.mapper.UserInfoMapper;
import com.lhr.milk.admin.service.UserInfoService;
import com.lhr.milk.model.model.user.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author lhr
 * @Date:2022/5/28 21:20
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
