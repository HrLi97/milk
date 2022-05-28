package com.lhr.milk.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhr.milk.model.model.commodity.AdvancePayment;
import com.lhr.milk.model.vo.AdvancePaymentVo;
import com.lhr.milk.model.vo.OrderQueryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/24 14:51
 * @Version 1.0
 */
@Mapper
public interface AdvancePaymentMapper extends BaseMapper<AdvancePayment> {
    List<AdvancePaymentVo> selectAll(OrderQueryVo orderQueryVo);

    AdvancePaymentVo selectByorderId(long orderId);


    AdvancePaymentVo selectByTradeNo(String outTradeNo);

}
