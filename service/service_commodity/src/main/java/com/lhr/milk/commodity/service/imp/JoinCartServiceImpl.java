package com.lhr.milk.commodity.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.commodity.mapper.JoinCartMapper;
import com.lhr.milk.commodity.service.JoinCartService;
import com.lhr.milk.model.model.commodity.JoinCart;
import com.lhr.milk.model.vo.JoinVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lhr
 * @Date:2022/5/23 22:21
 * @Version 1.0
 */
@Service
public class JoinCartServiceImpl extends ServiceImpl<JoinCartMapper, JoinCart> implements JoinCartService {


    @Override
    public void addJoinList(Long id, JoinVo joinVo) {
        JoinCart joinCart = new JoinCart();
        BeanUtils.copyProperties(joinVo,joinCart);
        joinCart.setUserId(id);
        baseMapper.insert(joinCart);
    }

    @Override
    public List<JoinCart> getJoinList(Long id) {
        QueryWrapper<JoinCart> wrapper = new QueryWrapper<JoinCart>().eq("userId", id);
        List<JoinCart> joinCarts = baseMapper.selectList(wrapper);
        AtomicReference<Integer> totalPrice = new AtomicReference<>(0);
        joinCarts.stream().forEach(joinCart -> {
            HashMap<String, Object> map = new HashMap<>();
            Integer temp = joinCart.getTemp();
            Integer price = joinCart.getPrice();
            totalPrice.updateAndGet(v -> v + price);
            String temps = null;
            String types = null;
            if (temp==1){
                temps = "正常温";
            }else if (temp==2){
                temps = "加冰";
            }else if (temp==3){
                temps = "加热";
            }else {
                temps = "无";
            }
            Integer type = joinCart.getType();
            if (type==1){
                types = "标准糖";
            }else if (type==2){
                types = "少糖";
            }else if (type==3){
                types = "多糖";
            }else {
                types = "无";
            }
            if ("无".equals(temps)&&"无".equals(types)){
                map.put("types","");
            }
            else if ("无".equals(types)){
                map.put("types",temps);
            }else if ("无".equals(temps)){
                map.put("types",types);
            }else {
                map.put("types",temps+","+types);
            }
            map.put("totalPrice",totalPrice);
            joinCart.setParam(map);
        });
        return joinCarts;
    }

    @Override
    public void delAll(Long userId) {
        QueryWrapper<JoinCart> wrapper = new QueryWrapper<JoinCart>().eq("userId", userId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void changeNum( Integer flag, Long id) {

        JoinCart joinCart = baseMapper.selectById(id);
        Integer price = joinCart.getPrice()/joinCart.getNumber();

        //如果num大于1才可以进行以下操作 如果num=1操作完之后就删除
        if (joinCart.getNumber()!=1){
            if (flag==0){
                joinCart.setNumber(joinCart.getNumber()-1);
                joinCart.setPrice(joinCart.getPrice()-price);
                baseMapper.updateById(joinCart);
            }
            else {
                joinCart.setNumber(joinCart.getNumber()+1);
                joinCart.setPrice(joinCart.getPrice()+price);
                baseMapper.updateById(joinCart);
            }
        }
        else {
            baseMapper.deleteById(joinCart);
        }

    }

    @Override
    public void delById(long joinCartId) {
        baseMapper.deleteById(joinCartId);
    }
}
