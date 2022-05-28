package com.lhr.milk.commodity.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.lhr.milk.commodity.mapper.AdvancePaymentMapper;
import com.lhr.milk.commodity.service.WXPayService;
import com.lhr.milk.commodity.service.PaymentService;
import com.lhr.milk.commodity.utils.ConstantPropertiesUtils;
import com.lhr.milk.commodity.utils.HttpClient;
import com.lhr.milk.model.vo.AdvancePaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lhr
 * @Date:2022/5/26 16:06
 * @Version 1.0
 */
@Service
public class WXPayServiceImpl implements WXPayService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AdvancePaymentMapper advancePaymentMapper;

    @Autowired
    private PaymentService paymentService;

    /**
     * 下单生成微信支付二维码
     * @param orderId
     * @param addressId
     * @param getTime
     * @param userId
     * @return
     */
    @Override
    public Object createNative(Long orderId, Integer addressId, String getTime, Long userId) {

//        Map redisMap = (Map)redisTemplate.opsForValue().get(orderId.toString());
//        if (redisMap!=null&&redisMap.get("codeUrl")!=null){
//            return redisMap;
//        }

        try {
            //获取订单信息
            AdvancePaymentVo paymentVo = advancePaymentMapper.selectByorderId(orderId);

            //向支付记录表payment添加信息
            paymentService.savePaymentInfo(paymentVo, 2,addressId,getTime,userId);
            //1、设置参数  调用微信生成的二维码接口
            //把参数转换为xml格式 使用商户key进行加密

            Map paramMap = new HashMap();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            String body = this.getNameFromDetail(paymentVo);
            paramMap.put("body", body);
            paramMap.put("out_trade_no", paymentVo.getOutTradeNo());
            paramMap.put("total_fee", "1");
            paramMap.put("spbill_create_ip", "127.0.0.1");
            paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            paramMap.put("trade_type", "NATIVE");

            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
            client.setHttps(true);
            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            System.out.println("resultMap="+resultMap);

            //4、封装返回结果集
            Map map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("price", paymentVo.getPrice());
            map.put("resultCode", resultMap.get("result_code"));
            //codeUrl为二维码地址
            map.put("codeUrl", resultMap.get("code_url"));

            redisTemplate.opsForValue().set(orderId.toString(),map,2, TimeUnit.MINUTES);

            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> queryPayStatus(Long orderId, String weixin) {
        try {
            AdvancePaymentVo paymentVo = advancePaymentMapper.selectByorderId(orderId);
            //1、封装参数
            Map paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("out_trade_no",paymentVo.getOutTradeNo());
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据，转成Map
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、返回
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    public String getNameFromDetail(AdvancePaymentVo itemVo){
        //条件查询完毕整理数据
        String detail = itemVo.getDetail();
        String[] numberList = detail.substring(0,detail.length()-2).replace("\"","").split("},");
        /**
         *         Arrays.stream(numberList).forEach(list->{
         *             String totalName = "";
         *             String[] split = list.split(",");
         *             HashMap<String, String> map = new HashMap<>();
         *             String name = split[0].split(":")[1];
         *             totalName+=name;
         *         });
         */
        String totalName = "";
        for (int i = 0; i < numberList.length; i++) {
            String[] num = numberList[i].split(",");
            String[] split = num[0].split(":");
            String name = split[1];
            totalName+=name;
        }

        return totalName;

    }
}
