package com.lhr.milk.model.model.commodity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.lhr.milk.model.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/24 14:33
 * @Version 1.0
 */
@Data
@ApiModel(value = "商品信息")
@TableName("advance_payment")
public class AdvancePayment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品价格")
    @TableField("price")
    private Integer price;

    @ApiModelProperty(value = "商品名称")
    @TableField("userId")
    private long userId;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<Integer> joinCartId;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<HashMap<String,Object>> detail;

    @TableField("payId")
    private long payId;

}
