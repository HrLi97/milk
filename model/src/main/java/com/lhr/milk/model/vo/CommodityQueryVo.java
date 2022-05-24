package com.lhr.milk.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhr
 * @Date:2022/5/23 10:13
 * @Version 1.0
 */
@Data
@ApiModel(description="查询具体商品")
public class CommodityQueryVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "邮箱")
    private Integer isHot;

    @ApiModelProperty(value = "邮箱")
    private Integer isNew;

}
