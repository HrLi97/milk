package com.lhr.milk.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhr.milk.admin.service.AdminCommodityService;
import com.lhr.milk.admin.service.UserInfoService;
import com.lhr.milk.client.PaymentFeignClient;
import com.lhr.milk.common.result.Result;
import com.lhr.milk.model.model.commodity.Commodity;
import com.lhr.milk.model.model.commodity.CommodityType;
import com.lhr.milk.model.model.user.UserInfo;
import com.lhr.milk.model.vo.AdminCommodityVo;
import com.lhr.milk.model.vo.SaveCommodityVo;
import com.lhr.milk.model.vo.UserQueryVo;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/28 10:48
 * @Version 1.0
 */
@Api
@RestController
@RequestMapping("/admin/author")
public class AdminController {

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    @Autowired
    private AdminCommodityService adminCommodityService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询商品信息 需要调用远程接口
     * @param current
     * @param limit
     * @param adminCommodityVo
     * @return
     */
    @PostMapping("showAll/{current}/{limit}")
    public Result showAll(@PathVariable long current,
                          @PathVariable long limit,
                          @RequestBody(required = false) AdminCommodityVo adminCommodityVo){

        Page<Commodity> page = new Page<>(current, limit);
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();

        if (!StringUtils.isNullOrEmpty(adminCommodityVo.getCommodityName())){
            wrapper.like("name",adminCommodityVo.getCommodityName());
        }
        if (!StringUtils.isNullOrEmpty(adminCommodityVo.getCommodityType())){
            //根据类别名称去查询id
            String commodityName = adminCommodityVo.getCommodityType();
            List<Integer> typeId = paymentFeignClient.getTypeByName(commodityName);
            wrapper.in("parent_id",typeId);
        }

        Page<Commodity> commodityPage = adminCommodityService.page(page, wrapper);
        commodityPage.getRecords().stream().forEach(item->{
            HashMap<String, Object> map = new HashMap<>();
            Integer parentId = item.getParentId();
            String parentName = paymentFeignClient.getTypeNameById(parentId);
            map.put("type",parentName);
            item.setParam(map);
        });
        return Result.ok(commodityPage);
    }

    /**
     * 增加商品为新款
     * @param id
     * @return
     */
    @GetMapping("/changeNew/{id}")
    public Result changeNew(@PathVariable long id){
        adminCommodityService.changeNew(id);
        return Result.ok();
    }
    /**
     * 增加商品为爆款
     * @param id
     * @return
     */
    @GetMapping("/changeHot/{id}")
    public Result changeHot(@PathVariable long id){
        adminCommodityService.changeHot(id);
        return Result.ok();
    }
    /**
     * 取消商品的热和爆
     * @param id
     * @return
     */
    @GetMapping("/cancelHotNew/{id}")
    public Result cancelHotNew(@PathVariable long id){
        adminCommodityService.cancelHotNew(id);
        return Result.ok();
    }
    /**
     * 设置类别选择商品类型
     * @return
     */
    @GetMapping("/findAllType")
    public Result findAllType(){
        List<CommodityType> allType = paymentFeignClient.findAllType();
        return Result.ok(allType);
    }

    /**
     * 拿到参数后保存
     * @param saveCommodityVo
     * @return
     */
    @PostMapping("/saveCommoditySet")
    public Result saveCommoditySet(@RequestBody SaveCommodityVo saveCommodityVo){
        adminCommodityService.saveCommoditySet(saveCommodityVo);
        return Result.ok();
    }

    @GetMapping("/getCommodityById/{id}")
    public Result getCommodityById(@PathVariable long id){
        Commodity commodity = adminCommodityService.getById(id);
        return Result.ok(commodity);
    }

    @PostMapping("/getUser/{current}/{limit}")
    public Result getUser(@PathVariable long current,
                          @PathVariable long limit,
                          @RequestBody(required = false) UserQueryVo userQueryVo){

        Page<UserInfo> page = new Page<>(current, limit);
        String email = userQueryVo.getEmail();
        String name = userQueryVo.getName();
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(email)){
            wrapper.like("email",email);
        }
        if (!StringUtils.isNullOrEmpty(name)){
            wrapper.like("name",name);
        }

        Page<UserInfo> userInfoPage = userInfoService.page(page, wrapper);
        return Result.ok(userInfoPage);

    }


}
