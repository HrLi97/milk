package com.lhr.milk.user.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author lhr
 * @Date:2022/5/22 13:57
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    /**
     * 发送邮箱列表
     */
    @NotEmpty
    private List<String> tos;

    /**
     * 主题
     */
    @NotBlank
    private String subject;

    /**
     * 内容
     */
    @NotBlank
    private String content;
}
