package com.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie_take_out.common.R;
import com.reggie_take_out.entity.User;
import com.reggie_take_out.service.UserService;
import com.reggie_take_out.utils.SMSUtils;
import com.reggie_take_out.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if (!StringUtils.isEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info(code);
            //SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
            session.setAttribute(phone,code);
            return R.success("手机验证码发送成功");
        }
        return R.error("发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        String sessioncode = (String) session.getAttribute(phone);
        if (sessioncode!=null && code.equals(sessioncode)){
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(userLambdaQueryWrapper);
            if (user != null){
                log.info("已经注册过");
            }
            else {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);

        }
        return R.error("验证码错误");

    }
}
