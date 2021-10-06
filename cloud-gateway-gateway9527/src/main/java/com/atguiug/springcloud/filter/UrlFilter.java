package com.atguiug.springcloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @Description: url过滤器 获取客户端访问的url
 * @Author: wqw
 * @Date: 2021-10-06 12:21
 **/
@Component
public class UrlFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("经验了UrlFilter过滤器");
        String ip=exchange.getRequest().getURI().getPath();
        System.out.println("ip:"+ip);
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
