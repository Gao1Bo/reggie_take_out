# 瑞吉外卖

## 1、环境搭建

![image-20230701225440314](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230701225440314.png)

开启驼峰命名  例如 实体属性 idNumber 对应表字段 id_number



![image-20230701224209562](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230701224209562.png)



解决只能识别static目录下的静态资源问题

![image-20230701224035669](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230701224035669.png)

识别静态资源

```java
registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
```

args1：请求路径       args2：静态页面路径  是 `"classpath:/backend/"`而不是 `"classpath:/backend"`



## 2、用户登录功能

### 过滤器

使用过滤器实现未登录无法访问的功能

实现自定义过滤器

![image-20230702203303322](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230702203303322.png)

urlPatterns 过滤资源

![image-20230702203405619](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230702203405619.png)

启动类添加 @ServletComponentScan  添加后才能扫描到@WebFilter注解

补全业务逻辑

![image-20230703083243918](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703083243918.png)

request.js 中设置了响应拦截器   当data.msg为NOTLOGIN时返回登录页面， 因此被拦截时响应数据设为R.error("NOTLOGIN");

![image-20230703083330265](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703083330265.png)

### 问题：

点击登录后发现页面被拦截，发现session中并不存在"employeeId"

发现controller层代码有误，

![image-20230703085449950](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703085449950.png)

此处存入的应为查询到的employ的id，而非请求体中的employ，请求体中的employ只有username和password属性



## 3、新增员工

![image-20230703090825056](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703090825056.png)

前端页面通过POST方式传给服务器一个JSON格式的employ

![image-20230703102344390](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703102344390.png)

### 异常处理器 

保存的username在数据库中有唯一约束，如果重复会出现异常，设置全局异常处理器

![image-20230703102413143](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703102413143.png)

`@ControllerAdvice`  指定作用范围

`@ResponseBody` 返回的是响应体，而不是视图处理器

`@ExceptionHandler` 指定处理的异常类型

## 4、员工分页查询

![image-20230703103049423](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703103049423.png)

## 5、启用禁用员工账号

### 设置消息转换器

## 6、编辑员工信息

![image-20230703144200598](C:\Users\GaoYibo\AppData\Roaming\Typora\typora-user-images\image-20230703144200598.png)

和添加公用add.html  

### 公共字段填充

### 线程