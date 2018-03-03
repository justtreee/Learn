# jdbcTemplate 实现 crud 操作

Spring 对不同的持久化技术都进行了封装：包括`JDBC`、`Hibemate`、`MyBatis`和`JPA`。

用jdbcTemplate对jdbc进行封装。

> 以下代码不需要依赖xml配置

## 增加


**第一步：设置数据库信息**

通过模板设置好数据库驱动、地址、账号密码等信息。
```java
//设置数据库信息
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///jdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("");
```

**第二步：创建模板对象，设置数据源**
```java
JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
```

**第三步：创建sql语句**
```java
String sql = "insert into tempspring values (?,?)";
        int rows = jdbcTemplate.update(sql, "1","amy");
        //rows表示影响的行数
        System.out.println(rows);
```

全部代码：
```java
public class JdbcTemplateDemo1 {

    //1. 添加操作
    @Test
    public void add(){
        //设置数据库信息
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///jdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        //创建模板对象，设置数据源
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //调用模板对象里面的方法实现操作
        //下面这行sql是创建一个表
        //jdbcTemplate.execute("create table tempSpring(id int primary key,name varchar(32))");
//        创建sql语句
        String sql = "insert into tempspring values (?,?)";
        int rows = jdbcTemplate.update(sql, "1","amy");
        //rows表示影响的行数
        System.out.println(rows);
        //输出 1
    }
}

```
数据库中成功更新；
显然，修改、删除的操作就可以通过修改代码中的sql即可实现。

## 查询
而查询是需要返回结果的，需要不同的写法。

按照之前的设置数据库信息与创建模板对象之后：
**1. 查询返回某一个值**

查询表中有多少记录：

```java
//sql
        String sql = "SELECT COUNT(*) FROM tempspring";

        //调用方法
        int cnt = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(cnt);
```
**2. 查询返回对象**
假设我们有一个User类，其中有id和name，以及相应的getter、setter和toString方法。

查询的时候代码还是差不多，但是我们需要返回一个对象，所以我们需要将查询结果封装成一个对象返回。

顶层的查询代码如下：
```java
//sql,根据name查询
        String sql = "SELECT * FROM tempspring WHERE name=?";

        //调用方法,
        // 第二个参数是接口，需要自己写类实现接口，自己做数据封装
        User user = jdbcTemplate.queryForObject(sql, new MyRowMapper(),"amy");
        System.out.println(user);
```

而`queryForObject`的第二个参数需要我们自己实现接口，也就是对象的封装。

在`public class JdbcTemplateDemo2`下方再声明一个类：

```java
class MyRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        //1. 从结果集里面吧数据得到
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        //2. 把数据封装到对象里面
        User user = new User();
        user.setId(id);
        user.setName(name);

        return user;
    }
}
```

运行之后得到信息：
```
User{id=1, name='amy'}
```

**3. 查询返回list集合**

list其实就是遍历数据库中的所有User对象，用相同的rowMapper封装，依次存入list中。

