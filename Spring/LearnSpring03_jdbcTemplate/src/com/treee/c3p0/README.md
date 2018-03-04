# 使用Spring配置连接池与注入操作

[上一篇博客](https://github.com/justtreee/Learn/tree/master/Spring/LearnSpring03_jdbcTemplate/src/com/treee/jdbc)的重复模板形式的代码很多，而这也是Spring着力避免的。

## 1. 导入jar并配置c3p0
在开发中, 频繁的开关连接是一件非常消耗资源和时间的操作, 会导致程序执行的效率非常的低下，创建连接要花的时时间一般比对数据库的数据增删改查的时间还要长！ 
因此我们可以在程序中创建容器(连接池), 在程序启动时就初始化一批连接放在连接池中, 当用户需要时,就从连接池中获取一个连接, 用完连接之后再将连接还回连接池中, 这样就可以实现连接的复用, 减少连接开关的次数, 提高程序执行的效率。

这里我们使用c3p0连接池。
[c3p0-0.9.5.2.jar和mchange-commons-java 0.2.12.jar下载地址](https://jar-download.com/explore-java-source-code.php?a=c3p0&g=com.mchange&v=0.9.5.2&downloadable=1)

如果是原始的代码，我们需要在jdbcTemplate中new一个`ComboPooledDataSource`并把数据库信息赋值给这个对象，但在Spring下，我们就可以用xml配置文件来简化。

```xml
<!--配置c3p0连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--注入属性值-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql:///jdbc"></property>
        <property name="user" value="root"></property>
        <property name="password" value=""></property>
    </bean>
```

## 2. 定义对象并配置

假设我们有一个`UserService`类和`UserDao`类。
```java
public class UserDao {
    public void add(){
        //向数据库添加
    }
}

```

```java
public class UserService {
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserDao userDao;

    public void add(){
        userDao.add();
    }
}
```

在xml中配置：
```xml
<bean id="userService" class="com.treee.c3p0.UserService">
        <property name="userDao" ref="userDao"></property>
    </bean>
    <bean id="userDao" class="com.treee.c3p0.UserDao"></bean>

```

## 3. 在Dao中创建jdbcTemplate对象并注入数据库信息
```java
public class UserDao {
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    public void add(){
        //
    }
}
```

在xml中配置：
```xml
<bean id="userDao" class="com.treee.c3p0.UserDao">
        <!--注入jdbcTemplate对象-->
        <property name="jdbcTemplate" ref="jdbcTemplate"></property>
    </bean>

    <!--创建jdbcTemplate对象-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate"></bean>
```

而这时候的`jdbcTemplate`中并没有相应的数据库信息，而我们之前已经在连接池中已经配置好了，所以我们需要把`dataSource`注入到`jdbcTemplate`中。

```xml
<!--创建jdbcTemplate对象-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <!--把dataSource（数据库信息）注入到模板类中-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
```

## 4. 编写sql语句并测试

在UserDao中设置好sql语句（如同[上一篇博客](https://github.com/justtreee/Learn/tree/master/Spring/LearnSpring03_jdbcTemplate/src/com/treee/jdbc)一样），使用模板类调用。
```java
public void add(){
        String sql = "insert into tempspring values (?,?)";
        jdbcTemplate.update(sql, "4", "david");
    }
```

测试代码如下：
```java
public class TestService {
    @Test
    public void testDemo(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserService service = (UserService) context.getBean("userService");
        service.add();

    }
}
```

查看数据库发现插入成功。

## 参考链接

[JAVA连接池的作用与C3P0连接池的使用](http://blog.csdn.net/u012658005/article/details/78754863)
[使用Spring JDBCTemplate简化JDBC的操作](http://www.cnblogs.com/lichenwei/p/3902294.html)