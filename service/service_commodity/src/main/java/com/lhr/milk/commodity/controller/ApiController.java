package com.lhr.milk.commodity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhr.milk.commodity.service.AdvancePaymentService;
import com.lhr.milk.common.result.Result;
import com.lhr.milk.model.model.commodity.AdvancePayment;
import com.lhr.milk.model.vo.CommodityQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lhr
 * @Date:2022/5/24 16:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/order")
public class ApiController {

    @Autowired
    private AdvancePaymentService advancePaymentService;

    @GetMapping("/getPageList/{page}/{limit}")
    public Result getPageList(HttpServletRequest request,
                              @PathVariable int page,
                              @PathVariable int limit,
                              CommodityQueryVo commodityQueryVo){

        Page<AdvancePayment> pageParam = new Page<>(page, limit);
        IPage<AdvancePayment> pageModel = advancePaymentService.getPageList(pageParam, commodityQueryVo);
        return Result.ok(pageModel);

    }

}
