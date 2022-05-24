package com.lhr.milk.model.model.commodity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhr.milk.model.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhr
 * @Date:2022/5/23 22:15
 * @Version 1.0
 */
@Data
@ApiModel(value = "加入购物车")
@TableName("join_cart")
public class JoinCart extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "商品名称")
    @TableField("number")
    private Integer number;

    @ApiModelProperty(value = "商品名称")
    @TableField("temp")
    private Integer temp;

    @ApiModelProperty(value = "商品名称")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "商品名称")
    @TableField("price")
    private Integer price;

    @ApiModelProperty(value = "商品名称")
    @TableField("userId")
    private long userId;
}
