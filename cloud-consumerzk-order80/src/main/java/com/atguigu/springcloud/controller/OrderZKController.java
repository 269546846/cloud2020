package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-07-22 15:41
 **/
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderZKController {

    public static final String INVOKE_URI="http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/payment/zk")
    public String paymentinfo(){
        String res=restTemplate.getForObject(INVOKE_URI+"/payment/zk", String.class);
        return res;
    }
}
