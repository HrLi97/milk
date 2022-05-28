package com.lhr.milk.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.model.order.PaymentInfo;
import com.lhr.milk.model.vo.AdvancePaymentVo;

import java.util.List;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/26 16:14
 * @Version 1.0
 */
public interface PaymentService extends IService<PaymentInfo> {

    String savePaymentInfo(AdvancePaymentVo paymentVo, int i, Integer addressId, String getTime, Long userId);

    void paySuccess(String out_trade_no, int i, Map<String, String> resultMap);

    PaymentInfo getByOutTrandeNo(String outTradeNo);

    List<PaymentInfo> getAllByUserId(long userId);

}
