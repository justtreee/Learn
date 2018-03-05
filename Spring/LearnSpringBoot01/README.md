# 第一次创建SpringBoot项目-HelloWorld

请参阅：
[Intellij IDEA 搭建Spring Boot项目](http://blog.csdn.net/wuyinlei/article/details/79227962)
[详细介绍](http://blog.csdn.net/u012702547/article/details/53740047)

# Spring的常规属性配置


## 第一种方式：注入properties文件里的值

首先打开`application.properties`这个空的文件。

因为默认情况下这个文件不支持中文，所以我们先设置一下：
> 在IntelliJ IDEA中依次点击File -> Settings -> Editor -> File Encodings 
> 将Properties Files(.properties)下的Default encoding for properties files设置为UTF-8，将Transparent native-to-ascii conversion前的勾选上。


设置完之后还要添加如下代码：

```
server.tomcat.uri-encoding=UTF-8
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=true
spring.messages.encoding=UTF-8

myuser.name=张三
myuser.pinyin=zhangsan
myuser.age=18
```

前半部分是编码设置，后半部分就是需要被注入的值。

而这些属性直接在`UserController`中定义。

```java
@RestController
@EnableAutoConfiguration
public class UserController {
    @Value(value = "${myuser.name}")
    private String userName;
    @Value(value = "${myuser.pinyin}")
    private String userPinYin;
    @Value(value = "${myuser.age}")
    private String userAge;

    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    private String index(){
        return "Hello Spring Boot! The UserName is "+userName+";and PinYin is "+userPinYin+";and Age is "+userAge;
    }
}
```


测试类`xxxApplication.java`：

```java
@SpringBootApplication
public class Learnspringboot01Application {

	public static void main(String[] args) {
		SpringApplication.run(Learnspringboot01Application.class, args);
	}
}
```

## 第二种方式：类型安全的配置

刚刚说的这种方式我们在实际项目中使用的时候工作量略大，因为每个项目要注入的变量的值太多了，这种时候我们可以使用基于类型安全的配置方式，就是将properties属性和一个Bean关联在一起，这样使用起来会更加方便。我么来看看这种方式怎么实现。

**1. 在src/main/resources文件夹的文件application.properties添加类的注入信息**
文件内容如下：
```
book.name=红楼梦
book.author=曹雪芹
book.price=28
```

**2. 创建Book Bean,并注入properties文件中的值**
```java
@Component
@ConfigurationProperties(prefix = "book")
public class BookBean {
    private String name;
    private String author;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
```
prefix是指前缀，location指定要注入文件的位置。但我指定一个新的properties文件时有错误，不指定location的参数了。

**3. 在Controller中添加如下代码注入Bean：**

并添加`/book`路径映射。

```java
@RestController
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BookController {
    @Autowired
    private BookBean bookBean;
    @RequestMapping("/book")
    public String book() {
        return "Hello Spring Boot! The BookName is "+bookBean.getName()+";and Book Author is "+bookBean.getAuthor()+";and Book price is "+bookBean.getPrice();
    }
}
```

## 参考链接

[初识Spring Boot框架](http://blog.csdn.net/u012702547/article/details/53740047)