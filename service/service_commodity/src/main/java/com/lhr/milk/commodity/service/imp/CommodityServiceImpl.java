package com.lhr.milk.commodity.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.commodity.mapper.CommodityServiceMapper;
import com.lhr.milk.commodity.service.CommodityService;
import com.lhr.milk.commodity.service.CommodityTypeService;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.vo.CommodityQueryVo;
import com.lhr.milk.model.vo.JoinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/22 22:25
 * @Version 1.0
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityServiceMapper,
        Commodity> implements CommodityService {

    @Autowired
    private CommodityTypeService commodityTypeService;

    /**
     * 根据id去查询具体的商品信息
     * @return
     */
    @Override
    public Page<Commodity> getCommodityByQuery(Integer page, Integer limit, CommodityQueryVo commodityQueryVo) {

        Page<Commodity> commodityPage = new Page<>(page-1,limit);
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();

        Integer id = commodityQueryVo.getId();
        //如果是有热品新品的标签需要先判断
        if (id!=null){
            if (id==100){
                wrapper.eq("is_new",id);
            }
            //不是的话直接通过id
            else if (id==200){
                wrapper.eq("is_hot",id);
            }
            else{
                wrapper.eq("parent_id",id);
            }
        }
        Page<Commodity> selectPage = baseMapper.selectPage(commodityPage, wrapper);
        return selectPage;
    }

    @Override
    public List<Commodity> findCommodityByName(String name) {
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.like("name",name);
        List<Commodity> commodities = baseMapper.selectList(wrapper);
        return commodities;
    }

    @Override
    public Map<String, Object> findCommodityById(Integer id) {

        Commodity commodity = baseMapper.selectById(id);
        Integer parentId = commodity.getParentId();
        String parentName = commodityTypeService.findCommodityTypeById(parentId);
        long amount = commodity.getAmount();
        String name = commodity.getName();
        String description = commodity.getDescription();
        Integer price = commodity.getPrice();
        Integer type = commodity.getType();
        Integer temperature = commodity.getTemperature();
        String imgUrl = commodity.getImgUrl();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("price",price);
        map.put("parent_name",parentName);
        map.put("type",type);
        map.put("temp",temperature);
        map.put("description",description);
        map.put("amount",amount);
        map.put("imgUrl",imgUrl);
        return map;
    }


}
