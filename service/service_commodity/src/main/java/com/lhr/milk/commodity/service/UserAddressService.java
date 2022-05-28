package com.lhr.milk.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.model.user.UserAddress;

import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/26 13:23
 * @Version 1.0
 */
public interface UserAddressService extends IService<UserAddress> {
    List<UserAddress> getUserAddress(long userId);

    String getAddressById(long addressId);

    void changeAddressDefault(long addressId, Long userId);

    void addAddress(String address, Long userId);
}
