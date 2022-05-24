package com.lhr.milk.commodity.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.interfaces.Join;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.commodity.mapper.AdvancePaymentMapper;
import com.lhr.milk.commodity.service.AdvancePaymentService;
import com.lhr.milk.commodity.service.JoinCartService;
import com.lhr.milk.model.model.commodity.AdvancePayment;
import com.lhr.milk.model.vo.CommodityQueryVo;
import com.lhr.milk.model.vo.UserJoinListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lhr
 * @Date:2022/5/24 14:51
 * @Version 1.0
 */
@Service
public class AdvancePaymentServiceImpl extends ServiceImpl<AdvancePaymentMapper, AdvancePayment>
        implements AdvancePaymentService {

    @Autowired
    private JoinCartService joinCartService;

    @Override
    public void insert(JSONArray jsonArray,long userId) {
        List<UserJoinListVo> joinCarts = jsonArray.toJavaList(UserJoinListVo.class);
        ArrayList<Integer> idList = new ArrayList<>();
        AtomicReference<Integer> totalPrice = new AtomicReference<>(0);
        AtomicReference<String> detail = new AtomicReference<>("");
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
        joinCarts.stream().forEach(cart->{
            HashMap<String, Object> map = new HashMap<>();
            long joinCartId = cart.getId();
            joinCartService.delById(joinCartId);
            idList.add((int) joinCartId);
            map.put("name",cart.getName());
            map.put("types",(String)cart.getParam().get("types"));
            map.put("number",cart.getPrice());
            map.put("price",cart.getPrice());
            map.put("joinCartId",joinCartId);
            totalPrice.set((Integer) cart.getParam().get("totalPrice"));
            hashMaps.add(map);
        });

        AdvancePayment advancePayment = new AdvancePayment();
        advancePayment.setJoinCartId(idList);
        advancePayment.setUserId(userId);
        advancePayment.setPrice(totalPrice.get());
        advancePayment.setDetail(hashMaps);

        baseMapper.insert(advancePayment);
    }

    @Override
    public IPage<AdvancePayment> getPageList(Page<AdvancePayment> pageParam, CommodityQueryVo commodityQueryVo) {



        return null;
    }
}
