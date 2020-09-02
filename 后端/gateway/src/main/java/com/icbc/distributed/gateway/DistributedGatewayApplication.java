package com.icbc.distributed.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@EnableDiscoveryClient
@SpringBootApplication
public class DistributedGatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(DistributedGatewayApplication.class, args);
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }
//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        //常见的demo，拦截get请求，往请求头添加一个参数，然后转到http://httpbin.org:80
//        return builder.routes()
//                .route(p -> p
//                        .path("/details")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://127.0.0.1:25002/test/test"))
//                .build();
//    return builder.routes()
//        .route(p -> p
//            .path("/get")
//            .filters(f -> f.addRequestHeader("Hello", "World"))
//            .uri("http://httpbin.org:80"))
//        .build();
//    }


}
