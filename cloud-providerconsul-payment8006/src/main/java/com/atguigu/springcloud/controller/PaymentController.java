package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-07-22 17:49
 **/

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/consul")
    public String paymentzk(){
        return "springcloud with consul:"+serverPort+"\t"+ UUID.randomUUID().toString();
    }

}
