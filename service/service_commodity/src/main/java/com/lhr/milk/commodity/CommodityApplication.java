package com.lhr.milk.commodity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lhr
 * @Date:2022/5/22 22:11
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lhr"})
@ComponentScan(basePackages = "com.lhr")
@MapperScan("com.lhr.milk.commodity.mapper")
public class CommodityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommodityApplication.class,args);
    }
}
