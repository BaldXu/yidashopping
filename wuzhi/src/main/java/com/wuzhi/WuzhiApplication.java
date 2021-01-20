package com.wuzhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wuzhi.mapper")
public class WuzhiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WuzhiApplication.class, args);
    }

}
