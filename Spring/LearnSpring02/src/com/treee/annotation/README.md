# Spring 的 bean 管理（注解方式）

## 注解

1. 代码里面的特殊标记，使用注解可以完成功能。

2. 注解写法 `@注解名称（属性名称=属性值）`。

3. 注解可以使用在类、方法、属性上面。

## 注解开发准备工作

首先新建SpringMVC项目，IDEA会将所需的jar包都配置好。

### 引入约束

**注意**：最初创建的`applicationContext.xml`需要引入文档要求的[context schema](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/xsd-configuration.html#xsd-config-body-schemas-context)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"> <!-- bean definitions here -->
    <!--开启注解扫描
        意为到包里面扫描类、方法、属性上面是否注解
    -->
    <context:component-scan base-package="com.treee.annotation"></context:component-scan>
</beans>
```
这样与xml配置相比，减少了文本量。

## 注解创建对象

**1. 示例**

在类的上方添加注解：
```java
@Component(value = "user")      //这一个注解相当与 <bean id="user" class=""/>
public class User {
    public void add(){
        System.out.println("add.........");
    }
}
```
注解方式其实与xml配置等价。

测试代码：
```java 
    @Test
    public void testUser(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) context.getBean("user");
        System.out.println(user);
        user.add();

//        com.treee.annotation.User@35fc6dc4
//        add.........
    }
```
**2. 创建对象有四个注解**

1. @component

2. @Controller ： Web层

3. @Service ：业务层

4. @Repository ：持久层

后三个注解是为了让标注类本身用途清晰，但目前功能都是一样的。

**3. 创建对象单实例或多实例**

在类的上方继续添加一个`@Scope`注解即可：
```java
@Component(value = "user")
@Scope(value="prototype")
public class User {
    public void add(){
        System.out.println("add.........");
    }
}
```

## 注解注入属性

假设我们要创建service类，创建dao类，在service得到dao对象。
```java
@Component(value = "userDao")
public class UserDao {
    public void add(){
        System.out.println("Dao.....");
    }
}
```

```java
@Service("userService")  //可以不写value
public class UserService {
    //得到dao对象
    //1. 定义dao类型属性
    //在dao属性上面使用注解，完成对象注入
    @Autowired
    private UserDao userDao;
    //使用注解不需要setter方法

    public void add(){
        System.out.println("Service......");
        userDao.add();
    }
}
```

**注意**：

注入属性的第一个注解：`@Autowired`。

还有第二种方式：`@Resource`：

```java
@Resource(name = "userDao")  //注意需要name
private UserDao userDao;
```
这时候，我们需要用name指定类，来找到`@Component(value = "userDao")`。


测试UserService调用dao对象属性
```java
    @Test
    public void testService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService);
        userService.add();
//        com.treee.annotation.UserService@448ff1a8
//        Service......
//        Dao.....
    }
```

而在一般情况下：
1. 创建对象操作使用配置文件方式实现；
也就是类的上方不写注解，在xml中配置。
2. 注入属性的操作使用注解方式实现。
也就是在属性上方写`@Resource(name="Dao")`，而不是xml配置。