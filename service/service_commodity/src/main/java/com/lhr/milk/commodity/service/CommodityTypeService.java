package com.lhr.milk.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.model.commodity.CommodityType;
import com.lhr.milk.model.vo.JoinVo;

import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/23 10:38
 * @Version 1.0
 */
public interface CommodityTypeService extends IService<CommodityType> {
    List<CommodityType> findCommodityType();


    String findCommodityTypeById(Integer parentId);

    List<CommodityType> findAll();

    List<CommodityType> getIdByName(String name);

}
