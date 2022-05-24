package com.lhr.milk.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lhr
 * @Date:2022/5/23 20:52
 * @Version 1.0
 */
@Data
@ApiModel(description="登录对象")
public class JoinVo {

    private String name;
    private Integer number;
    private Integer temp;
    private Integer type;
    private Integer price;

}
