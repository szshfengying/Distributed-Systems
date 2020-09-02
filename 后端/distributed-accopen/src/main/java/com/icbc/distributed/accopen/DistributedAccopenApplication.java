package com.icbc.distributed.accopen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.icbc.distributed.accopen.dao")
@EnableDiscoveryClient
@SpringBootApplication()
public class DistributedAccopenApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedAccopenApplication.class, args);
    }

}
