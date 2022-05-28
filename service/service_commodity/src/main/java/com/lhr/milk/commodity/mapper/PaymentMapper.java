package com.lhr.milk.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhr.milk.model.model.order.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lhr
 * @Date:2022/5/26 16:15
 * @Version 1.0
 */
@Mapper
public interface PaymentMapper extends BaseMapper<PaymentInfo> {
}
