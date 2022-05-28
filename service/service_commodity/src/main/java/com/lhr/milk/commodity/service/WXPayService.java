package com.lhr.milk.commodity.service;

import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/26 16:06
 * @Version 1.0
 */
public interface WXPayService {
    Object createNative(Long orderId, Integer addressId, String getTime, Long userId);

    Map<String, String> queryPayStatus(Long orderId, String weixin);
}
