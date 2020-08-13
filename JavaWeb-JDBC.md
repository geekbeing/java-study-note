# JDBC

## 基本概念

**JDBC**：Java Database Connectivity, Java语言操作数据库

**本质**：定义了一套操作所有关系型数据库的规则（接口）

## 快速入门

**步骤**：

> 导入驱动jar包
>
> * 复制jar到项目libs目录下
> * 右键 --> ADD as library
>
> 注册驱动
>
> 获取数据库链接对象 Connection
>
> 定义sql
>
> 获取执行sql语句的对象 Statement
>
> 执行sql,接受返回结果
>
> 处理结果
>
> 释放资源

**代码实现:**

```java
//1.导入jar包
//1.注册驱动
Class.forName("com.mysql.jdbc.Driver");
//2. 获取数据库链接对象 Connection
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1", "root", "root");
//3. 定义sql
String sql = "update account set balance = 500 where id = 1";
//4. 获取执行sql语句的对象 Statement
Statement stmt = conn.createStatement();
//5. 执行sql,接受返回结果
int count = stmt.executeUpdate(sql);
//6. 处理结果
System.out.println(count);
//7. 释放资源
stmt.close();
conn.close();
```



## JDBC 各个类详解

1. **DriverManager**：驱动管理对象

   功能:

   * 注册驱动:告诉程序该使用哪个数据库驱动jar包

     ```java
     Class.forName("com.mysql.jdbc.Driver");
     ```

   * 获取数据库连接

     ```java
     static Connection getConnection(String url, String user, String password); 
     ```

     

2. **Connection**：数据库连接对象

   功能:

   * 获取sql的执行方法

     ```java
     Statement createStatement();
     PreparedStatement  PreparedStatement(String sql);
     ```

   * 管理事务

     * 开启事务: `set AutoCommit(boolean autoConmmit)` 调用该方法设置参数为 `false`, 即开启事务
     * 提交事务: `commit()`
     * 回滚事务: `rollback()`

3. **Statement**：执行sql的对象

   执行sql :

   * `boolean execute(String sql);`  : 可以执行任意sql  **了解**

   * `int executeUpdate(String sql); ` :  执行DML(insert, update, delete)语句,DDL(create, alter, drop)语句

     返回值: 影响的行数.   DML执行结果的判断,  > 0 , DML执行成功

   * `ResultSet  executeQuery(String sql); ` : 执行DQL(select)语句

4. **ResultSet**：结果集对象,封装程序结果

   * `boolean next()` :  游标向下移动一行,判断当前行是否是最后一行末尾(是否有数据), **如果是,在返回 false.**  **如果否,在返回 true.** 

   * `getXxx(参数)` : 获取数据

     * Xxx: 代表数据类型,  `int getInt()`
     * 参数:  
       * int : 代表**列的编号**, **从1 开始** . 如 : `getString(1);`
       * String :  代表**列的名称**.  如 `getDouble("name");`

   * 注意:  **使用步骤**

     > 1. 游标向下移动一行
     >
     > 2. 判断是否有数据
     >
     > 3. 获取数据

   * **练习**:   定义一个方法,查询 emp 表的数据将其封装为对象,然后装载集合, 返回

     > 1. 定义Emp类
     >
     > 2. 定义方法 `public List<Emp> findAll(){} `
     >
     > 3. 实现方法  `select * from emp;`

5. **PreparedStatement**：执行sql的对象

   * **SQL注入问题**: 

     * 输入用户 **随便**, 输入密码:  **'a' or 'a' = a'**
     * sql 语句 : `select * form user where username = 'lhkaho' and password = 'a' or 'a' = 'a';`  **此时where 条件永远成立**

   * **解决SQL注入问题**: 使用**PreparedStatement**对象来解决

   * 预编译的SQL:  参数使用 **?** 作为占位符

   * **步骤:**

     > 1. 导入驱动jar包
     >
     > 2. 注册驱动
     >
     > 3. 获取数据库连接对象 Connection
     >
     > 4. 定义sql  **注意**: sql的参数使用 **?** 作为占位符. 
     >
     >    如:  `select select * form user where username = ? and password = ?;`
     >
     > 5. 获取执行sql语句的对象 : ` PreparedStatement Connection.PreparedStatement(String sql);` 
     >
     > 6. 给 **?** 赋值 :
     >
     >    方法: setXxx(参数1, 参数2)
     >
     >    参数1 : **? 的位置编号,从1开始** , 参数2 : **?** 的值
     >
     > 7. 执行sql,接受返回结果,不需要传递sql语句
     >
     > 8. 处理结果
     >
     > 9. 释放资源

   * **注意**: **后期都会使用PreparedStatement来完成增删改查的所有操作**

     1. 可以防止注入
     2. 效率更高

   

