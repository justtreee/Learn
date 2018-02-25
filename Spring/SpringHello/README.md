> 参考链接：[使用IntelliJ IDEA开发Spring MVC HelloWorld](http://blog.csdn.net/industriously/article/details/52851588)  
> 参考链接：[严重 [RMI TCP Connection(2)-127.0.0.1]解决办法 ](http://www.cnblogs.com/feiyujun/p/6537510.html)
# 开发环境
intellij IDEA 2017.2.6 ultimate版
jdk1.8.0_71
tomcat8.5.24

# 创建工程
打开IntelliJ IDEA 新建一个project，选择spring MVC。接下来按照提示取名为SpringHello。
![1](http://img.blog.csdn.net/20161018185506630)


创建完成的project目录如下。
![11](http://img.blog.csdn.net/20161018185614467)


# 编写代码
## 1. 编写Controller
MVC框架有model、view、controller三部分组成。model一般为一些基本的Java Bean，view用于进行相应的页面显示，controller用于处理网站的请求。

在项目的src目录创建一个包用于存放controller。
![1](http://img.blog.csdn.net/20161018192356785)


在这个包中，新建一个`HelloController`类。
```java
package com.treee.springhello.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping(value = "/hello", method = RequestMethod.GET)
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        model.addAttribute("msg", "Spring MVC Hello World");
        model.addAttribute("name", "treee");
        return "hello";
    }
}
```
解释下上边代码的意思

> 1.@Controller注解：采用注解的方式定义该类为处理请求的Controller类；
> 2.@RequestMapping解：用于定义一个请求映射，value为请求的url，method用以指定该请求类型，一般为get和post，代码中要调用printHello方法，即可访问 hello/hello
> 3.return “hello”：处理完该请求后返回的页面，此请求返回 hello.jsp页面。

## 2. 编写jsp页面
第二步删除web目录下的`index.jsp`，在`web/WEB-INF`中创建一个`views`目录，然后创建一个`hello.jsp`文件。这个`hello.jsp`就是之后在浏览器中显示出来的页面。
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${msg}</title>
</head>
<body>
<h1>${msg}</h1>
<span>${name}</span>
</body>
</html>
```
这时候项目目录如下。
![3](https://user-images.githubusercontent.com/15559340/36638250-c6e0ef44-1a2a-11e8-9577-97adebcd5ec7.PNG)


# 配置xml文件
## 1. 配置web.xml文件
首先打开web/WEB-INF目录下的web.xml文件，如下，把url-pattern的值改为/，用于拦截请求。
> （url-pattern为 / ，说明拦截所有请求,网上有说配置为/*的，我这样配置会出错），并交由Spring MVC的后台控制器来处理。这一项配置是必须的。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/applicationContext.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern> <!--把url-pattern的值改为/，用于拦截请求（url-pattern为 / ，说明拦截所有请求-->
    </servlet-mapping>
</web-app>
```

## 2. 配置dispatcher-servlet.xml文件
在web.xml同级目录下有个dispatcher-servlet.xml的配置文件，他的前缀dispatcher对应上边web.xml中配置的servlet（名称可修改）

打开dispatcher-servlet.xml是空的，我们需要添加一些配置
首先要指定下controller所在的包，这样spring mvc会扫描其中的注解。
```xml
    <context:component-scan base-package="com.treee.springhello.controller"/>
```
```xml
    <!--ViewResolver 视图解析器-->
    <!--用于支持Servlet、JSP视图解析-->
    <bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```
还需要配置下边两项，静态资源访问，开启注解。
```xml
    <!-- 静态资源(js、image等)的访问 -->
    <mvc:default-servlet-handler/>

    <!-- 开启注解 -->
    <mvc:annotation-driven/>
```


最后如下所示：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <context:component-scan base-package="com.treee.springhello.controller"/>

    <!-- 静态资源(js、image等)的访问 -->
    <mvc:default-servlet-handler/>

    <!-- 开启注解 -->
    <mvc:annotation-driven/>

    <!--ViewResolver 视图解析器-->
    <!--用于支持Servlet、JSP视图解析-->
    <bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```


# 运行前的配置
## 1.配置tomcat
使用tomcat提供web服务。下载解压好相应版本的tomcat之后，在bin目录下终端执行./startup.sh，windows执行startup.bat。

在IDEA下，选择run->edit configurations，配置tomcat所在目录，如下借用参考博客的截图。
![3](https://camo.githubusercontent.com/a2dc3c40ea36277d2591e0f3c99ddc79cf6bc34f/687474703a2f2f696d672e626c6f672e6373646e2e6e65742f3230313631303138313931393434383434)
![4](https://camo.githubusercontent.com/8f6ee7e6af2877be72d9f96cf0a0b80a57f8718f/687474703a2f2f696d672e626c6f672e6373646e2e6e65742f3230313631303138313930393038383539)

但这样并没有配置完成。
会产生这样的报错。
```
严重 [RMI TCP Connection(3)-127.0.0.1] org.apache.catalina.core.StandardContext.startInternal One or more listeners failed to start. Full details will be found in the appropriate container log file
严重 [RMI TCP Connection(2)-127.0.0.1] org.apache.catalina.core.StandardContext.listenerStart Skipped installing application listeners due to previous error(s)
```

## 2. 添加引用的jar包

File > Project Structure > Artifacts > 在右侧Output Layout右击项目名，选择Put into Output Root,见下面截图：
![2](https://images2015.cnblogs.com/blog/1083199/201703/1083199-20170312122553373-45181132.png)
执行后，在WEB-INF在增加了lib目录，里面是项目引用的jar包，点击OK。
![2](https://images2015.cnblogs.com/blog/1083199/201703/1083199-20170312122637654-214347815.jpg)

到这一步之后打开浏览器进入[localhost:8080/hello/hello](localhost:8080/hello/hello)  
不再是白屏404了，但还是tomcat返回的500报错：  
`NoClassDefFoundError: javax/servlet/jsp/jstl/core/Config`。

> 使用spring mvc进行开发，使用tomcat容器，通过url映射寻找view的时候，会报错NoClassDefFoundError: javax/servlet/jsp/jstl/core/Config，如果随便去找个jstl包过来放入web-inf/lib会报错，正确的下载地址在[这里](http://archive.apache.org/dist/jakarta/taglibs/standard/binaries/)，下载jakarta-taglibs-standard-1.1.2.zip这个包，解压缩后将standard和jstl两个包放入lib下即可。

File –> Project Structure  –> Artifacts.
![4](https://user-images.githubusercontent.com/15559340/36638490-a52072e2-1a31-11e8-88c8-c08edea0bcc0.PNG)
xuan'zhong
选中两个包导入。

# 运行
![hello1](https://user-images.githubusercontent.com/15559340/36638491-b478bed4-1a31-11e8-9a80-21f2a544345e.PNG)
