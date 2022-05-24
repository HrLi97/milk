package com.lhr.milk.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lhr.milk.model.model.commodity.JoinCart;
import com.lhr.milk.model.vo.JoinVo;

import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/23 22:21
 * @Version 1.0
 */
public interface JoinCartService extends IService<JoinCart> {
    void addJoinList(Long id, JoinVo joinVo);

    List<JoinCart> getJoinList(Long id);

    void delAll(Long userId);

    void changeNum(Integer flag, Long id);

    void delById(long joinCartId);

}
