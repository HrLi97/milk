package com.lhr.milk.model.model.user;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhr.milk.model.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

/**
 * @author lhr
 * @Date:2022/5/22 12:30
 * @Version 1.0
 */

@Data
@ApiModel(value = "userInfo用户信息")
@TableName("user_info")
public class UserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "电子邮件")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "用户姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "状态（0：锁定 1：正常）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "最后消费时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "last_consumption",fill = FieldFill.UPDATE)
    private Date lastConsumption;

    @ApiModelProperty(value = "总消费金额")
    @TableField("consumption_amount")
    private Integer consumptionAmount;

    @ApiModelProperty(value = "在线状态（0：在线 1：离线）")
    @TableField("on_line")
    private Integer onLine;

    @ApiModelProperty(value = "微信头像路径")
    @TableField("headimgurl")
    private String headImgUrl;

}

