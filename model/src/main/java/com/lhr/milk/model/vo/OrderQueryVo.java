package com.lhr.milk.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author lhr
 * @Date:2022/5/24 16:41
 * @Version 1.0
 */
@Data
@ApiModel(description="查询具体商品")
public class OrderQueryVo {

    private Date creatTime;
    private String commodityName;
    private boolean isPay;

}
