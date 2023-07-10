package com.reggie_take_out.Filter;

import com.alibaba.fastjson.JSON;
import com.reggie_take_out.common.BaseContext;
import com.reggie_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取URI 只要能唯一标识资源的就是URI，在URI的基础上给出其资源的访问方式的就是URL
        String requestURI = request.getRequestURI();
        //判断请求是否要处理  登录时不要处理 登陆出不要处理 静态资源不用处理
        String[] Urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        //不需要处理放行
        if (check(requestURI,Urls)){
            log.info("放行{}",request.getRequestURI());
            filterChain.doFilter(request,response);
            return;
        }
        //判断登录状态 如果已登录放行
        if (request.getSession().getAttribute("employeeId") != null){
            log.info("用户id为：{}",request.getSession().getAttribute("employeeId"));
            //log.info("放行{}",request.getRequestURI());
            long id = Thread.currentThread().getId();
            log.info("过滤器中当前线程id{}",id);
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employeeId"));
            filterChain.doFilter(request,response);
            return;
        }

        //判断登录状态 如果已登录放行  移动端
        if (request.getSession().getAttribute("user") != null){
            log.info("用户id为：{}",request.getSession().getAttribute("user"));
            //log.info("放行{}",request.getRequestURI());
            long id = Thread.currentThread().getId();
            log.info("过滤器中当前线程id{}",id);
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
            filterChain.doFilter(request,response);
            return;
        }

        //未登录返回谓登录结果  通过输出流方式像客户端页面响应数据
        log.info("拦截到{}",request.getRequestURI());
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

        return;


        //log.info("拦截到请求 {}", request.getRequestURI());
        //filterChain.doFilter(request,response);
    }
    
    public boolean check(String uri, String[] URIs){
        for (String urI : URIs) {
            if (antPathMatcher.match(urI,uri)){
                return true;
            }
        }
        return false;
    }
}
