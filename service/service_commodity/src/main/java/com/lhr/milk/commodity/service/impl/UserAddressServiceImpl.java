package com.lhr.milk.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhr.milk.commodity.mapper.UserAddressMapper;
import com.lhr.milk.commodity.service.UserAddressService;
import com.lhr.milk.common.exception.MilkException;
import com.lhr.milk.common.result.ResultCodeEnum;
import com.lhr.milk.model.model.user.UserAddress;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/26 13:23
 * @Version 1.0
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
        implements UserAddressService {

    /**
     * 根绝用户id获取地址 并且设置默认地址
     * @param userId
     * @return
     */
    @Override
    public List<UserAddress> getUserAddress(long userId) {

        List<UserAddress> uList = this.baseMapper.selectList(new QueryWrapper<UserAddress>().eq("user_id", userId));

        uList.stream().forEach(address->{
            Integer isDefault = address.getIsDefault();
            if (isDefault==1){
                HashMap<String, Object> map = new HashMap<>();
                map.put("default",1);
                address.setParam(map);
            }
        });
        return uList;
    }

    @Override
    public String getAddressById(long addressId) {
        UserAddress userAddress = baseMapper.selectById(addressId);
        return userAddress.getAddress();
    }

    @Override
    public void changeAddressDefault(long addressId, Long userId) {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("is_default",1);
        UserAddress address = baseMapper.selectOne(wrapper);
        if (address!=null){
            address.setIsDefault(0);
            baseMapper.updateById(address);
        }
        UserAddress address1 = baseMapper.selectById(addressId);
        if (address1!=null){
            address1.setIsDefault(1);
            baseMapper.updateById(address1);
        }
    }

    @Override
    public void addAddress(String address, Long userId) {
        UserAddress address1 = new UserAddress();
        address1.setUserId(userId);
        address1.setAddress(address);
        baseMapper.insert(address1);
    }
}
