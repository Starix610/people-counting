package com.weiyun.peoplecounting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.weiyun.peoplecounting.mapper")
public class PeopleCountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeopleCountingApplication.class, args);
    }

}
