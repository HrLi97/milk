package com.lhr.milk.commodity.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.model.commodity.AdvancePayment;
import com.lhr.milk.model.vo.CommodityQueryVo;

/**
 * @author lhr
 * @Date:2022/5/24 14:51
 * @Version 1.0
 */
public interface AdvancePaymentService extends IService<AdvancePayment> {
    void insert(JSONArray jsonArray,long userId);

    IPage<AdvancePayment> getPageList(Page<AdvancePayment> pageParam, CommodityQueryVo commodityQueryVo);

}
