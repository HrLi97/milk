package com.lhr.milk.client;

import com.lhr.milk.model.model.commodity.CommodityType;
import com.lhr.milk.model.model.order.PaymentInfo;
import com.lhr.milk.model.model.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/27 14:29
 * @Version 1.0
 */
@Repository
@FeignClient("service-commodity")
public interface PaymentFeignClient {

    /**
     * 通过id获取所有的交易记录
     * @param userId
     * @return  回调给user模块
     */
    @GetMapping("/api/order/getAllByUserId/{userId}")
    List<PaymentInfo> getAllByUserId(@PathVariable("userId") long userId);

    /**
     * 拿取用户地址 进行管理
     * @param userId
     * @return
     */
    @GetMapping("/api/order/getUserAllAddress/{userId}")
    List<UserAddress> getUserAllAddress(@PathVariable long userId);

    /**
     * 远程嗲用接口
     * @param name
     * @return
     */
    @GetMapping("/front/commodity/getTypeByName/{name}")
    List<Integer> getTypeByName(@PathVariable("name") String name);

    /**
     * 调用接口
     * @param id
     * @return
     */
    @GetMapping("/front/commodity/getTypeNameById/{id}")
    String getTypeNameById(@PathVariable("id") long id);

    /**
     * 查找所有种类信息
     * @return
     */
    @GetMapping("/front/commodity/findAllType")
    List<CommodityType> findAllType();
}
