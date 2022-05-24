package com.lhr.milk.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhr
 * @Date:2022/5/22 15:37
 * @Version 1.0
 */

@Data
@ApiModel(description="登录对象")
public class LoginVo {

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String code;

    @ApiModelProperty(value = "IP")
    private String ip;
}
