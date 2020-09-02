package com.icbc.distributed.transfer;

import com.icbc.distributed.common.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication()
@EnableFeignClients
@EnableDiscoveryClient
public class DistributedTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedTransferApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
