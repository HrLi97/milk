package com.lhr.milk.admin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lhr
 * @Date:2022/5/28 13:24
 * @Version 1.0
 */
@Configuration
@MapperScan("com.lhr.milk.admin.mapper")
public class PageConfig {
    /**
     * 分页插件的mp配置
     */

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
