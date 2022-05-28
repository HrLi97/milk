package com.lhr.milk.commodity.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhr.milk.commodity.service.PaymentService;
import com.lhr.milk.commodity.service.WXPayService;
import com.lhr.milk.common.result.Result;
import com.lhr.milk.common.utils.AuthContextHolder;
import com.lhr.milk.model.enums.PaymentTypeEnum;
import com.lhr.milk.model.vo.DateVo;
import io.swagger.annotations.ApiParam;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/26 16:05
 * @Version 1.0
 */

@RestController
@RequestMapping("/api/order/weixin")
public class WXController {

    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private PaymentService paymentService;

    /**
     * 下单生成微信支付二维码
     * @param orderId
     * @param addressId
     * @param getTime
     * @return
     */
    @GetMapping("/createNative/{orderId}/{addressId}/{getTime}")
    public Result createNative(
            @PathVariable("orderId") Long orderId,
            @PathVariable("addressId") Integer addressId,
            @PathVariable String getTime, HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        return Result.ok(wxPayService.createNative(orderId,addressId,getTime,userId));
    }

    @GetMapping("/queryPayStatus/{orderId}")
    public Result queryPayStatus(@PathVariable("orderId") Long orderId){
        //调用查询接口
        Map<String, String> resultMap = wxPayService.queryPayStatus(orderId, PaymentTypeEnum.WEIXIN.name());
        if (resultMap == null) {
            //出错
            return Result.fail().message("支付出错");
        }
        //如果成功
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {
            //更改订单状态，处理支付结果
            String out_trade_no = resultMap.get("out_trade_no");
            //再把数据更新
            paymentService.paySuccess(out_trade_no,PaymentTypeEnum.WEIXIN.getStatus(), resultMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");
    }
}
