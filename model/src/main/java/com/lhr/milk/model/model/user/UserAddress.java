package com.lhr.milk.model.model.user;

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
 * @Date:2022/5/26 9:46
 * @Version 1.0
 */
@Data
@ApiModel(value = "用户地址管理")
@TableName("useraddress")
public class UserAddress extends BaseEntity {

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private long userId;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "地址")
    @TableField("is_default")
    private Integer isDefault;

    @ApiModelProperty(value = "地址")
    @TableField("usage_times")
    private long usageTimes;

}
