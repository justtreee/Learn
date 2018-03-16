# 基于Spring Security权限控制与使用Ajax调用类

当我们能够[基于Spring Security实现权限控制](http://blog.csdn.net/u012702547/article/details/54319508) 之后，我们可以为管理员添加功能（比如按钮）而普通用户无法使用：

```html
<div sec:authorize="hasRole('ROLE_ADMIN')">
            <p class="bg-info" th:text="${msg.extraInfo}"></p>
            <button type="button" onclick="light1()">Light 1</button>
           
        </div>
        <div sec:authorize="hasRole('ROLE_USER')">
            <p class="bg-info">非管理员无法操作</p>
        </div>
```

**需求**：  

当我们在浏览器中点击`Light 1`按钮，会通过Ajax发出POST请求，从而调用一个类，类中的方法会被调用。

## 1. 添加js配置
首先在`resources`下添加js文件：
![default](https://user-images.githubusercontent.com/15559340/37527431-ff33dfa4-296c-11e8-947d-dabada5c93e4.PNG)


然后在`index.html`的头部加入jquery.js路径。
```html
<script type="text/javascript" src="js/jquery.js"></script>
```

## 2. 编写Ajax函数

在`index.html`中添加function。
```html
<script text="javaScript">
    function light1() {
        $.ajax({
            type: "POST",
            url: "/light1",    //向后端请求数据的url
            async: false,
            success: function (data) {
                alert(data);
            }
        });
    }
</script>
```
这里的url是指之后类中方法的注解的名字。

## 3. 注解方式调用类中的方法

```java
@Controller
public class Light {
    @PostMapping("/light1")
    @ResponseBody
    public String light1(){
        System.out.println("light1");
        return "light1";
    }
}
```
当我们点击网页上的按钮之后，控制台会输出`light1`，表示方法调用成功。

但实际运行之后，在chrome中查看F12，会发现返回了403错误。

## 4. 403错误解决方法

> **原因分析**：如果是使用的是 Java 代码配置 Spring Security，那么 CSRF 保护默认是开启的，那么在 POST 方式提交表单的时候就必须验证 Token，如果没有，那么自然也就是 403 没权限了。
>
> **解决方式**：这里主要有[两种解决方式](http://blog.csdn.net/t894690230/article/details/52404105)

我使用第一种简单的方法，暂时不知道是否会有安全方面的缺陷。

1. 关闭 CSRF 保护：
在`WebSecurityConfig`的`configure`下添加`http.csrf().disable();`

```java
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout()
                .permitAll();
        http.csrf().disable();
    }
```


## 参考链接

[Spring MVC Post请求返回403错误，Get请求却正常？](http://blog.csdn.net/t894690230/article/details/52404105)