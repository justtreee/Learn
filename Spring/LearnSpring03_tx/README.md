# Spring 的事务管理

## 事务概念
**1. 什么是事务**  
- 是数据库管理系统执行过程中的一个逻辑单位，由一个有限的数据库操作序列构成。也就是一组操作。

**2. 事务特性**
- 原子性（Atomicity）　
- 一致性（Consistency） 
- 隔离性（Isolation） 
- 持久性（Durability） 

**3. 不考虑隔离性产生读问题**
- 脏读
- 不可重复读
- 虚读(幻读)

**4. 解决读问题**
- 设置隔离级别

## Spring的事务管理API

Spring事务管理有两种方式：编程式事务管理（不用）和声明式事务管理。  
而声明式事务管理又分为基于xml配置文件实现和基于注解实现两种。

## 搭建转账环境

1. 创建一个包括`id`、`username`、`balance`的转账数据库。并初始化几个记录。

2. 创建service和dao类，完成注入关系
- service层又叫业务逻辑层。
- dao层，单纯对数据库操作层，在dao层中不添加业务。

**3. 定义Dao和Service，并配置注入：**

- 定义Dao和Service
```java
public class OrdersDao {
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;
}
```

```java
public class OrdersService {
    public void setOrdersDao(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    private OrdersDao ordersDao;
}
```

- 配置注入:
```xml
<bean id="ordersService" class="com.treee.service.OrdersService">
        <property name="ordersDao" ref="ordersDao"></property>
    </bean>

    <bean id="ordersDao" class="com.treee.dao.OrdersDao">
        <property name="jdbcTemplate" ref="jdbcTemplate"></property>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
```

4. 假设需求：amy向bob转账1000。

- 这对于数据库来说就amy的余额减少1000，bob余额增加1000。

在Dao层中编写数据库操作：
```java
//amy余额减少
    public void decrease(Integer num, String name){
        String sql = "update account set balance = balance - ? where username = ?";
        jdbcTemplate.update(sql, num, name);

    }

    //bob余额增加
    public void increase(Integer num, String name){
        String sql = "update account set balance = balance + ? where username = ?";
        jdbcTemplate.update(sql, num, name);
    }
```

业务逻辑层，写转账业务:
```java

    public void Transfer(){
        //amy转出1000
        ordersDao.decrease(1000,"amy");
        //bob收到1000
        ordersDao.increase(1000,"bob");
    }
```
其实还有更优雅的写法（算了不改了）。

但是，这样并不是事务操作，如果amy转出1000之后突然发生异常，bob就不会收到1000，这样转账就出现了异常。

5. 解决方法

- 添加事务解决，出现异常进行回滚操作

## 声明式事务管理（xml配置）

**第一步：配置事务管理器**

```xml
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
```

**第二步：配置事务增强**

```xml
<!--第二步：配置事务增强-->
    <tx:advice id="txadvice" transaction-manager="transactionManager">
        <!--做事务操作-->
        <tx:attributes>
            <!--设置事务操作的方法匹配规则
                也就是匹配到Transfer方法
            -->
            <tx:method name="Transfer*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
```

**第三步：配置切面**
```xml
<!--第三步：配置切面-->
    <aop:config>
        <!--切入点-->
        <aop:pointcut id="pointcut1" expression="execution(* com.treee.service.OrdersService.*(..))"></aop:pointcut>
        <!--切面-->
        <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut1"
    </aop:config>
```

**测试**

这时候如果在转账业务之间插入异常:
```java
public void Transfer(){
        //amy转出1000
        ordersDao.decrease(1000,"amy");
        //异常
        int i = 10/0;
        //bob收到1000
        ordersDao.increase(1000,"bob");
    }
```

运行测试类之后数据库并没有变化，也就是运行到一半回滚了。说明事务操作成功。

注释掉异常之后功能恢复。

## 声明式事务管理（注解）

而注解方式会简单很多。

**第一步：配置事务管理器**
```xml
<!--第一步：配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
```
与之前一样。

**第二步：开启事务注解**
```xml
<!--第二步：开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>

```

**第三步：注解**
```java
@Transactional
public class OrdersService {
....
```

运行成功，数据库更新。

## 参考链接

[数据库事务的四大特性以及事务的隔离级别](https://www.cnblogs.com/fjdingsd/p/5273008.html)

