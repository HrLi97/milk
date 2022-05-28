package com.lhr.milk.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.vo.SaveCommodityVo;

/**
 * @author lhr
 * @Date:2022/5/28 11:07
 * @Version 1.0
 */
public interface AdminCommodityService extends IService<Commodity> {
    void changeNew(long id);

    void changeHot(long id);

    void cancelHotNew(long id);

    void saveCommoditySet(SaveCommodityVo saveCommodityVo);

}
