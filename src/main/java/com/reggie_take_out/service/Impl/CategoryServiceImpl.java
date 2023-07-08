package com.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie_take_out.common.CustomException;
import com.reggie_take_out.entity.Category;
import com.reggie_take_out.entity.Dish;
import com.reggie_take_out.entity.Setmeal;
import com.reggie_take_out.mapper.CategoryMapper;
import com.reggie_take_out.service.CategoryService;
import com.reggie_take_out.service.DishService;
import com.reggie_take_out.service.SetmealService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    public DishService dishService;

    @Autowired
    public SetmealService setmealService;
    @Autowired
    public CategoryMapper categoryMapper;

    //删除时考虑其他表中是否关联该id
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0){
            throw new CustomException("删除失败，Dish中有该分类");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 1){
            throw new CustomException("删除失败，SetMeal中有该分类");
        }
        super.removeById(id);
    }


}
