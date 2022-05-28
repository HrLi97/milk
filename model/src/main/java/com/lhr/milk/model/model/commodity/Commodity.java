package com.lhr.milk.model.model.commodity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhr.milk.model.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lhr
 * @Date:2022/5/22 21:56
 * @Version 1.0
 */
@Data
@ApiModel(value = "商品信息")
@TableName("commodity")
public class Commodity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "类别的id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "无小0 无中1 无大2 全无3")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "无常温0 无加冰1 无加热2 全无3")
    @TableField("temperature")
    private Integer temperature;

    @ApiModelProperty(value = "销量")
    @TableField("amount")
    private Integer amount;

    @ApiModelProperty(value = "商品名称")
    @TableField("is_hot")
    private Integer isHot;

    @ApiModelProperty(value = "商品名称")
    @TableField("is_new")
    private Integer isNew;

    @ApiModelProperty(value = "商品描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "图片路径")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "商品价格")
    @TableField("price")
    private Integer price;
}
