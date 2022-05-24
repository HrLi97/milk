package com.lhr.milk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.common.exception.MilkException;
import com.lhr.milk.common.helper.JwtHelper;
import com.lhr.milk.common.result.ResultCodeEnum;
import com.lhr.milk.model.model.user.UserInfo;
import com.lhr.milk.model.vo.LoginVo;
import com.lhr.milk.user.mapper.UserInfoMapper;
import com.lhr.milk.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/22 15:39
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper,
        UserInfo> implements UserInfoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 用户登录接口
     * @param loginVo
     * @return
     */
    @Override
    public Map<String, Object> login(LoginVo loginVo) {

        //从loginVo获取输入的邮箱，和验证码
        String email = loginVo.getEmail();
        String code = loginVo.getCode();

        Map<String, Object> map = new HashMap<>();

        //判断手机号和验证码是否为空
        if (StringUtils.isEmpty(email)&&StringUtils.isEmpty(code)){
            throw new MilkException(ResultCodeEnum.PARAM_ERROR);
        }
        //手机号和验证码不为空
        //TODO 验证码校验
        String realCode = redisTemplate.opsForValue().get(email);
        if (!realCode.equals(code)){
            throw new MilkException(ResultCodeEnum.CODE_ERROR);
        }

        //验证码正确的情况下加入用户信息，设置姓名，如果没有openid强制跳出wx登录
        UserInfo user = null;
        if (!StringUtils.isEmpty(loginVo.getOpenid())){
            //绑定了微信
            map.put("weixin",0);
            user = this.findByOpenId(loginVo.getOpenid());
            user.setEmail(email);
            baseMapper.updateById(user);
        }
        //没有绑定wx，但是邮箱登录了 就查找有没有同样的邮箱 如果有就判断有没有openid

        if(user==null){
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("email",email);
            user = baseMapper.selectOne(wrapper);
            //第一次登录 绑定wx
            if (user==null){
                user = new UserInfo();
                user.setName("");
                user.setEmail(email);
                user.setStatus(1);
                user.setOnLine(0);
                baseMapper.insert(user);
            }
            //TODO 绑定微信
            map.put("weixin",1);
        }
        //校验是否被禁用
        if(user.getStatus() == 0) {
            throw new MilkException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }
        //返回页面显示名称
        String name = user.getName();
        if(StringUtils.isEmpty(name)) {
            name = user.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = user.getEmail();
        }

        //TODO token生成
        String token = JwtHelper.createToken(user.getId(), name);
        map.put("name", name);
        map.put("token", token);
        return map;
    }

    @Override
    public UserInfo findByOpenId(String openid) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }
}
