package com.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie_take_out.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);

}
