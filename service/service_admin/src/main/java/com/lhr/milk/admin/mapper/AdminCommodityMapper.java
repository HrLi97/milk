package com.lhr.milk.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhr.milk.model.model.commodity.Commodity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lhr
 * @Date:2022/5/28 11:08
 * @Version 1.0
 */
@Mapper
public interface AdminCommodityMapper extends BaseMapper<Commodity> {
}
