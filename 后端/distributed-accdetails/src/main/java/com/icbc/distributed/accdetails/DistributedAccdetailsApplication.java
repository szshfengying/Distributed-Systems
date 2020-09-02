package com.icbc.distributed.accdetails;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import  com.icbc.distributed.common.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;


@EnableDiscoveryClient
@SpringBootApplication
public class DistributedAccdetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedAccdetailsApplication.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }



}
