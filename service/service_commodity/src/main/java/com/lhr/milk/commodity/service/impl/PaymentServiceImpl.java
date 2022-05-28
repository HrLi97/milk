package com.lhr.milk.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.client.UserFeignClient;
import com.lhr.milk.commodity.mapper.AdvancePaymentMapper;
import com.lhr.milk.commodity.mapper.PaymentMapper;
import com.lhr.milk.commodity.service.*;
import com.lhr.milk.common.exception.MilkException;
import com.lhr.milk.common.result.ResultCodeEnum;
import com.lhr.milk.model.enums.PaymentStatusEnum;
import com.lhr.milk.model.model.commodity.AdvancePayment;
import com.lhr.milk.model.model.commodity.JoinCart;
import com.lhr.milk.model.model.order.PaymentInfo;
import com.lhr.milk.model.model.user.UserAddress;
import com.lhr.milk.model.vo.AdvancePaymentVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author lhr
 * @Date:2022/5/26 16:14
 * @Version 1.0
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper,
        PaymentInfo> implements PaymentService {

    @Autowired
    private AdvancePaymentServiceImpl advancePaymentService;

    @Autowired
    private AdvancePaymentMapper advancePaymentMapper;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 向订单表中添加基本数据
     * @param advancePaymentVo
     * @param paymentType
     * @param addressId
     * @param getTime
     * @param userId
     * @return
     */
    @Override
    public String savePaymentInfo(AdvancePaymentVo advancePaymentVo, int paymentType, Integer addressId, String getTime, Long userId) {
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", advancePaymentVo.getId());
        queryWrapper.eq("payment_type", paymentType);
        Integer count = baseMapper.selectCount(queryWrapper);
        String outTradeNo = System.currentTimeMillis() + ""+ new Random().nextInt(100);
        if (count>0){
            PaymentInfo paymentInfo = baseMapper.selectOne(queryWrapper);
            paymentInfo.setGetTime(getTime);
            paymentInfo.setAddressId(addressId);
            baseMapper.updateById(paymentInfo);
            return outTradeNo;
        }

        // 保存交易记录
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(advancePaymentVo.getId());
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setAddressId(addressId);
        paymentInfo.setGetTime(getTime);
        paymentInfo.setUserId(userId);
        paymentInfo.setOutTradeNo(advancePaymentVo.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatusEnum.UNPAID.getStatus());

        String subject = advancePaymentVo.getDetail();
        paymentInfo.setSubject(subject);
        paymentInfo.setPrice(advancePaymentVo.getPrice());
        baseMapper.insert(paymentInfo);
        return outTradeNo;

    }

    /**
     * 支付成功的回调请求
     * @param outTradeNo
     * @param paymentType
     * @param paramMap
     */
    @Override
    public void paySuccess(String outTradeNo, int paymentType, Map<String, String> paramMap) {

        PaymentInfo paymentInfo = this.getPaymentInfo(outTradeNo, paymentType);
        if (null == paymentInfo) {
            throw new MilkException(ResultCodeEnum.PARAM_ERROR);
        }
        if (!paymentInfo.getPaymentStatus().equals(PaymentStatusEnum.UNPAID.getStatus())) {
            return;
        }
        //修改支付状态
//        PaymentInfo paymentInfoUpd = new PaymentInfo();
        paymentInfo.setPaymentStatus(PaymentStatusEnum.PAID.getStatus());
        paymentInfo.setTradeNo(paramMap.get("transaction_id"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(paramMap.toString());
        this.updatePaymentInfo(outTradeNo, paymentInfo);

        //修改订单状态
        AdvancePayment advancePayment = advancePaymentService.getById(paymentInfo.getOrderId());
        advancePayment.setPayId(1);
        advancePaymentService.updateById(advancePayment);

        //更新用户表的消费金额
        Integer price = paymentInfo.getPrice();
        Long userId = paymentInfo.getUserId();
        userFeignClient.userPaySuccess(userId,price);

        //确认支付完毕在商品表更新数据，自动创建评论表
        AdvancePaymentVo advancePayment1 = advancePaymentMapper.selectByTradeNo(outTradeNo);
        //估计拿不到这些数据。。。 还是用string接收
        advancePaymentService.packageAdvancePaymentVo(advancePayment1);
        List<Map> param = advancePayment1.getParam();
        param.stream().forEach(item->{
            String name = (String) item.get("name");
            String number = (String) item.get("number");
            //根据name和number去商品表做操作
            commodityService.addNumberByName(name,number);
        });


    }

    @Override
    public PaymentInfo getByOutTrandeNo(String outTradeNo) {
        PaymentInfo paymentInfo = baseMapper.selectOne(new QueryWrapper<PaymentInfo>().eq("out_trade_no", outTradeNo));
        long addressId = paymentInfo.getAddressId();
        UserAddress address = userAddressService.getById(addressId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("address",address.getAddress());
        paymentInfo.setParam(map);
        return paymentInfo;
    }
    /**
     * 通过id获取所有的交易记录
     * @param userId
     * @return  回调给user模块
     */
    @Override
    public List<PaymentInfo> getAllByUserId(long userId) {

        List<PaymentInfo> paymentInfoList = baseMapper.selectList(new QueryWrapper<PaymentInfo>().eq("user_id", userId));
        //数据处理 整理subject中的数据
        for (int i = 0; i < paymentInfoList.size(); i++) {
            if (paymentInfoList.get(i).getPaymentStatus()!=2){
                PaymentInfo paymentInfo = paymentInfoList.get(i);
                paymentInfoList.remove(paymentInfo);
                i--;
            }
            this.packagePaymentInfo(paymentInfoList.get(i));
        }

        return paymentInfoList;

    }

    private void updatePaymentInfo(String outTradeNo, PaymentInfo paymentInfoUpd) {
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no",outTradeNo);
        baseMapper.update(paymentInfoUpd,wrapper);
    }

    private PaymentInfo getPaymentInfo(String outTradeNo, Integer paymentType) {
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("out_trade_no", outTradeNo);
        queryWrapper.eq("payment_type", paymentType);
        return baseMapper.selectOne(queryWrapper);
    }

    public void packagePaymentInfo(PaymentInfo itemVo){



        long addressId = itemVo.getAddressId();

        String address = userAddressService.getAddressById(addressId);
        //条件查询完毕整理数据
        Map<String, Object> address1 = new HashMap();
        address1.put("address", address);
        itemVo.setParam(address1);
        String detail = itemVo.getSubject();
        List<Map> arrayList = new ArrayList<>();
        String[] numberList = detail.substring(0,detail.length()-2).replace("\"","").split("},");
        Arrays.stream(numberList).forEach(list->{
            String[] split = list.split(",");
            HashMap<String, String> map = new HashMap<>();
            String name = split[0].split(":")[1];
            String price = split[1].split(":")[1];
            String types = split[2].split(":")[1];
            String number = split[3].split(":")[1];
            String cart = split[4].split(":")[1];

            map.put("name",name);
            map.put("price",price);
            map.put("types",types);
            map.put("number",number);
            map.put("cart",cart);
            arrayList.add(map);
            itemVo.setList(arrayList);
        });
    }
}
