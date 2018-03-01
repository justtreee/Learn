# Spring 的 IOC 入门案例
## 一、概念
### 核心两部分
(1). aop：面向切面编程，扩展功能不是修改源代码实现。

(2). ioc：控制反转，
- 在没有spring的时候：比如有一个类，类里面有一个不是静态的方法，要调用类里面的方法，就需要创建类的对象，使用对象调用方法，而创建类对象的过程，需要 new 出来对象。
- 而spring出现后：把对象的创建不在通过new的方式实现，而是交给spring配置创建类对象。

### spring是一站式框架
spring为javaee三层结构中都提供了不同的解决技术。
- web层： springMVC
- service层：spring的ioc
- dao层：spring的jdbcTemplate

------------------

## 二、spring的ioc
ioc操作分两部分
- ioc的配置文件方式
- ioc的注解方式

### ioc的底层原理

所有从helloworld开始学习java的人都知道，最原始的java代码，一个类定义了方法后，调用这个方法要new这个类的对象。
```java
User user = new User();
user.add();
```
如果方法一改变（比如add()改名为add1()），那么之后所有代码的相应调用都需要修改代码。缺陷也就是耦合度太高。

所以有了**工厂模式解耦和**的改进。

通俗点说，就是增加了一个工厂作为衔接（？）。

```java
public class UserService{
	public void add(){}
}
//创建工厂类
public class Factory{
	public static UserService getService(){
		return new UserService();
	}
}
//使用工厂模式
public class UserServlet{
	UserService s = Factory.getService();
}
```

但是这个改进依然存在之前的问题：servlet和工厂类耦合。

这里也体现了高内聚低耦合的思想。

#### 1. ioc底层原理实用技术
(1) xml配置文件
(2) dom4j解决xml
(3) 工厂设计模式
(4) 反射
![ioc-principal](https://user-images.githubusercontent.com/15559340/36713512-0bfbf7dc-1bc9-11e8-9701-b93c439c4a80.PNG)

这样的话，类之间的耦合度降低。如果`UserService`的文件路径改变，只需要修改xml即可。

### 入门案例

1. 通过IDEA创建Spring MVC项目。

2. 作为入门案例，我们先新建一个User类。
```java
public class User {
    public void add(){
        System.out.println("add.....");
    }

    public static void main(String[] args) {
        //原始做法：
        User user = new User();
        user.add();
    }
}
```

3. 在`web/WEB-INF/applicationContext.xml`中配置。这个文件的名字与位置可以任意修改，但官方推荐为`applicationContext.xml`。（虽然默认是放在WEB-INF里面，但在第4步机会发现会出错）  
IDEA会在创建项目之后直接生成该文件，并且已经在初始化了必要的内容，我们需要在`<beans>`标签中配置。

该xml文件内容如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--以上内容为IDEA的SpringMVC自动生成，如果没有可以在官方文档中寻找-->
    <!--ioc入门-->
    <bean id="user" class="com.treee.ioc.User"></bean>
</beans>
```

4. 创建TestIOC类。通过Spring环境加载配置文件，并创建对象。

```java
public class TestIOC {
    @Test
    public void testUser(){
        //1. 加载spring配置文件，根据配置文件创建对象
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        //2. 得到配置创建的对象
        User user = (User)context.getBean("user");
        System.out.println(user); //输出地址：com.treee.ioc.User@1c3a4799
        user.add(); //输出 add.....
    }
}
```
如果`applicationContext.xml`此时是在WEB-INF里面，会发生报错。这时候把xml移到src下是最简便的方法。

[关于applicationContext.xml cannot be opened because it does not exist的解决](http://www.cnblogs.com/doublesong/archive/2012/08/10/2632312.html)
