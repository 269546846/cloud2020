package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-07-19 23:39
 **/

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/save")
    public CommonResult save(@RequestBody Payment payment){
        int result=paymentService.save(payment);
        log.info("********新增数据结果："+result);
        if(result>0)
            return new CommonResult(200,"插入数据成功,serverPort:"+serverPort,result);
        else
            return new CommonResult(401,"插入数据失败",null);
    }

    @GetMapping("/get/{id}")
    public CommonResult get(@PathVariable("id") Long id){
        Payment payment=paymentService.getPaymentById(id);
        log.info("********查询数据结果："+payment);
        if(payment!=null)
            return new CommonResult(200,"查询数据成功,serverPort:"+serverPort,payment);
        else
            return new CommonResult(401,"查询数据失败",null);
    }

    @GetMapping("/get1/{id}")
    public CommonResult get1(@PathVariable("id") Long id){
        return new CommonResult(200,"查询数据成功",id);
    }

    @GetMapping("/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for(String element:services) {
            log.info("******element*****:" + element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance:instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri()+"\t");
        }

        return this.discoveryClient;
    }
}
