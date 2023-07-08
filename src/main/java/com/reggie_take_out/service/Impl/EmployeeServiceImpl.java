package com.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie_take_out.entity.Employee;
import com.reggie_take_out.mapper.EmployMapper;
import com.reggie_take_out.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployMapper, Employee> implements EmployeeService {
}
