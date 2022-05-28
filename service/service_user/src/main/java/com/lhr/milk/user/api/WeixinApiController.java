package com.lhr.milk.user.api;

import com.alibaba.fastjson.JSONObject;
import com.lhr.milk.common.exception.MilkException;
import com.lhr.milk.common.helper.JwtHelper;
import com.lhr.milk.common.result.Result;
import com.lhr.milk.common.result.ResultCodeEnum;
import com.lhr.milk.model.model.user.UserInfo;
import com.lhr.milk.user.service.UserInfoService;
import com.lhr.milk.user.utils.ConstantPropertiesUtil;
import com.lhr.milk.user.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/22 18:59
 * @Version 1.0
 */

@Controller
@RequestMapping("api/ucenter/wx")
public class WeixinApiController {
    @Autowired
    private UserInfoService userInfoService;

    //生成微信扫描的二维码  将需要的参数回调
    @GetMapping("getLoginParams")
    @ResponseBody
    public Result getLoginParams(HttpSession session) throws UnsupportedEncodingException {
        String redirectUri = URLEncoder.encode(ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL, "UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("appid", ConstantPropertiesUtil.WX_OPEN_APP_ID);
        map.put("redirectUri", redirectUri);
        map.put("scope", "snsapi_login");
        map.put("state", System.currentTimeMillis() + "");
        System.out.println(map.toString());
        return Result.ok(map);
    }

    /**
     * 微信登录回调
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("callback")
    public String callback(String code, String state) throws Exception {
        if (StringUtils.isEmpty(state) || StringUtils.isEmpty(code)) {
            System.out.println("error");
            throw new MilkException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        //使用code和appid以及appscrect换取access_token
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resultJson = JSONObject.parseObject(result);
        String access_token = resultJson.getString("access_token");
        String openid = resultJson.getString("openid");

        //判断用户数据库中是否有扫码人信息
        UserInfo userInfo = userInfoService.findByOpenId(openid);
        if (userInfo==null){
            //之前没有微信登陆
            //拿着access_token和openid去获取用户的具体信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
            String resultInfo = HttpClientUtils.get(userInfoUrl);

            System.out.println(resultInfo);

            JSONObject resultUserInfoJson = JSONObject.parseObject(resultInfo);
            //解析用户信息
            //用户昵称
            String nickname = resultUserInfoJson.getString("nickname");
            //用户头像
            String headimgurl = resultUserInfoJson.getString("headimgurl");

            //获取扫描人信息添加数据库
            userInfo = new UserInfo();
            userInfo.setNickName(nickname);
            userInfo.setOpenid(openid);
            userInfo.setStatus(1);
//            userInfo.setName(nickname);
            userInfo.setHeadImgUrl(headimgurl);
            userInfo.setOnLine(0);
            userInfoService.save(userInfo);
        }
        //返回name和token字符串
        Map<String,String> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getEmail();
        }
        userInfo.setName(name);
        map.put("name", name);
        userInfo.setName(name);
        //判断userInfo是否有邮箱，如果邮箱为空，返回openid
        //如果手机号不为空，返回openid值是空字符串
        //前端判断：如果openid不为空，绑定手机号，如果openid为空，不需要绑定手机号
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            map.put("openid", userInfo.getOpenid());
        } else {
            map.put("openid", "");
        }
        //使用jwt生成token字符串
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);
        //跳转到前端页面
        return "redirect:" + ConstantPropertiesUtil.MILK_BASE_URL + "/weixin/callback?token="+map.get("token")+ "&openid="+map.get("openid")+
                "&name="+ URLEncoder.encode(map.get("name"),"utf-8");
    }



}
