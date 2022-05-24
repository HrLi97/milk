package com.lhr.milk.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author lhr
 * @Date:2022/5/24 14:23
 * @Version 1.0
 */
@Data
@ApiModel(description="登录对象")
public class UserJoinListVo {

    private String name;
    private Integer number;
    private Integer temp;
    private Integer type;
    private Integer price;
    private long userId;
    private long id;
    private Map<String, Object> param;
    private Date createTime;

}
