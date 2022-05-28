package com.lhr.milk.commodity.controller;

import com.lhr.milk.commodity.service.AdvancePaymentService;
import com.lhr.milk.commodity.service.CommodityService;
import com.lhr.milk.commodity.service.PaymentService;
import com.lhr.milk.commodity.service.UserAddressService;
import com.lhr.milk.common.result.Result;
import com.lhr.milk.common.utils.AuthContextHolder;
import com.lhr.milk.model.model.commodity.AdvancePayment;
import com.lhr.milk.model.model.commodity.CommodityType;
import com.lhr.milk.model.model.order.PaymentInfo;
import com.lhr.milk.model.model.user.UserAddress;
import com.lhr.milk.model.vo.AdvancePaymentVo;
import com.lhr.milk.model.vo.OrderQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/24 16:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/order")
public class ApiController {

    @Autowired
    private AdvancePaymentService advancePaymentService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private PaymentService paymentService;
    /**
     * 通过查询条件来返回固定的购物车内容
     * @param request
     * @param orderQueryVo
     * @return
     */
    @GetMapping("/getPageList")
    public Result getPageList(HttpServletRequest request,
                              OrderQueryVo orderQueryVo){
        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
        List<AdvancePaymentVo> list = advancePaymentService.getPageList(orderQueryVo);
        return Result.ok(list);
    }
    /**
     * 获取练级查询json
     * @return
     */
    @GetMapping("/getParent")
    public Result getParent(){
        List<CommodityType> cList = commodityService.getParent();
        return Result.ok(cList);
    }
    /**
     * 根据订单的id来删除购物车中的内容
     * @param id
     * @return
     */
    @DeleteMapping("/cancel/{id}")
    public Result cancel(@PathVariable long id){
        advancePaymentService.cancel(id);
        return Result.ok();
    }
    /**
     * 根据订单的id来展示购物车中的内容
     * @param orderId
     * @return
     */
    @GetMapping("/orderDetail/{orderId}")
    public Result orderDetail(@PathVariable long orderId){
        AdvancePaymentVo a = advancePaymentService.orderDetail(orderId);
        return Result.ok(a);
    }

    /**
     * 根绝用户id获取地址 并且设置默认地址
     * @param request
     * @return
     */
    @GetMapping("/getUserAddress")
    public Result getUserAddress(HttpServletRequest request){
        long userId = AuthContextHolder.getUserId(request);
        List<UserAddress> uList = userAddressService.getUserAddress(userId);
        return Result.ok(uList);
    }

    /**
     * 支付通过的拿取回调参数
     * @param orderId
     * @return
     */
    @GetMapping("/getBack/{orderId}")
    public Result getBack(@PathVariable long orderId){

        AdvancePayment advancePayment = advancePaymentService.getById(orderId);
        String outTradeNo = advancePayment.getOutTradeNo();
        PaymentInfo paymentInfo = paymentService.getByOutTrandeNo(outTradeNo);
        return Result.ok(paymentInfo);
    }

    /**
     * 通过id获取所有的交易记录
     * @param userId
     * @return  回调给user模块
     */
    @GetMapping("/getAllByUserId/{userId}")
    public List<PaymentInfo> getAllByUserId(@PathVariable long userId){
        List<PaymentInfo> paymentInfoList = paymentService.getAllByUserId(userId);
        return paymentInfoList;
    }

    /**
     * 拿取用户地址 进行管理
     * @param userId
     * @return
     */
    @GetMapping("getUserAllAddress/{userId}")
    public List<UserAddress> getUserAllAddress(@PathVariable long userId){
        return userAddressService.getUserAddress(userId);
    }

    @GetMapping("changeAddressDefault/{addressId}")
    public Result changeAddressDefault(@PathVariable long addressId,HttpServletRequest request){
        userAddressService.changeAddressDefault(addressId,AuthContextHolder.getUserId(request));
        return Result.ok();
    }

    @PostMapping("addAddress/{address}")
    public Result addAddress(HttpServletRequest request,
                             @PathVariable String address){
        Long userId = AuthContextHolder.getUserId(request);
        userAddressService.addAddress(address,userId);
        return Result.ok();
    }
}
