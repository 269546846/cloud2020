package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-09-21 20:33
 **/
@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "-----PaymentFallbackService fallback  paymentInfo_OK,O(∩_∩)O";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "-----PaymentFallbackService fallback  paymentInfo_TimeOut,O(∩_∩)O";

    }
}
