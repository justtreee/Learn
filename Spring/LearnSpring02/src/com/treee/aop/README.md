# AOP
AOP：Aspect Oriented Programing 面向切面编程。
**AOP概念**
1. AOP：面向切面编程，扩展功能不修改源代码实现。

2. AOP采用横向抽取机制，取代了传统的纵向继承体系重复性代码。

## AOP原理
假设我们要开发一个用户管理系统。
原本的方式，我们需要通过修改源代码来扩展功能。
之后通过继承的方式发展出纵向机制：
![jicheng](https://user-images.githubusercontent.com/15559340/36891839-deafa8b0-1e3d-11e8-998c-1f9f1d2ac3b2.PNG)


### 1. 动态代理
而横向机制的特点：
> 就是代理机制:
> 动态代理:(JDK中使用)
> JDK的动态代理,对实现了接口的类生成代理。
[AOP底层原理解析-动态代理](http://blog.csdn.net/jeffleo/article/details/52226273)
![aop](https://user-images.githubusercontent.com/15559340/36896847-20ef5944-1e4f-11e8-9ca6-76a672956dc6.PNG)

> 而动态代理只能对实现了相应Interface的类使用，如果某个类没有实现任何的Interface，就无法使用动态代理对其产生相应的代理对象。

> 因此：在默认情况下，如果Spring AOP发现目标实现了相应的Interface，则采用动态代理为其生成代理对象实例；而如果目标对象没有实现任何的Interface，Spring AOP会尝试使用CGLIB动态字节码生成类库，为目标对象生成代理对象实例。

### 2. CGLIB动态字节码生成

> 使用CGLIB扩展对象行为的原理是：对目标对象进行继承扩展，为其生成相应的子类，而子类可以通过覆写来扩展父类的行为，只要将横切逻辑的实现放到子类中，然后让系统使用扩展后的目标对象的子类，就可以达到与代理模式相同的效果了。 
> **记住**：相比于动态代理，CGLIB的优势就是，可以为没有实现任何接口的类进行扩展

## AOP操作术语

很多博客的解释太抽象了。

这里我们假设有一个User类，其中有增删改查的方法。

![aopex](https://user-images.githubusercontent.com/15559340/36897655-72fa1ab4-1e52-11e8-9533-8165b27cf5df.PNG)

[这里](http://blog.csdn.net/yuanye348623610/article/details/8823429)也有更多的术语解释。



## 参考链接
[探析Spring AOP（二）：Spring AOP的实现机制](http://blog.csdn.net/jeffleo/article/details/61205623)
[Java设计模式之—静态代理和动态代理](http://blog.csdn.net/jeffleo/article/details/52226273)

[Spring AOP 术语解释](http://blog.csdn.net/yuanye348623610/article/details/8823429)

---

---

# Spring的AOP操作（xml配置）

> 在Spring中使用AspecJ实现AOP
> 1. AspecJ不是Spring的一部分，两者一起使用进行AOP操作。
> 2. Spring2.0之后支持

使用AspecJ实现AOP有两种方式：
1. 基于AspecJ的xml配置
2. 基于AspecJ的注解方式

## AOP操作准备
1. 导入`aspectjrt.jar` `aspectjweaver.jar`
作为第三方的框架，需要下载之后引入到项目中才能让之后的配置正常运行。

2. 创建Spring核心配置，导入aop约束（schema）:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd"> <!-- bean definitions here -->

</beans>
```
### 配置对象
在xml里配置：
```xml
    <!--1. 配置对象-->
    <bean id="book" class="com.treee.aop.Book"></bean>
    <bean id="myBook" class="com.treee.aop.MyBook"></bean>
```

### 使用表达式配置切入点

> 切入点：意为实际要增强的方法。

**常用表达式**
`execution(<访问修饰符>?<返回类型><方法名>(<参数>)<异常>)`

下面给出一些通用切入点表达式的例子。
1. 任意公共方法的执行：
`execution(public * *(..))`
2. 任何一个名字以“set”开始的方法的执行：
`execution（* set*(..))`
3. AccountService接口定义的任意方法的执行：
`execution（* com.xyz.service.AccountService.*(..))`
4. 在service包中定义的任意方法的执行：
`execution（* com.xyz.service.*.*(..))`
5. 在service包或其子包中定义的任意方法的执行：
`execution（* com.xyz.service..*.*(..))`

我们的xml需要再加入这样的设置。

```xml
    <!--2. 配置aop操作-->
    <aop:config>
        <!--2.1配置切入点-->
        <aop:pointcut id="pointcut1" expression="execution(* com.treee.aop.Book.*(..))"></aop:pointcut>

        <!--2.2配置切面
            把增强用到方法上
        -->
        <aop:aspect ref="myBook">
            <!--配置增强类型
                method:增强类里面使用哪个方法作为前置
            -->
            <aop:before method="before1" pointcut-ref="pointcut1"></aop:before>
        </aop:aspect>

    </aop:config>
```

### 定义测试的类
1. 被增强(advice)的类
```java
public class Book {
    public void test(){
        System.out.println("Book test.....");
    }
}
```
2. 增强类（把Book类增强）
```java
public class MyBook {
    public void before1(){
        System.out.println("前置增强.....");
    }//预计先输出这个，再输出Book中的test
```
3. 测试类
```java
    @Test
    public void testAOP(){
        ApplicationContext context = new ClassPathXmlApplicationContext("aspecjAOP.xml");
        Book book = (Book) context.getBean("book");
        book.test();
//        前置增强.....
//        Book test.....
    }
```

### 其他增强操作
**1. 后置增强**
给增强类添加后置增强代码
```java
public class MyBook {
    public void before1(){
        System.out.println("前置增强.....");
    }//预计先输出这个，再输出Book中的test

    public void after1(){
        System.out.println("后置增强.....");
    }//预计先输出test，再输出后置
}
```

在xml中添加配置信息：
```xml
<aop:after method="after1" pointcut-ref="pointcut1"></aop:after>
```

再运行测试类：
```
前置增强.....
Book test.....
后置增强.....
```

**2. 环绕增强**
环绕增强的操作相对复杂，需要一个参数：
```java
//环绕通知
    public void around1(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //方法之前
        System.out.println("方法之前...");

        //执行被增强的方法
        proceedingJoinPoint.proceed();

        //方法之后
        System.out.println("方法之后...");

    }
```

配置文件还是如法炮制：
```xml
<aop:around method="around1" pointcut-ref="pointcut1"></aop:around>
```

运行结果：
```
前置增强.....
方法之前...
Book test.....
方法之后...
后置增强.....
```


## 参考链接
[Spring切入点表达式常用写法](http://blog.51cto.com/lavasoft/172292)