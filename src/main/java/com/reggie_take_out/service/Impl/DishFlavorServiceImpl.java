package com.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie_take_out.entity.DishFlavor;
import com.reggie_take_out.mapper.DishFlavorMapper;
import com.reggie_take_out.service.DishFlaverService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlaverService {
}
