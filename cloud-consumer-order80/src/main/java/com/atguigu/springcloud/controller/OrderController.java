package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-07-20 02:24
 **/
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001";

    public static final String PAYMENT_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/payment/save")
    public CommonResult<Payment> save(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/save",payment,CommonResult.class);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult get(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    /*@GetMapping("/payment/save")
    public CommonResult<Payment> save(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/save",payment,CommonResult.class);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult get(@PathVariable("id") Long id){
       return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }*/
}
