package com.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie_take_out.entity.SetmealDish;
import com.reggie_take_out.mapper.SetmealDishMapper;
import com.reggie_take_out.service.SetmealDishService;
import com.reggie_take_out.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
