package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-09-21 16:01
 **/
@Slf4j
@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result=paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    /**
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
     @HystrixCommand 不指定参数 使用全局兜底函数
     **/
    @HystrixCommand
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        int a=10/0;
        String result=paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    /**
     * @Description:  超时响应进行降级的兜底处理函数
     * @Param:
     * @return:
     * @Author: wqw
     * @Date: 16:57 2021/9/21
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "我是消费者80，对方支付系统繁忙 请10秒钟后再试或者自己运行出错检查自己   /(ㄒoㄒ)/~~";
    }

    /** 
    * @Description:  全局异常处理(fallback)方法
    * @Param:
    * @return:  
    * @Author: wqw 
    * @Date: 18:30 2021/9/21 
    */
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试 /(ㄒoㄒ)/~~";
    }
}
