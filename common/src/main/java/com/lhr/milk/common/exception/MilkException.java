package com.lhr.milk.common.exception;

import com.lhr.milk.common.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lhr
 * @Date:2022/5/22 15:46
 * @Version 1.0
 */
@Data
@ApiModel("MilkException")
public class MilkException extends RuntimeException{
    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public MilkException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public MilkException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "MilkException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
