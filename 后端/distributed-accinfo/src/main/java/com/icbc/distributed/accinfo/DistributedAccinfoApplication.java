package com.icbc.distributed.accinfo;

import  com.icbc.distributed.common.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients(basePackages = "com.icbc.distributed.accinfo.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class DistributedAccinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedAccinfoApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
