# Spring 的 AOP 操作（注解）

## 基于aspecj 的注解aop

使用注解方式实现aop操作。

**第一步：创建对象**
假设我们有一个Book类和MyBook类。在配置文件中创建对象。（记得在前面加入aop约束和aspecj的两个jar包）
```xml
    <bean id="book" class="com.treee.aop.Book"></bean>
    <bean id="myBook" class="com.treee.aop.MyBook"></bean>
```

**第二步：在Spring核心配置文件中，开启aop操作**
```xml
<!--开启aop操作-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```

**第三步：使用注解**

```java
@Aspect
public class MyBook {

    //在方法上面使用注解完成增强配置
    @Before(value = "execution(* com.treee.aop.Book.*(..))")
    public void before1(){
        System.out.println("before.........");
    }
}

```

**第四步：测试**

```java
    @Test
    public void testAOP(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Book book = (Book) context.getBean("book");

        book.test();
        //before.........
        //Book test....
    }
```


