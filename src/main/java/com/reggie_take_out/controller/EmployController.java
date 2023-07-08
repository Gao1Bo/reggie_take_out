package com.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie_take_out.common.R;
import com.reggie_take_out.entity.Employee;
import com.reggie_take_out.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmConstraints;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        Employee queryEmployee = employeeService.getOne(lqw);
        //没有查询到返回登陆失败
        if (queryEmployee == null){
            return R.error("没有该用户");
        }
        //密码比对，不一致返回登录失败
        if (!queryEmployee.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //查看员工状态是否已禁用
        if (queryEmployee.getStatus() == 0){
            return R.error("员工已被禁用");
        }
        //登录成功 将员工id存入session并返回登录成功结果
        HttpSession session = request.getSession();
        session.setAttribute("employeeId",queryEmployee.getId());
        return R.success(queryEmployee);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employeeId");
        return R.success("推出成功");
    }

    @PostMapping()
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) request.getSession().getAttribute("employeeId"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employeeId"));
        employeeService.save(employee);
        return R.success("保存成功");
    }

    @GetMapping("/page")
    public R<Page> page( int page, int pageSize,String name){
        log.info("page = {} pageSize = {} name = {}", page, pageSize, name);
        //构造分页构造器
        Page page1 = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper();
        //添加过滤条件
        lqw.like(!StringUtils.isEmpty(name),Employee::getName,name);
        //添加排序
        lqw.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(page1,lqw);
        return R.success(page1);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
        long id = Thread.currentThread().getId();
        log.info("update当前线程id{}",id);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employeeId"));
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable  Long id){
        log.info("根据id{}查询员工信息",id);
        Employee byId = employeeService.getById(id);
        if (byId != null){
            return R.success(byId);
        }
        return R.error("没有查询到对应员工信息");
    }
}