## 抽取 JDBC 工具类 : JDBCUtils

**目的**: 简化书写

分析:

1.  注册驱动也抽取

2. 抽取一个方法获取连接对象

   需求: 不想传递参数,还得保证工具类的通用性

   解决: 配置文件  jdbc.properties

   ```
   url=
   user=
   password=
   ```

3. 练习:

   * 通过键盘录入用户名和密码
   * 判断用户是否登录成功

   步骤:

   * 创建



## JDBC控制事务

操作:

1. 开启事务
2. 提交事务
3. 回滚事务

使用Connection对象来管理事务

* **开启事务**: setAutoCommit(boolean autoCommit) , 调用该方法设置参数为false, 即开启事务
  * 在执行sql之前开启事务
* **提交事务**: commit()
  * 在执行完sql之后提交事务
* **回滚事务** rollback()
  * 在catch中回滚事务



## 数据库连接池

1. ** 概念**: 就是一个容器(集合),存放数据库连接电容器

​	当系统初始化好后,容器被创建,容器中会申请一些连接对象,当用户来访问数据库时,从容器中获取连接对象,用完之后再归还给容器.

2. **好处**:

* 节约资源
* 用户访问高效

3. **实现方法**

* 标准接口:  DataSource   java.sql包下

  方法:

  * 获取连接:  getConnection()
  * 归还连接: Connection.close(). 如果对象Connection时从连接池中获取的,那么调用Connection.close()方法, 在不会关闭连接,而是归还连接

* 一般由数据库厂商实现
  * C3P0: 数据库连接池技术
  * Druid: 数据库连接池实现技术, 阿里提供

4. **C3P0: 数据库连接池技术**

* 步骤:

  * 导入jar包 (两个)   c3p0-0.9.5.2.jar \ mchange-commons-java-0.2.12.jar

    不要网络数据库驱动jar包

  * 定义配置文件

    * 名称(一定): c3p0.properties  或者 c3p0-config.xml
    * 路径: 直接放在src目录下

  * 创建核心对象: 数据库连接池对象 ComboPooledDataSource

  * 获取连接: getConnection

5. **Druid: 数据库连接池实现技术, 阿里提供**

* 步骤: 
  * 导入jar包   druid-1.0.9.jar
  * 定义配置文件:
    * propertise形式
    * 名称任意,目录任意
  * 加载配置文件, properties
  * 获取数据库连接池对象: 通过工厂类来获取  DruidDataSourceFactory
  * 获取连接: getConnection

* 定义工具类
  * 定义一个类 JDBCUtils
  * 提供静态代码块加载配置文件,初始化连接池对象
  * 提供的方法
    * 获取连接的方法:  通过数据库连接池获取对象
    * 释放资源
    * 获取连接池的方法



## Spring JDBC

Spring 框架对JDBC的见到那封装．提供了一个JDBCTemplate对象,简化JDBC的开发

1. **步骤:**

   * **导入jar包**

   * **创建jdbcTemplate对象**. 依赖于数据源DataSource

     ` jdbcTemplate template = new jdbcTemplate(ds); `

   * **调用方法来完成CRUD操作**

     * update: 执行DML语句, 增删改

     * queryForMap : 查询结果, 将结果集封装为map集合

       注意: **结果集长度只能是1**

     * queryForList : 查询结果, 将结果集封装为list集合

     * query : 查询结果, 将结果集封装为JavaBean对象

       * 参数:  RowMapper 

         * 一般使用 BeanPropertyRowMapper 实现类,可以完成数据到JavaBean的自动封装

         * ```java
           new BeanPropertyRowMapper<类型>(类型.class)
           ```

     * queryForObject : 查询结果,将结果集封装为对象

       * 一般用于聚合函数的查询

2. 练习

   需求:

   > 1. 修改1号数据的salary 为10000
   >
   > 2. 添加一条记录
   >
   > 3. 删除刚才添加的记录
   >
   > 4. 查询 id=1的记录,将其封装为map集合
   >
   >    注意: **结果集长度只能是1**
   >
   > 5. 查询所有记录,将其封装为list集合
   >
   > 6. 查询所有记录,将其封装为Emp对象的list集合
   >
   > 7. 查询记录总数