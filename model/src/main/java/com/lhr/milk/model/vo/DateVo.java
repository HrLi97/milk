package com.lhr.milk.model.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author lhr
 * @Date:2022/5/26 20:14
 * @Version 1.0
 */
@Data
public class DateVo {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date getTime;

}
