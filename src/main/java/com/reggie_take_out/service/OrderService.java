package com.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.reggie_take_out.entity.Orders;

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);

}
