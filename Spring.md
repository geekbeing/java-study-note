

## 一、spring

#### 1.简介

**Spring**是一个开发应用框架，有这么几个标签：`轻量级`、`非侵入式`、`一站式`、`模块化`，其目的是用于简化企业级应用程序开发。



**Spring理念** : 使现有技术更加实用 . 本身就是一个大杂烩 , 整合现有的框架技术

官网 : http://spring.io/

官方下载地址 : https://repo.spring.io/libs-release-local/org/springframework/spring/

GitHub : https://github.com/spring-projects



```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.8.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.8.RELEASE</version>
</dependency>

```





#### **2.优点**

1、Spring是一个开源免费的框架 , 容器  .

2、Spring是一个轻量级的框架 , 非侵入式的 .

**3、控制反转(IoC )  , 面向切面(Aop) **

4、对事物的支持 , 对框架的支持

一句话概括：

==**Spring是一个轻量级的控制反转(IoC)和面向切面(AOP)的容器（框架）。**==



#### 3.组成

七大功能模块

![Spring 框架图示](https://developer.ibm.com/developer/articles/wa-spring1/nl/zh/images/spring_framework.gif)



#### 4.扩展

现代化的Java开发

![image-20200808170024327](C:\Users\chiwai lee\AppData\Roaming\Typora\typora-user-images\image-20200808170024327.png)

- Spring Boot 
  - 一套快速配置脚手架，
  - 基于Spring Boot 可以快速开发单个微服务;
  - 约定大于配置
- Spring Cloud
  - Spring Cloud是基于Spring Boot实现的；
  - 关注全局的服务治理框架；

因为现在大多数公司都在使用SpringBoot进行快手开发，学习SpringBoot的前提，需要完全掌握Spring 及SpringMVC，七承上启下的作用

弊端：发展了太久之后，违背了原来的理念，配置十分繁琐。



## 二、IOC

#### 1.IOC简介

原来开发流程：

1. UserDao 接口
2. UserDaoImpl 实现类
3. UserService 业务接口
4. UserServiceImpl  业务实现类



在之前的业务中，用户的需求可能会影响到原来的代码，需要根据用户的需求去修改源代码。

如果程序代码量十分大，修改一次成本代价十分昂贵。

```java
//原来的程序设计
private UserDao userDao = new UserDaoMysqlImpl();

```

我们使用一个Set接口实现，已经发生了革命性变化！

```java
private UserDao userDao;

//利用set进行动态实现值的注入
public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
}
```

- 之前，程序是主动创建对象，控制权在程序员手上
- 使用了set注入之后，程序不再具有主动性，而是变成了被动的接受对象   

这种思想，本质上解决了问题，程序猿不用再去管理对象的创建了。系统的耦合性大大降低，可以更加专注的在业务上的实现。这是IOC的原型



#### 2.IOC本质

**控制反转IoC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法**

没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，所谓控制反转就是：**获得依赖对象的方式反转了**。

![img](https://img-blog.csdnimg.cn/20200702151016907.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjM2NTUzMA==,size_16,color_FFFFFF,t_70)



**IoC是Spring框架的核心内容**，使用多种方式完美的实现了IoC，可以使用XML配置，也可以使用注解，新版本的Spring也可以零配置实现IoC。

Spring容器在初始化时先读取配置文件，根据配置文件或元数据创建与组织对象存入容器中，程序使用时再从Ioc容器中取出需要的对象。

**控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection,DI）**



## 三、 Hello spring

1. 实体类或实现类

2. beans配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <bean id="mysqlImpl" class="com.lee.dao.UserDaoMysqlImpl"/>
       <bean id="oracleImpl" class="com.lee.dao.UserDaoOracleImpl"/>
       <!--
       ref: 引用Spring容器中创建好的对象
       value：具体的值，如基本数据类型
       -->
       <bean id="serviceImpl" class="com.lee.service.UserServiceImpl">
           <property name="userDao" ref="oracleImpl"/>
       </bean>
   
   </beans>
   ```

   

3. 测试

   ```java
    public static void main(String[] args){
   
        //获取ApplicationContext；拿到Spring的容器
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //容器在手，天下我有，需要什么，及直接get什么
        UserServiceImpl serviceImpl = (UserServiceImpl) context.getBean("serviceImpl");
   
        serviceImpl.getUser();
    }
   ```

   **思考**：

   - Hello 对象是谁创建的 ?  【hello 对象是由Spring创建的】
   - Hello 对象的属性是怎么设置的 ?  【hello 对象的属性是由Spring容器设置的】

   这个过程就叫控制反转 :

   - 控制 : 谁来控制对象的创建 , 传统应用程序的对象是由程序本身控制创建的 , 使用Spring后 , 对象是由Spring来创建的
   - 反转 : 程序本身不创建对象 , 而变成被动的接收对象 .

   依赖注入 : 就是利用set方法来进行注入的.

   **IOC是一种编程思想，由主动的编程变成被动的接收**

   

   到现在，彻底不用在程序中去改动了，要实现不同操作，值需要在xml配置文件中进行修改。**所谓的IoC，就是：对象有Spring来创建，管理，装配**

## 四、 IOC创建对象的方式

1. 默认使用无参构造创建对象

2. 假设要使用有参构造创建对象，

   1. 通过下标赋值

      ```xml
      <!--直接通过下标赋值-->
      <bean id="user" class="com.lee.pojo.User">
          <constructor-arg index="0" value="java 说"/>
      </bean>
      ```

   2. 通过类型赋值【不建议使用】

      ```xml
       <!--不建议使用-->
      <bean id="user" class="com.lee.pojo.User">
          <constructor-arg type="java.lang.String" value="ni hao"/>
      </bean>
      ```

   3. 通过参数名赋值【推荐】

      ```xml
      <!--直接通过参数名赋值-->
      <bean id="user" class="com.lee.pojo.User">
          <constructor-arg name="name" value="泥嚎"/>
      </bean>
      ```

      

总结：在配置文件加载时，容器中管理的对象就已经初始化了

## 五、 Spring配置

#### 1.别名

```xml
<!--别名-->
<alias name="user" alias="userTest"/>
```

#### 2.Bean的配置

```xml
 <!--
    id:bean 的唯一标识符，也就是相当于对象名
    class：bean对象锁对应的全限名： 包名 + 类名
   name：也是别名，可以取多个别名，多种分隔符
    -->
<bean id="user" class="com.lee.pojo.User" name="user2 u2,u3;u4">
    <constructor-arg name="name" value="哈喽"/>
</bean>
```

#### 3.import

一般用于团队开发，可以将多个配置文件，导入合并为一个

applicationContext.xml

```xml
<import resource="beans.xml"/>
<import resource="beans2.xml"/>
<import resource="beans3.xml"/>
```

使用时直接使用总的配置就可以了



## 六、依赖注入

#### 1.构造器注入

前面说过了

#### 2.Set方式注入【重点】

依赖注入（Dependency Injection,DI）：Set注入。

- 依赖 : 指Bean对象的创建依赖于容器 . Bean对象的依赖资源 .
- 注入 : 指Bean对象所依赖的资源 , 由容器来设置和装配 .

#### 3.**环境搭建**

1. 赋值类型

   ```java
   public class Address {
       private String address;
   
       public String getAddress() {
           return address;
       }
   
       public void setAddress(String address) {
           this.address = address;
       }
   }
   ```

   

2. 真实参数对象

   ```java
   public class Student {
       private String name;
       private Address address;
       private String[] books;
       private List<String> hobbys;
       private Map<String,String> card;
       private Set<String> games;
       private String wife;
       private Properties info;
   }
   ```

3. beans.xml

   ```xml
   <bean id="student" class="com.lee.pojo.Student">
       <!--第一种，普通注入，value-->
       <property name="name" value="张三"/>
   </bean>
   ```

4. 测试类

   ```java
   public static void main(String[] args) {
       ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
       Student student = (Student) context.getBean("student");
   
       System.out.println(student.toString());
   }
   ```

#### 4.完善注入信息：

1. **Bean注入**

   ```xml
   <bean id="addr" class="com.kuang.pojo.Address">
        <property name="address" value="重庆"/>
    </bean>
    
    <bean id="student" class="com.kuang.pojo.Student">
        <!--第一种，普通注入，value-->
        <property name="name" value="小明"/>
        <!--第二种，bean注入, ref-->
        <property name="address" ref="address"/>
    </bean>
   ```

2. **数组注入**

   ```xml
   <!--数组注入-->
   <bean id="student" class="com.kuang.pojo.Student">
        <property name="name" value="小明"/>
        <property name="address" ref="addr"/>
       <!--数组注入-->
        <property name="books">
            <array>
                <value>西游记</value>
                <value>红楼梦</value>
                <value>水浒传</value>
            </array>
        </property>
    </bean>
   ```

3. **List注入**

   ```xml
   <!--List注入-->
   <property name="hobbys">
       <list>
           <value>听歌</value>
           <value>看电影</value>
           <value>爬山</value>
       </list>
   </property>
   ```

4. **Map注入**

   ```xml
   <!--Map注入-->
   <property name="card">
        <map>
            <entry key="中国邮政" value="456456456465456"/>
            <entry key="建设" value="1456682255511"/>
        </map>
    </property>
   ```

5. **set注入**

   ```xml
   <!--set注入-->
   <property name="games">
        <set>
            <value>LOL</value>
            <value>BOB</value>
            <value>COC</value>
        </set>
    </property>
   ```

6. **Null注入**

   ```xml
   <!--Null注入-->
   <property name="wife"><null/></property>
   ```

   

7. **Properties注入**

   ```xml
   <!--Properties注入-->
   <property name="info">
        <props>
            <prop key="学号">20190604</prop>
            <prop key="性别">男</prop>
            <prop key="姓名">小明</prop>
        </props>
    </property>
   ```

   

   

#### 5.扩展方式注入

可以使用p命名空间注入和c命名空间注入

官方解释： https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-p-namespace

1、P命名空间注入 : 需要在头文件中加入约束文件

```xml
导入约束 : xmlns:p="http://www.springframework.org/schema/p"
 
 <!--P(属性: properties)命名空间 , 属性依然要设置set方法-->
 <bean id="user" class="com.kuang.pojo.User" p:name="张三" p:age="18"/>
```



2、c 命名空间注入 : 需要在头文件中加入约束文件

```xml
导入约束 : xmlns:c="http://www.springframework.org/schema/c"

 <!--C(构造: Constructor)命名空间 , 属性依然要设置set方法-->
 <bean id="user" class="com.kuang.pojo.User" c:name="李四" c:age="18"/>
```



**注意：**不能直接使用，需要先导入约束

#### 6 .Bean的作用域

![image-20200809100307690](C:\Users\chiwai lee\AppData\Roaming\Typora\typora-user-images\image-20200809100307690.png)

1. 单例模式（Spring默认机制）：只会产生一个对象

   ![image-20200809100558128](C:\Users\chiwai lee\AppData\Roaming\Typora\typora-user-images\image-20200809100558128.png)

   ```xml
   <!-- the following is equivalent, though redundant (singleton scope is the default) -->
   <bean id="accountService" class="com.something.DefaultAccountService" scope="singleton"/>
   ```

   

2. 原型模式

   每次从容器中get的时候，都会产生一个新对象

   ```xml
   <bean id="accountService" class="com.something.DefaultAccountService" scope="prototype"/>
   ```

3. 其余的request、session、application、这些个只能在web开发中使用到

## 七、==Bean的自动装配==

- 自动装配是使用spring满足bean依赖的一种方法
- spring会在应用上下文中为某个bean寻找其依赖的bean。

Spring中bean有三种装配机制，分别是：

1. 在xml中显式配置；
2. 在java中显式配置；
3. 隐式的bean发现机制和自动装配。【重要】

#### 1.测试

环境搭建

#### 2.ByName自动装配

```xml
<!--
    byName: 会自动在容器上下文查找，和自己对象set方法后面对应的bean id-->
<bean id="people" class="com.lee.pojo.People" autowire="byName">
    <property name="name" value="张三"/>
    <!--<property name="cat" ref="cat"/>-->
    <!--<property name="dog" ref="dog"/>-->
</bean>
```

#### 3.ByType自动装配

```xml
<bean class="com.lee.pojo.Dog"/>
<bean class="com.lee.pojo.Cat"/>

<!--
    byName: 会自动在容器上下文查找，和自己对象set方法后面对应的bean id
    byType: 会自动在容器上下文查找，和自己对象类型相同的bean-->
<bean id="people" class="com.lee.pojo.People" autowire="byType">
    <property name="name" value="张三"/>
    <!--<property name="cat" ref="cat"/>-->
    <!--<property name="dog" ref="dog"/>-->
</bean>
```

总结：

- byName的时候，需要保证所有的bean的id唯一，并且这个bean需要和自动注入的属性的set方法的值一致
- byType的时候，需要保证所有的bean的class唯一，并且这个bean需要和自动注入的属性的类型一致

#### 4. 使用注解实现自动装配

准备工作：利用注解的方式注入属性。

1. 导入约束. context约束

2. ==配置注解的支持==，<context:annotation-config/>

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           https://www.springframework.org/schema/context/spring-context.xsd">
   
       
       <context:annotation-config/>
   
   </beans>
   ```

   

**@Autowired**

- 直接在属性上使用，可以在set方法上使用
- 使用Autowired可以不用编写set方法，前提是这个走到装配的属性在IoC（Spring）容器中存在，且符合命名byName

科普：

```
@Nullable  表明这个字段可以为null
```

```java
//如果显式地定义了Autowired的required属性为false，说明这个对象可以为null，否则不允许为空
@Autowired(required = false)
private Cat cat;
@Autowired
private Dog dog;
```



**@Qualifier**

如果@Autowired字段装配的环境比较复杂，走到装配无法通过一个注解【@Autowired】完成时，可以使用@Qualifier(value = “xx”)去配置@Autowired的使用，指定唯一的bean对象注入

```java
@Autowired
@Qualifier(value = "dog233")
private Dog dog;
```



**@Resource**

```java
@Resource(name = "cat3")
private Cat cat;
```

小结：

@Autowired和@Resource的区别

- 都是用来自动装配bean。都可以写在属性字段上，或写在setter方法
- @Autowired通过byType方式实现，
- @Resource默认通过byName方式实现，如果没有指定name属性，则通过byType实现。如果两个都找不到则报错！



## 八、==使用注解开发==

spring4之后，要使用注解开发，必须要导入aop的包

#### 1.bean

```java
//@Component组件
//等价于<bean id="user" class="com.lee.pojo.User"/>
@Component
public class User {
    public String name="张三";
}
```

#### 2.属性如何注入

```java
//@Component组件
//等价于<bean id="user" class="com.lee.pojo.User"/>
@Component
public class User {
    
    public String name;
    
    //等价于 <property name="name" value="张三"/>
    @Value("张三")
    public void setName(String name) {
        this.name = name;
    }
}
```



#### 3.衍生的注解

@Component有几个衍生注解，在web开发中，会按照MVC三层架构分层

- dao层：@Repository

- service层：@Service

- web层：@Controller

这四个注解功能都是一样的，都是代表将某个类注册到Spring容器中，装配bean

#### 4.自动装配配置

```
@Autowired：自动装配通过类型、名字
	如果Autowired不能唯一自动装配上属性，则需要通过@Qulifier(value="xx")
@Nullable：标记字段，说明字段可以为null
@resource：自动装配通过名字、类型
@scope：默认的singleton，单例模式； 可选prototype多例模式
```



#### 5.作用域

@scope：默认的singleton，单例模式； 可选prototype多例模式

#### 6.小结

xml与注解

- xml更万能，适合任何场合，维护方便
- 注解不是自己提供的类使用不了，维护复杂，开发简单方便

xml与注解最佳实践 ：

- xml管理Bean

- 注解完成属性注入

- 使用过程中， 只要注意，**必须让注解生效，就需要开启注解的支持**

  ```xml
  <!--指定要扫描的包，这个包下的著环节就会生效-->
  <context:component-scan base-package="com.lee"/>
  <!--开启注解-->
  <context:annotation-config/>
  ```

  

## 九、==使用Java的方式配置Spring==

JavaConfig 原来是 Spring 的一个子项目，它通过 Java 类的方式提供 Bean 的定义信息，在 Spring4 的版本， JavaConfig 已正式成为 Spring4 的核心功能 。

1. 编写实体类

   ```java
   @Component
   public class User {
       private String name;
   
       public String getName() {
           return name;
       }
   
       @Value("张三")
       public void setName(String name) {
           this.name = name;
       }
   }
   ```

2. 新建config配置包，编写配置类

   ```java
   //这个也会在Spring容器中托管，注册到容器中，因为他本来就是一个@Component
   //@Configuration代表这是一个配置类，九和beans.xml一样
   @Configuration
   public class LeeConfig {
   
       //注册一个bean，九相当于一个bean标签
       //这个方法的名字，相当于bean标签中的id属性
       //这个方法的返回值，相当于bean标签中的class属性
       @Bean
       public User getUser(){
           return new User(); //返回要注入到bean的对象
       }
   }
   ```

3. 测试

   ```java
   public static void main(String[] args) {
       //如果其安全使用配置类去做，愧疚只能通过AnnotationConfig 的上下文来获取容器，通过配置类的class对象来 加载bean
       ApplicationContext context = new AnnotationConfigApplicationContext(LeeConfig.class);
       User user = context.getBean("getUser", User.class);
   
       System.out.println(user.getName());
   
   }
   ```



这种纯Java的配置方式，在SpringBoot随处可见！



## 十、==代理模式==

SpringAOP的底层。  ==【SpringAOP和SpringMVC 面试必问】==



**代理模式**：

- 静态代理
- 动态代理

#### 1.静态代理

**静态代理角色分析**

- 抽象角色 : 一般使用接口或者抽象类来实现
- 真实角色 : 被代理的角色
- 代理角色 : 代理真实角色 ; 代理真实角色后 , 一般会做一些附属的操作 .
- 客户  :  使用代理角色来进行一些操作 .



**代码实现**

1. 接口：Rent.java 即抽象角色

   ```java
   public interface Rent {
       public void rent();
       
   }
   ```

2. Host.java 即真实角色

   ```java
   public class Host {
       public void rent(){
           System.out.println("租房");
       }
   }
   ```

3. Proxy.java 即代理角色

   ```java
   public class Proxy implements Rent{
       private Host host;
   
       public Proxy() {
       }
       public Proxy(Host host) {
           this.host = host;
       }
   
       public void  rent(){
           host.rent();
       }
   }
   ```

4. Client.java 即客户

   ```java
   public class Client {
       public static void main(String[] args) {
           //房东要租房
           Host host = new Host();
           //代理，中介帮房东租房，
           Proxy proxy = new Proxy(host);
   
           //不用面对房东，直接找中介租房
           proxy.rent();
       }
   }
   ```

   



**静态代理的好处:**

- 可以使得我们的真实角色更加纯粹 . 不再去关注一些公共的事情 .
- 公共的业务由代理来完成 . 实现了业务的分工 ,
- 公共业务发生扩展时变得更加集中和方便 .

缺点 :

- 每个类都会产生一个代理类 , 工作量变大了 . 开发效率降低 .

#### 2.加深理解

![image-20200809184607032](C:\Users\chiwai lee\AppData\Roaming\Typora\typora-user-images\image-20200809184607032.png)



#### 3.动态代理

- 动态代理的角色和静态代理的一样     .

- 动态代理的代理类是动态生成的     . 静态代理的代理类是我们提前写好的

- 动态代理分为两类     : 一类是基于接口动态代理 , 一类是基于类的动态代理

  - 基于接口的动态代理----JDK动态代理
  - 基于类的动态代理--cglib
  - java字节码实现：  javassist ，现在用的比较多的
  - 我们这里使用JDK的原生代码来实现，其余的道理都是一样的

  

需要了解两个类：**Proxy，InvocationHandler**

**InvocationHandler**：

是由代理实例的调用处理程序实现的接口。每个代理实例都有一个关联的调用处理程序

**Proxy：**提供了创建动态代理类和实例的静态方法，他也是由这些方法创建的所有动态代理类的超类



##### **代码实现：**

**ProxyInvocationHandler.java文件**

```java
package com.lee.demo04;

import com.lee.demo03.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    //被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    // proxy : 代理类 method : 代理类的调用处理程序的方法对象.
    //处理代理实例，并返回结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        Object result = method.invoke(target, args);

        return result;
    }

    //日志
    public void log(String msg){
        System.out.println("【Debug】执行了" + msg + "方法");
    }

}
```



**Client产生文件**

 ```java
public static void main(String[] args) {
        //真实角色
        UserServiceImpl userService = new UserServiceImpl();
        //代理角色，不存在
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        //设置要代理的对象
        pih.setTarget(userService);
        //动态生成代理对象
        UserService proxy = (UserService) pih.getProxy();

        proxy.add();
        proxy.delete();
    }
 ```



##### 动态代理的好处

静态代理有的它都有，静态代理没有的，它也有！

- 可以使得我们的真实角色更加纯粹 . 不再去关注一些公共的事情 .
- 公共的业务由代理来完成 . 实现了业务的分工 ,
- 公共业务发生扩展时变得更加集中和方便 .
- 一个动态代理 , 一般代理某一类业务
- 一个动态代理可以代理多个类，代理的是接口！



## 十一、AOP

#### 1.什么是AOP

AOP（Aspect Oriented Programming）意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

![img](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JAeTYOaaH6rZ6WmLLgwQLHf5pmH30gj6mZm81PC7iauicFu55sicJtspU7K3vTCVdZCDTSHq7D5XHlw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



#### 2.Aop在Spring中的作用

==提供声明式事务；允许用户自定义切面==

以下名词需要了解下：

- 横切关注点：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，就是横切关注点。如日志 , 安全 , 缓存 , 事务等等 ....
- 切面（ASPECT）：横切关注点 被模块化 的特殊对象。即，它是一个类。
- 通知（Advice）：切面必须要完成的工作。即，它是类中的一个方法。
- 目标（Target）：被通知对象。
- 代理（Proxy）：向目标对象应用通知之后创建的对象。
- 切入点（PointCut）：切面通知 执行的 “地点”的定义。
- 连接点（JointPoint）：与切入点匹配的执行点。

![img](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JAeTYOaaH6rZ6WmLLgwQLHVOZ1JpRb7ViaprZCRXsUbH0bZpibiaTjqib68LQHOWZicSvuU8Y1dquUVGw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**即 Aop 在 不改变原有代码的情况下 , 去增加新的功能 .**



#### 3.使用Spring实现Aop

【重点】使用AOP织入，需要导入一个依赖包！

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
   <groupId>org.aspectj</groupId>
   <artifactId>aspectjweaver</artifactId>
   <version>1.9.4</version>
</dependency>
```



**方法一：使用Spring的API接口【注意SpringAPI接口实现】**

- 业务接口

- 实现类

- 增强类 , 我们编写两个 , 一个前置增强 一个后置增强

  ```java
  public class Log implements MethodBeforeAdvice {
  
      //method：要执行的目标对象的方法
      //args：参数
      //target：目标对象
      @Override
      public void before(Method method, Object[] objects, Object target) throws Throwable {
          System.out.println(target.getClass().getName() + "的" + method.getName() + "被执行了");
      }
  }
  ```

  ```java
  public class LogAfter implements AfterReturningAdvice {
  
      @Override
      public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
          System.out.println("执行了" + method.getName() + " 返回结果为：" +  returnValue);
      }
  }
  ```

- 在spring的文件中注册 , 并实现aop切入实现 , 注意导入约束 .

  ```xml
     <!--注册bean-->
      <bean id="userService" class="com.lee.service.UserServiceImpl"/>
      <bean id="log" class="com.lee.log.Log"/>
      <bean id="after" class="com.lee.log.LogAfter"/>
  
      <!--方式一：原生的Spring API接口-->
      <!--配置aop：需要导入aop包-->
      <aop:config>
          <!--切入点，expression 表达式，execution（要执行的位置！ * * * * *）-->
          <aop:pointcut id="pointcut" expression="execution(* com.lee.service.UserServiceImpl.*(..))"/>
  
          <!--执行环绕增加！-->
          <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
          <aop:advisor advice-ref="after" pointcut-ref="pointcut"/>
      </aop:config>
  ```

- 测试

  ```java
   public static void main(String[] args) {
  
       ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  
       UserService userService = (UserService) context.getBean("userService");
  
       userService.add();
   }
  ```

  

Aop的重要性 : 很重要 . 一定要理解其中的思路 , 主要是思想的理解这一块 .

Spring的Aop就是将公共的业务 (日志 , 安全等) 和领域业务结合起来 , 当执行领域业务时 , 将会把公共业务加进来 . 实现公共业务的重复利用 . 领域业务更纯粹 , 程序猿专注领域业务 , 其本质还是动态代理 . 



**方法二：自定义类实现Aop 【主要是切面定义】**





**方法三：使用注解方式实现Aop**



```java
//方式三：使用注解实现AOP
@Aspect //标注这个类是一个前面
public class AnnotationPointCut {

    @Before("execution(* com.lee.service.UserServiceImpl.*(..))")
    public  void before(){
        System.out.println("========执行前=======");
    }

    @After("execution(* com.lee.service.UserServiceImpl.*(..))")
    public  void after(){
        System.out.println("========执行后=======");
    }

    @Around("execution(* com.lee.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("环绕前");

        Object proceed = jp.proceed();

        System.out.println("环绕前");
    }
}
```



spring配置

```xml
<!--方法三-->
<bean id="annotationPointCut" class="com.lee.diy.AnnotationPointCut"/>
<!--开启注解, JDK(默认)  cglib(proxy-target-class="true")-->
<aop:aspectj-autoproxy/>
```



测试结果：

```
环绕前
========执行前=========
新增了一个用户
========执行后=========
环绕前
```



#### 3.使用自定义类实现Aop



## 十二、整合Mybatis



#### 1.回忆mybatis

1. 编写实体类
2. 编写核心配置文件
3. 编写接口
4. 编写Mapper.xml
5. 测试



#### 2.Mybatis-Spring

步骤：

> 1. 配置数据源替换mybaits的数据源
> 2. 配置SqlSessionFactory，关联MyBatis
> 3. 注册sqlSessionTemplate，关联sqlSessionFactory；
> 4. 增加Dao接口的实现类；私有化sqlSessionTemplate
> 5. 注册bean实现
> 6. 测试



1. 配置数据源替换mybaits的数据源

   ```xml
   <!--配置数据源：数据源有非常多，可以使用第三方的，也可使使用Spring的-->
   <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
      <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
      <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
      <property name="username" value="root"/>
      <property name="password" value="123456"/>
   </bean>
   ```

2. 配置SqlSessionFactory，关联MyBatis

   ```xml
   <!--sqlSessionFactory-->
   <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
       <property name="dataSource" ref="dataSource"/>
       <!--绑定Mybatis配置文件-->
       <property name="configLocation" value="classpath:mybatis-config.xml"/>
       <property name="mapperLocations" value="classpath:com/lee/mapper/*.xml"/>
   </bean>
   ```

3. 注册sqlSessionTemplate，关联sqlSessionFactory；

   ```xml
    <!--SqlSessionTemplate：就是我们使用的SqlSession,关联sqlSessionFactory-->
   <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
       <!--只能通过构造方法注入sqlSessionFactory，因为它没有set方法-->
       <constructor-arg index="0" ref="sqlSessionFactory"/>
   </bean>
   ```

   

4. 增加Dao接口的实现类；私有化sqlSessionTemplate

   ```java
   public class UserMapperImpl implements UserMapper {
       private SqlSessionTemplate sqlSession;
   
       public void setSqlSession(SqlSessionTemplate sqlSession) {
           this.sqlSession = sqlSession;
       }
   
       @Override
       public List<User> selectUser() {
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           return mapper.selectUser();
       }
   }
   ```

5. 注册bean实现

   ```xml
   <bean id="userMapper" class="com.lee.mapper.UserMapperImpl">
       <property name="sqlSession" ref="sqlSession"/>
   </bean>
   ```

6. 测试

   ```java
   @Test
       public void test2(){
           ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
           UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
   
           for (User user : userMapper.selectUser()) {
               System.out.println(user);
           }
       }
   ```

   

## 十三、声明式事务

#### 1.事务回顾

- 把一组业务当成一个业务来做，要么都成功，要么都失败
- 事务在项目开发过程非常重要，涉及到数据的一致性的问题，不容马虎！
- 确保数据的完整性和一致性。



**事务四个属性ACID**

1. 原子性（atomicity）

2. - 事务是原子性操作，由一系列动作组成，事务的原子性确保动作要么全部完成，要么完全不起作用

3. 一致性（consistency）

4. - 一旦所有事务动作完成，事务就要被提交。数据和资源处于一种满足业务规则的一致性状态中

5. 隔离性（isolation）

6. - 可能多个事务会同时处理相同的数据，因此每个事务都应该与其他事务隔离开来，防止数据损坏

7. 持久性（durability）

8. - 事务一旦完成，无论系统发生什么错误，结果都不会受到影响。通常情况下，事务的结果被写到持久化存储器中

#### 2.spring中事务管理

**编程式事务管理**

- 将事务管理代码嵌到业务方法中来控制事务的提交和回滚
- 缺点：必须在每个事务操作业务逻辑中包含额外的事务管理代码

**声明式事务管理** 【AOP】

- 一般情况下比编程式事务好用。
- 将事务管理代码从业务方法中分离出来，以声明的方式来实现事务管理。
- 将事务管理作为横切关注点，通过aop方法模块化。Spring中通过Spring AOP框架支持声明式事务管理。



1. 开启 事务声明

   ```xml
   <!--配置声明式事务-->
   <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
       <property name="dataSource" ref="dataSource"/>
   </bean>
   ```

2. 结合aop实现事务的织入

   ```xml
   <!--结合aop实现植物的织入-->
   <!--配置事务通知-->
   <tx:advice id="txAdvice" transaction-manager="transactionManager">
       <!--给哪些方法配置事务-->
       <!--配置事务的传播特性：new propagation= -->
       <tx:attributes>
           <tx:method name="add" propagation="REQUIRED"/>
           <tx:method name="*" propagation="REQUIRED"/>
       </tx:attributes>
   </tx:advice>
   ```

3. 配置事务切入

   ```xml
   <!--配置事务切入-->
   <aop:config>
       <aop:pointcut id="txPointCut" expression="execution(* com.lee.mapper.*.*(..))"/>
       <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
   </aop:config>
   ```



为什么需要配置事务？

- 如果不配置，就需要我们手动提交控制事务，可能会存在数据提交不一致的问题；
- 如果不在Spring中去配置声明式事务，就需要在代码中手动配置事务
- 事务在项目开发过程非常重要，涉及到数据的一致性的问题，不容马虎！



