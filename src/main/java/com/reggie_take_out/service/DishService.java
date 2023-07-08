package com.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie_take_out.dto.DishDto;
import com.reggie_take_out.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品 操作两张表 dish dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
