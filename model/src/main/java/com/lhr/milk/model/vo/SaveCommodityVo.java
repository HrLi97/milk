package com.lhr.milk.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lhr
 * @Date:2022/5/28 16:37
 * @Version 1.0
 */
@Data
@ApiModel(description="查询具体商品")
public class SaveCommodityVo {

    private String name;
    private Integer parentId;
    private String imgUrl;
    private Integer price;
    private Integer amount;
    private Integer temperature;
    private Integer type;
    private String description;

}
