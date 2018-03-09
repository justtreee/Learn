# 使用JPA访问数据库

本文通过JPA，在SpringBoot框架下实现了简单的数据库访问。最终演示的时候我们可以通过在浏览器中输入特定的url从而对数据库进行操作，而这些操作是通过java代码限定的。

## 第一步：创建项目
通过IDEA的Spring initializer创建项目，并选中Thtmeleaf、JPA两项。并导入mysql-connector-java的jar包。

## 第二步：配置数据库基本信息
在`application.properties`中配置：
```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/jdbc
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jackson.serialization.indent_output=true
```

> 1.第一行表示驱动的名称，这个和具体的数据库驱动有关，视情况而定，我这里使用了MySql数据库，所以驱动名为com.mysql.jdbc.Driver 
> 2.第二行表示数据库连接地址，当然也是视情况而定 
> 3.第三四行表示数据库连接的用户名和密码 
> 4.第五行则配置了实体类维护数据库表结构的具体行为，update表示当实体类的属性发生变化时，表结构跟着更新，这里我们也可以取值create，这个create表示启动的时候删除上一次生成的表，并根据实体类重新生成表，这个时候之前表中的数据就会被清空；还可以取值create-drop，这个表示启动时根据实体类生成表，但是当sessionFactory关闭的时候表会被删除；validate表示启动时验证实体类和数据表是否一致；none表示啥都不做。 
> 5.第六行表示hibernate在操作的时候在控制台打印真实的sql语句 
> 6.第七行表示格式化输出的json字符串

## 第三步：定义实体类
假设我们有一个User类，有相应的id，username和password。

我们使用注解`@Entity`，表示这是一个和数据库表映射的实体类，当我们在最后运行整个项目之后，会在MySQL中自动创建一张User表。

```java
@Entity
@NamedQuery(name = "User.withUsernameAndPasswordQuery",
query = "select u from User u where u.username=?1 and u.password=?2")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    
    //篇幅原因省略构造函数与getter和setter
}
```

在属性id上我们添加了`@Id`注解，表示该字段是一个id，`@GeneratedValue`注解则表示该字段自增。

`@NamedQuery`注解表示一个NamedQuery查询，这里一个名称代表一个查询语句，我们一会可以在控制器中直接调用`@NamedQuery`中的`withUsernameAndPasswordQuery`方法，该方法代表的查询语句是`select u from User u where u.username=?1 and u.password=?2`。


## 第四步：定义数据访问接口
因为我们要通过JPA来访问数据库，我们需要一个继承了JpaRepository的UserRepository。来调用自定义的sql语句。除此之外，继承接口后其实自带诸如`findAll`，`save`等方法，我们不声明也可以在之后的代码中调用。

```java
public interface UserRepository extends JpaRepository<User, Long>{
    //自定义的sql
    @Query("select u from User u where u.username=:username and u.password=:password")
    User withUsernameAndPasswordQuery(@Param("username") String username, @Param("password") String password);
}
```

## 第五步：编写Controller

所谓Controller，就是定义好如何在浏览器上通过相应url来访问数据库（目前的理解）。

```java
@RestController
public class DataController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("save") //不需要斜杠
    public User save(String username, String password){
        User user = userRepository.save(new User(null, username, password));
        return user;
    }//http://localhost:8080/save?username=user1&password=user1

    @RequestMapping("query")
    public User query(String username, String password){
        User user = userRepository.withUsernameAndPasswordQuery(username, password);
        return user;
    }//http://localhost:8080/query?username=admin&password=admin

    @RequestMapping("queryall")
    @ResponseBody
    public List<User> queryAll(){
        List<User> list = new ArrayList<User>();
        list = userRepository.findAll();
        return list;
    }//http://localhost:8080/queryall
}

```
这三个方法对应了三个url，输入注释中的url就能在数据库中添加记录或查询。

浏览器会返回json格式的结果。

**待解决：用url局限很大，需要更贴近用户使用体验的方式**

## 参考链接
[基于Spring boot的Spring data jpa连接MySQL数据库](http://blog.csdn.net/jinbaosite/article/details/77587600)

[初识在Spring Boot中使用JPA](http://blog.csdn.net/u012702547/article/details/53946440)