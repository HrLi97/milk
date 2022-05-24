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
 * @Date:2022/5/22 22:02
 * @Version 1.0
 */
@Data
@ApiModel(value = "商品类别")
@TableName("type")
public class CommodityType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "数量")
    @TableField("number")
    private long number;

    @ApiModelProperty(value = "xiao量")
    @TableField("total_consumption")
    private long totalConsumption;

}
