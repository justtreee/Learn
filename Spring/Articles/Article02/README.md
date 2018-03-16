# SpringBoot使用JPA实现数据更新update

## 1. 在MySQL中创建表
假设我们有一个`Machine`类，代表用户拥有的设备，其中有编号`id`，设备名`machine_name`，设备状态`enable`，设备拥有者的编号`user_id`。记得初始化一条记录，id为1，enable为0。

## 2. 定义实体类

并通过注解设定为实体类`@Entity`，指定表名`@Table(name = "machine")`，并定义好sql语句`@NamedQuery(name = "updateEnableByUserid",
query = "update Machine m set m.enable=?1 where`。

```java
@Entity
@Table(name = "machine")
@NamedQuery(name = "updateEnableByUserid",
query = "update Machine m set m.enable=?1 where m.id = ?2")
public class Machine {
    @Id
    @GeneratedValue
    private int id;
    //字段名
    @Column(name = "machine_name")
    private String machine_name;
    @Column(name = "enable")
    private int enable;

    //一个用户有多个设备，多对一
    @ManyToOne(cascade = CascadeType.ALL)
    private SysUser user;
    
    //setter and getter
}
```

并为原本的SysUser类添加Machine的关系：
```java
@Entity
public class SysUser implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }
    @OneToMany
    private List<Machine> machines;
    
    //setter and getter
```

## 3. 定义数据访问接口

除了[之前](https://github.com/justtreee/Learn/tree/master/Spring/LearnSpringBoot02#%E7%AC%AC%E5%9B%9B%E6%AD%A5%E5%AE%9A%E4%B9%89%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE%E6%8E%A5%E5%8F%A3)的`@Query`之外，
我们还需要加上`@Modifying`才能实现更新操作，而`@Modifying`需要返回int类型或void类型。

同时，更新操作要使用事务`@Transactional`。
```java
public interface MachineRepository extends JpaRepository<Machine, Integer> {
    @Modifying
    @Transactional
    @Query("update Machine m set m.enable=:enable where m.id = :id")
    void updateEnableById(@Param("enable") int enable, @Param("id") int id);
}
```

## 定义Controller

上一篇博客我们以及实现了点击按钮调用类的方法，这时候只需要将更新操作写在其中，就可以通过网页点击按钮实现对数据库的修改：

```java
@Controller
public class Light {
    @Autowired
    private MachineRepository machineRepository;

    @PostMapping("/light1")
    @ResponseBody
    public String light1(){
        machineRepository.updateEnableById(1,1);
        //System.out.println("light 1");
        return "light1";
    }
```

项目启动之后，以管理员身份登录，点击页面上的按钮，如果成功弹出提示，此时再查看数据库，可以发现数据库已经成功更新。


## 参考链接
[Spring Boot 揭秘与实战（二） 数据存储篇 - JPA整合](https://blog.720ui.com/2017/springboot_02_data_jpa/)

[Spring Boot JPA - 基本使用](https://lufficc.com/blog/spring-boot-jpa-basic)