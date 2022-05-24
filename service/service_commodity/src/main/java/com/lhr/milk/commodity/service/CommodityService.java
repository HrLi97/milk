package com.lhr.milk.commodity.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.model.commodity.CommodityType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.vo.CommodityQueryVo;
import com.lhr.milk.model.vo.JoinVo;

import java.util.List;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/22 22:15
 * @Version 1.0
 */
public interface CommodityService extends IService<Commodity> {

    Page<Commodity> getCommodityByQuery(Integer page, Integer limit, CommodityQueryVo commodityQueryVo);

    List<Commodity> findCommodityByName(String name);

    Map<String, Object> findCommodityById(Integer id);

}
