# Spring的Bean管理（xml方式）

## Bean实例化的方式
在Spring里面通过配置文件创建对象。Bean实例化有三种方式实现：

1. 使用类的无参数构造创建（重点）

2. 使用静态工厂创建

3. 使用实例工厂创建


### 类的无参数构造创建

所谓无参数构造，就是与类的有参数构造函数相对。
```java
public class User {
    public void add(){
        System.out.println("add.....");
    }

    public static void main(String[] args) {
        //原始做法：
        //User user = new User();
        //user.add();
    }
}
```

在之前的这个User类中，构造函数是没有参数的。
这时候通过xml配置文件是可以正常运行的。
```xml
<bean id="user" class="com.treee.ioc.User"></bean>
```

但若是增加一段带参数的构造函数，也就是类里面没有无参数的构造，运行就会出现异常。
```
No default constructor found;
```
[参考博客链接](http://blog.csdn.net/u012702547/article/details/56021922)
[参考代码](https://github.com/lenve/JavaEETest/tree/master/Test28-Spring1)


## Bean标签常用属性
1.  id属性：通过id名得到配置的对象。名字可以自定义（不能包含特殊符号）

2. class属性：创建对象所在类的全路径。

3. name属性：现在不常用，与id功能相同。

4. scope属性：Bean的作用范围。
- singleton: 默认值，单例
- prototype：多例

**singleton**：
入门案例的xml设置：
```xml
<bean id="user" class="com.treee.ioc.User"></bean>
```
这样的设置，就是默认的单例（singleton）设置。这时候如果通过配置创建两个对象：
```java
User user1 = (User)context.getBean("user");
User user2 = (User)context.getBean("user");
System.out.println(user1); 
System.out.println(user2); 
```

这时候输出的两个地址是相同的。这就证明他们是一个对象，也就是单实例对象。

**prototype**：
那么多实例对象就很明显了。

```xml
<bean id="user" class="com.treee.ioc.User" scope="prototype"></bean>
```

打印的地址就是不同的。

# 属性注入
属性注入： 创建对象时，向类里面的属性设置值。

## 属性注入方式
对于java代码来说，有三种方式：

![属性注入](https://user-images.githubusercontent.com/15559340/36832799-f8a7f2ee-1d67-11e8-96a5-1a751cf2272f.PNG)

但对于Spring框架，只支持前两种。而其中set方法是重点。

### 1. 有参构造注入
构造方法注入和p名称空间注入这两种方式我们在开发中用的并不算多，但是我们还是有必要先来看看构造方法如何注入。 
假设我有一个User_property，如下：
```java
public class User_property {
    private String username;
    //有参构造使用的构造函数
    public User_property(String username) {
        this.username = username;
    }

    public void test1(){
        System.out.println("Username: "+ username);
    }
}
```

这时候就需要在xml中添加如下配置：
```xml
<!--使用有参数构造注入属性 -->
    <bean id="user2" class="com.treee.property.User_property">
        <!--使用有参数构造注入-->
        <constructor-arg name="username" value="张三"></constructor-arg>
    </bean>
```
**注意**：这里使用的是`constructor-arg`标签。

测试代码：
```java
public void testUser1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User_property user_property = (User_property) context.getBean("user2");

        user_property.test1();
    }
```
运行结果输出：`Username: 张三`

### 2. set方法注入

set方法注入是我们用的比较多的一种注入方式，这种注入方式也很简单。

```java
public class User_set {
    private String username;
    //set方法注入的setter构造方法
    public void setUsername(String username) {
        this.username = username;
    }
    public void test1(){
        System.out.println("Username: "+ username);
    }
}
```

这时候就需要在xml中添加如下配置：
```xml
<bean id="user3" class="com.treee.property.User_set">
        <!--使用set方法注入属性（重点）-->
        <property name="username" value="李四"/>
    </bean>
```
**注意**：这里使用的是`property`标签。

测试代码：
```java
public void testUser2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User_set user_set = (User_set) context.getBean("user3");

        user_set.test1();
    }
```
运行结果输出：`Username: 李四`

这是主要使用的方式。

## 复杂对象的注入

### 对象的注入
这算是开发中最最常用的注入了。举一个常见的使用场景，我们在DAO层进行数据库的操作，在Service层进行业务逻辑操作，那我在Service中需要有一个DAO实例，如下，我有一个UserDao：
```java
public class UserDao {
    public void getData(){
        System.out.println("get data");
    }
}
```

还有一个UserService拥有一个UserDao属性:

```java
public class UserService {
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserDao userDao;

    public void getDaoData(){
        System.out.println("Service.......");
        userDao.getData();
    }
}
```

xml如下所示，ref属性表示的是注入的关系：
![default](https://user-images.githubusercontent.com/15559340/36838185-73ff2f1e-1d79-11e8-94b1-7ac21ea7617a.PNG)


### 复杂属性的注入

以下是数组注入、List集合注入、Map注入、Properties注入。
```java 

public class User_c {
    public void setNames(String[] names) {
        this.names = names;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private String[] names;
    private List<String> list;
    private Map<String, String> map;
    private Properties properties;

    public void test(){
        StringBuffer sb1 = new StringBuffer();
        for(String name : names){
            sb1.append(name).append(",");
        }

        StringBuffer sb2 = new StringBuffer();
        for(String l : list){
            sb2.append(l).append(",");
        }

        StringBuffer sb3 = new StringBuffer();
        Set<String> keySet = map.keySet();
        for(String ks : keySet){
            sb3.append("key: "+ ks + "  value: "+map.get(ks)).append(",");
        }

        System.out.println(sb1.toString()+"\n"+sb2.toString()+"\n"+sb3.toString());
        System.out.println(properties.getProperty("username"));

    }
}

```

在xml配置文件中注入值：

```xml 
<bean id="userc" class="com.treee.property.User_c">
        <property name="names">
            <list>
                <value>张三</value>
                <value>李四</value>
                <value>王五</value>
            </list>
        </property>
        <property name="list">
            <list>
                <value>足球</value>
                <value>篮球</value>
                <value>乒乓球</value>
            </list>
        </property>

        <property name="map">
            <map>
                <entry key="username" value="张三"/>
                <entry key="password" value="123456"/>
            </map>
        </property>
        <property name="properties">
            <props>
                <prop key="username">赵六</prop>
            </props>
        </property>
    </bean>
```

最后在test.java调用中测试：
```java 
//复杂属性的注入
    @Test
    public void testUserc(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User_c user_c = (User_c) context.getBean("userc");

        user_c.test();
    }
//    张三,李四,王五,
//    足球,篮球,乒乓球,
//    key: username  value: 张三,key: password  value: 123456,
//    赵六
```


## 参考链接
[Spring中属性注入的几种方式以及复杂属性的注入](http://blog.csdn.net/u012702547/article/details/56307861)