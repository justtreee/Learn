# 初识mybatis

## 一、基本使用
第一部分，我们先使用IDEA来简单使用一下mybatis。

### 1. 创建项目与搭建环境

- 这里我们已最普通的Java项目创建为例。创建之后直接导入mybatis的jar包，[最新下载链接](https://github.com/mybatis/mybatis-3/releases)，也可以在github中找。

- 因为涉及到数据库操作，我们创建一个数据库进行操作：这里假设有一个user表，其中有id,username,password,address四个字段。

### 2. 创建配置类并获取SqlSessionFactory
对数据库的操作总是绕不开数据库信息的配置。一般情况下一个数据库只需要有一个SqlSessionFactory实例，过多的SqlSessionFactory会导致数据库有过多的连接，从而消耗过多的数据库资源，因此SqlSessionFactory需要我们将之做成一个单例模式，如下：

```java

public class DBUtils {
    private static SqlSessionFactory sqlSessionFactory = null;
    private static final Class CLASS_LOCK = DBUtils.class;

    public static SqlSessionFactory initSqlSessionFactory(){
//        一般情况下一个数据库只需要有一个SqlSessionFactory实例，过多的SqlSessionFactory会导致数据库有过多的连接，从而消耗过多的数据库资源，因此SqlSessionFactory需要我们将之做成一个单例模式
        synchronized (CLASS_LOCK){
            if(sqlSessionFactory == null){
//                配置MySQL信息
                PooledDataSource dataSource = new PooledDataSource();
                dataSource.setDriver("com.mysql.jdbc.Driver");
                dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
                dataSource.setUsername("root");
                dataSource.setPassword("");
                TransactionFactory transactionFactory = new JdbcTransactionFactory();
                Environment environment = new Environment("development", transactionFactory, dataSource);
                Configuration configuration = new Configuration(environment);
                configuration.addMapper(UserMapper.class);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSession(){
        if(sqlSessionFactory == null)
        {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}
```
这里引用参考博客的一段，来解释一下各部分代码的作用：

> 1. 首先该类一共两个工具方法，一个用来获取SqlSessionFactory，另一个用来获取SqlSession 
> 2. 获取SqlSessionFactory的使用我们采用了单例模式，这是常见的单例写法，不用多说 
> 3. 在获取SqlSessionFactory的方法中，我们通过PooledDataSource对象的实例来设置数据库驱动、数据库连接地址、数据库用户名和密码等。 
> 4. 第15行的代码我们创建了数据库的运行环境，并将这个环境命名为development 
> 5. 第17行代码添加了一个映射器，这个映射器就是我们前文说的Mapper，用来执行一个SQL 语句。 
> 6. 有了SqlSessionFactory之后接下来在27行我们就可以通过SqlSessionFactory来构建一个SqlSession了。


### 3. 构建Mapper
Mapper以Java接口+注解的方式配置好所需要的sql语句。

```java
public interface UserMapper {
    @Select(value = "select * from user where id=#{id}")
    public User getUser(Long id);
}
```

### 4. 测试
```java
public class TestDemo {
    @Test
    public void test1(){
        SqlSession sqlSession = null;
        try {
            sqlSession = DBUtils.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUser((long) 1);
            System.out.println(user.toString());
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void test2(){
        SqlSession sqlSession = null;
        try{
            sqlSession = DBUtils.openSession();
            User user = (User) sqlSession.selectOne("db.UserMapper.getUser", 2L);
            System.out.println(user.toString());
            sqlSession.commit();
        } catch (Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
    }
```

## 参考链接

[初识mybatis](http://blog.csdn.net/u012702547/article/details/54408761)