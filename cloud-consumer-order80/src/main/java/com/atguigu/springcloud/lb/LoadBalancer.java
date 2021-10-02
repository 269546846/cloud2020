package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-07-24 14:07
 **/
public interface LoadBalancer
{
    ServiceInstance instances(List<ServiceInstance> serviceInstanceList);


}
