package com.atguitu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Hystrix 服务降级、服务熔断、服务限流
 * @Author: wqw
 * @Date: 2021-07-24 17:07
 **/
@Service
public class PaymentService {

    /**
     * @Description:
     * @Param: 正常访问，肯定Ok
     * @return:
     * @Author: wqw
     * @Date: 17:10 2021/7/24
     */
    public String paymenInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "  paymentInfo)_OK,id:" + id + "\t" + "o(n_n)o哈哈~";
    }

    /**
     * @Description: 模拟超时访问接口
     * fallbackMethod 降级容端兜底函数
     * HystrixProperty ：配置允许响应的最大时间
     * @Param:
     * @return:
     * @Author: wqw
     * @Date: 16:48 2021/9/21
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymenInfo_TimeOut(Integer id) {

        int TimeNumber = 4000;
        try {
            TimeUnit.MILLISECONDS.sleep(TimeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + "  paymenInfo_TimeOut,id:" + id + "\t" + "o(n_n)o哈哈~";
    }

    /**
    * @Description:  超时响应进行降级的兜底处理函数
    * @Param:
    * @return:
    * @Author: wqw
    * @Date: 16:57 2021/9/21
    */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "  8001系统繁忙或者运行报错，请稍后再试,id:" + id + "\t" + "/(ㄒoㄒ)/~~";
    }

    /**
     * 上面为服务降级代码案例
     *  下面为服务熔断代码案例
     */

    //*******服务熔断注解配置
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //时间窗口期内的请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //在时间窗口期的请求失败率达到多少后跳闸
            //四个配置意思就是：开启断路器，并且在10s内10次的请求失败率达到60%,将会进行熔断.
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException("********Id 不能为负数");
        }
        String serialNumber= UUID.randomUUID().toString();
        return Thread.currentThread().getName() +"\t"+"调用成功，流水号:"+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id){
        return "id不能为负数，请稍后再试，/(ㄒoㄒ)/~~ id:"+id;
    }
}
