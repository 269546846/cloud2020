package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 * @Author: wqw
 * @Date: 2021-07-19 23:36
 **/

public interface PaymentService {

    public int save(Payment payment);

    public Payment getPaymentById(Long id);
}
