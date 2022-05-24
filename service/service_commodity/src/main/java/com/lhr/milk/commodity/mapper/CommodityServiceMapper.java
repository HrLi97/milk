package com.lhr.milk.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.model.commodity.CommodityType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lhr
 * @Date:2022/5/22 22:26
 * @Version 1.0
 */
@Mapper
public interface CommodityServiceMapper extends BaseMapper<Commodity> {
}
