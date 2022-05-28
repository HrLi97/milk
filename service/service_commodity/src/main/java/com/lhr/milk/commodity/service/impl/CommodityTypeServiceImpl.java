package com.lhr.milk.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.commodity.mapper.CommodityTypeMapper;
import com.lhr.milk.commodity.service.CommodityTypeService;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.model.commodity.CommodityType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/23 10:38
 * @Version 1.0
 */
@Service
public class CommodityTypeServiceImpl extends ServiceImpl<CommodityTypeMapper,
        CommodityType> implements CommodityTypeService {
    /**
     * 查找出所有的品种信息
     * @return
     */
    @Override
    public List<CommodityType> findCommodityType() {

        List<CommodityType> cList = baseMapper.selectList(new QueryWrapper<CommodityType>().eq("is_deleted", 0));
        return cList;

    }

    @Override
    public String findCommodityTypeById(Integer parentId) {

        CommodityType commodityType = baseMapper.selectById(parentId);
        return commodityType.getName();

    }

    @Override
    public List<CommodityType> findAll() {
        return baseMapper.selectList(new QueryWrapper<CommodityType>());
    }

    @Override
    public List<CommodityType> getIdByName(String name) {
        return baseMapper.selectList(new QueryWrapper<CommodityType>().like("name",name));
    }
}
