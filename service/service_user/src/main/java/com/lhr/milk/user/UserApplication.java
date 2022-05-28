package com.lhr.milk.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lhr
 * @Date:2022/5/22 12:16
 * @Version 1.0
 */
@SpringBootApplication()
@ComponentScan(basePackages = "com.lhr")
@EnableFeignClients(basePackages = {"com.lhr"})
@MapperScan("com.lhr.milk.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
