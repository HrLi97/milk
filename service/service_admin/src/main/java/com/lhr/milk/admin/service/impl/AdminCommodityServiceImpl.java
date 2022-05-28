package com.lhr.milk.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.admin.mapper.AdminCommodityMapper;
import com.lhr.milk.admin.service.AdminCommodityService;
import com.lhr.milk.common.exception.MilkException;
import com.lhr.milk.common.result.ResultCodeEnum;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.vo.SaveCommodityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author lhr
 * @Date:2022/5/28 11:08
 * @Version 1.0
 */
@Service
public class AdminCommodityServiceImpl extends ServiceImpl<AdminCommodityMapper, Commodity>
        implements AdminCommodityService {


    @Override
    public void changeNew(long id) {
        Commodity commodity = baseMapper.selectById(id);
        if (commodity==null){
            throw new MilkException(ResultCodeEnum.PARAM_ERROR);
        }
        commodity.setIsNew(100);
        baseMapper.updateById(commodity);
    }

    @Override
    public void changeHot(long id) {
        Commodity commodity = baseMapper.selectById(id);
        if (commodity==null){
            throw new MilkException(ResultCodeEnum.PARAM_ERROR);
        }
        commodity.setIsHot(200);
        baseMapper.updateById(commodity);
    }

    @Override
    public void cancelHotNew(long id) {
        Commodity commodity = baseMapper.selectById(id);
        if (commodity==null){
            throw new MilkException(ResultCodeEnum.PARAM_ERROR);
        }
        commodity.setIsHot(0);
        commodity.setIsNew(0);
        baseMapper.updateById(commodity);
    }
    /**
     * 拿到参数后保存
     * @param saveCommodityVo
     * @return
     */
    @Override
    public void saveCommoditySet(SaveCommodityVo saveCommodityVo) {
        Commodity commodity = new Commodity();
        if (saveCommodityVo==null){
            throw new MilkException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        BeanUtils.copyProperties(saveCommodityVo,commodity);
        baseMapper.insert(commodity);
    }
}
