package com.lhr.milk.commodity.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhr.milk.commodity.service.AdvancePaymentService;
import com.lhr.milk.commodity.service.CommodityService;
import com.lhr.milk.commodity.service.CommodityTypeService;
import com.lhr.milk.commodity.service.JoinCartService;
import com.lhr.milk.common.result.Result;
import com.lhr.milk.common.utils.AuthContextHolder;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.model.commodity.CommodityType;
import com.lhr.milk.model.model.commodity.JoinCart;
import com.lhr.milk.model.vo.CommodityQueryVo;
import com.lhr.milk.model.vo.JoinVo;
import com.lhr.milk.model.vo.UserJoinListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/22 22:14
 * @Version 1.0
 */
@Api
@RestController
@RequestMapping("/front/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private CommodityTypeService commodityTypeService;

    @Autowired
    private JoinCartService joinCartService;

    @Autowired
    private AdvancePaymentService advancePaymentService;

    /**
     * 查找出所有的品种信息
     * @return
     */
    @ApiOperation("findCommodityType")
    @GetMapping("/findCommodityType")
    public Result findCommodityType(){
        List<CommodityType> commodityTypeList = commodityTypeService.findCommodityType();
        return Result.ok(commodityTypeList);
    }
    /**
     * 根据id去查询具体的商品信息
     * @return
     */
    @ApiOperation("page")
    @GetMapping("/findCommodity/{page}/{limit}")
    public Result findCommodity(CommodityQueryVo commodityQueryVo,
                                @PathVariable Integer page,
                                @PathVariable Integer limit){

        Page<Commodity> hospitals = commodityService.getCommodityByQuery(page, limit, commodityQueryVo);
        return Result.ok(hospitals);
    }
    /**
     * 根据name模糊查询商品信息
     * @return
     */
    @GetMapping("/findCommodityByName/{name}")
    public Result findCommodityByName(@PathVariable String name){

        List<Commodity> hospitals = commodityService.findCommodityByName(name);
        return Result.ok(hospitals);
    }
    /**
     * 根据id去查询加入购物车的信息
     * @return
     */
    @GetMapping("/findCommodityById/{id}")
    public Result findCommodityById(@PathVariable Integer id){
        Map<String,Object> map = commodityService.findCommodityById(id);
        return Result.ok(map);
    }
    /**
     * 用户加入购物车根据用户id给用户加入redis数据库
     * @return
     */
    @GetMapping("/addJoinList")
    public Result addJoinList(HttpServletRequest request,JoinVo joinVo){
        Long id = AuthContextHolder.getUserId(request);
        //加入sql来实现长时间的存在购物车
        joinCartService.addJoinList(id,joinVo);
        return Result.ok();
    }
    /**
     * 通过用户id拿去购物车中的物品
     * @return
     */
    @GetMapping("/getJoinList")
    public Result getJoinList(HttpServletRequest request){
        Long id = AuthContextHolder.getUserId(request);
        //加入sql来实现长时间的存在购物车
        List<JoinCart> jList = joinCartService.getJoinList(id);
        return Result.ok(jList);
    }
    /**
     * 清空购物车所有的商品
     * @return
     */
    @DeleteMapping("/delAll")
    public Result delAll(HttpServletRequest request){

        joinCartService.delAll(AuthContextHolder.getUserId(request));

        return Result.ok();
    }
    /**
     * 根据id和flag来改变某一个商品的数量 刷新购物车列表
     * @return
     */
    @DeleteMapping("/changeNum/{flag}/{id}")
    public Result changeNum(@PathVariable Integer flag,
                            @PathVariable Long id){
        joinCartService.changeNum(flag,id);
        return Result.ok();
    }

    @PostMapping("/GenerateTable")
    public Result GenerateTable(@RequestBody JSONArray jsonArray,HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId(request);
        advancePaymentService.insert(jsonArray,userId);
        return Result.ok();
    }
}
