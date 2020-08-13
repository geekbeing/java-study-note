# 数据库

## 数据库概念

参见（MySQL基础.pdf）

## MySQL软件

​	1.安装

​	**建议**：使用zip解压版安装，参照 [教程](https://www.cnblogs.com/swjian/p/11907600.html) ， 可以避免很多麻烦，utf-8编码等问题

​	**注意事项**

* 装完后先用 `mysqld  --initialize-insecure` （不设置root密码，建议使用）进行初始化，然后用`net start mysql` 启动服务 

	* `ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: YES)` 这是不需要输入密码，
	* 登录后使用命令 `SET PASSWORD = PASSWORD('new password');` 开启密码

2. 卸载

   * 去mysql安装目录下找到my.ini文件： 复制`datadir ="C:/ProgramData/MySQL/ ...'` 

   * 卸载

   * 删除data 文件夹 ,`datadir`下

   * 重装前，要彻底删干净MySQL相关的东西， [参考教程](https://blog.csdn.net/sxingming/article/details/52601250)

     

3. 配置

   * MySQL服务启动
     * 手动
     * cmd 管理员：  `net start mysql`  启动， `net stop mysql` 关闭 
   * 登录
     * `mysql -u用户名 -p` 
     * `mysql -hIP -u用户名 -p`
   * 退出
     * `exit` / `quit`
   * 数据目录

# SQL

​	1. 什么是SQL？

​		structured Query Language：结构化查询语言

​		定义了所有关系型数据库的规则

 2. SQL通用语法

    * SQL语句可以单行或多行书写，以**分号结尾**

    * 可以使用空格或缩进来增强可读性

    * MySQL数据库的语句不区分大小写，关键字建议大写

      `SELECT * FROM student;`

    * 3种注释： 

      * 单行注释： `-- 注释内容`  或  `# 注释内容` (mysql特有)
      * 多行注释：`/* 注释 */`

 3. SQL分类

    * DDL （操作数据库、表）

    * DQL（**查询**表中的数据）
    * DML（**增删改**表中的数据）
    * DCL（授权）

    ## DDL：操作数据库

1. 操作数据库： CRUD
   * C（Create）：创建
     * 创建数据库：`create database if not exists 数据库名;` 
     * 创建时指定字符集（如 gbk）：`create database  if not exists 数据库名 character set gbk;`
   * R（Retrieve）：查询
     * 查询所有数据库名称：`show databases；` 
     * 查询某个数据库的字符集：`show create database 数据库名;` 
   * U（Update）：修改
     * 修改数据库的字符集：`alter database 数据库名 character set 字符集名`
   * D（Delete）：删除
     * `drop database if exists 数据库名; `
   * 使用数据库
     * 查询当前使用的数据库：`select database();`
     * 使用数据库：`use 数据库名;`
   
5. 操作表

   - **C（Create）：创建**

     - **语法**

       ```sql
       create table 表名(
       	列名1 数据类型1，
       	列名2 数据类型2，
       	。。。
       );
       ```

     * 数据类型：

       ```sql
       int：整数类型
       double：小数类型，**要注明小数位数**
       date：日期 yyyy-MM-dd
       datetime：日期 yyyy-MM-dd HH:mm:ss
       timestamp：时间戳类型，不赋值时自动添加系统时间
       varchar：字符串类型
       ```

     - **创建表**

       ```sql
       create table student(
       	id int,
       	name varchar(32),
       	age int,
       	scorce double(4,1),
       	birthday date,
       	insert_time timestamp
       );
       ```

       

     * **复制表**  `create table 表名 like 表名; ` 

   - R（Retrieve）：查询

     - 查询所有表：`show tables;`
     - 查询表结构：`desc 表名;`

   - U（Update）：修改

     - 修改表名：`alter table 表名 rename to 新表名;`
     - 修改表字符集：`alter table 表名 character set 字符集名;`
     - 修改一列：`alter table 表名 add 列名 数据类型;`
     - 修改列名、类型：
       - 修改列名和类型：`alter table 表名 change 列名 新列名 新数据类型;`  
       - 仅修改类型：`alter table 表名 modify 列名 新数据类型;`
     - 删除列：`alter table 表名 drop 列名;`

   - D（Delete）：删除

     - 删除表`drop table if exists 表名;`



## DML：增删改表中数据

1. 添加数据：

   * 语法：

     `insert into 表名(列名1，列名2。。。) values(值1，值2。。。); `

   * 主要：除了数字，其他数据类型都要加 引号 `‘ ’`

2. 删除数据：

   * `delete from 表名 where 条件;`  删除符合条件的记录
   * `truncate table 表名;`  删除表，然后再创建一个一样的空表

3. 修改数据：

   * 语法：
     * `update 表名 set  列名1=值1,列名2=值2 。。。where 条件; `
     * **注意**：不加条件则将表中所有记录修改

## DQL:查询

 查询表的所有内容：`select * from 表名;`

1. 语法：

   ```sql
   select
   	字段列表
   from
   	表名列表
   where
   	条件列表
   group by 
   	分组字段
   having
   	分组后的条件
   order by
   	排序
   limit
   	分页限定
   ```

2. 基础查询
   * 多字段查询：`select  列名1,列名2 from 表名；`
   * 取除重复： distinct 
     *  `select distinct 列名1,列名2 from 表名；` 
     * **注意**：所有查询字段相同才能去重
   * 列值相加 `select  列名1,列名2,列名3,列名1+ifNULL(列名2,0) as 和的新列名 from 表名；`   
   * **注意**：ifNULL(列名，为NULL时的设定值)
   * 起别名： ` as 别名; `

3. 条件查询

   * where 子句后跟条件

   * 运算符：

     ```sql
     >, <, <=, >=, =, <>
     BETWEEN...AND
     IN(集合)
     LIKE  -- 模糊查询
     IS NULL
     and 或 &&
     or 或 ||
     not 或 !
     ```

   * 模糊查询：LIKE 

     * 占位符： 

       ```sql
       '_'：单个任意字符
       '%'：任意个任意字符
       ```

     * 例子

       ```sql
       LIKE '马%'：马开头
       LIKE '_化%'：第二个时化字
       LIKE '___'：三个字
       LIKE '%德%'：含有德字
       ```

4. 排序查询：oder by

   * 语法：`oder by 排序字段1 排序方式1, 排序字段2 排序方式2...；` 

   * 排序方式： 

     ```sql
     ASC：升序，默认
     DESC：降序
     ```

5. 聚合函数：将一列数据作为一个整体，做纵向计算

   ```sql
   count : 计算个数;
   max: 计算最大值
   min: 计算最小值
   sum: 计算总和
   avg: 计算平均值
   ```

   **注意**：聚合函数会排除NULL值，可以IFNULL()处理

6. 分组查询：group by

   ```sql
   -- 按性别分组，分别查询男、女同学的平均分、人数
   SELECT sex, AVG(math),COUNT(id) FROM stu GROUP BY sex;
   
   -- 按性别分组，分别查询男、女同学的平均分、人数 要求：分数低于70 的人不参与分组
   SELECT sex, AVG(math),COUNT(id) FROM stu WHERE math > 70 GROUP BY sex;
   ```

   * **where 与 having 的区别**：
     * where在分组之前进行限定，不满足不参与分组；having在分组后进行限定，不满足不会被查询出来
     * where后不可以跟聚合函数，having可以进行聚合函数的判断

   

7. 分页查询： limit

   * 语法：`limit 开始索引，每页查询条数;`     
   * 公式： 开始索引 = （当前页码 - 1）* 每页显示的条数

## 约束

概念：对表中的数据进行限定，保证数据的正确性、有效性和完整性

分类：

 1. 主键约束: primary key

 2. 非空约束: not null

 3. 唯一约束: unique

 4. 外键约束: foreign key 

    

#### 1. 主键约束: primary key

**说明**：主键非空且唯一，一张表只能有一个字段作为主键，即表中记录的唯一标识符

* 创建表时添加主键约束

  ```sql
  CREATE TABLE stu(
  	id INT PRIMARY KEY, -- 给id添加主键约束
      NAME VARCHAR(20) NOT NULL -- name 为非空
  );
  ```

* 创建表后，添加主键约束

  ```sql
  ALTER TABLE stu MODIFY id INT PRIMRY KEY;
  ```

  **注意**：必须先删除重复才能添加主键约束

* 删除name的主键约束

  ```sql
  ALTER TABLE stu DROP id PRIMRY KEY;
  ```

* 自动增长

  概念：某一列为数值类型，是使用 `auto_increment` 可以完成值的自动增长

  * 创建表时添加主键约束并且自动增长

    ```sql
    CREATE TABLE stu(
    	id INT PRIMARY KEY AUTO_INCREMENT, -- 给id添加主键约束,且自动增长
        NAME VARCHAR(20) NOT NULL -- name 为非空
    );
    ```

  * 删除自动增长

    ```sql
    ALTER TABLE stu DROP id INT;
    ```

  * 添加自动增长

    ```sql
    ALTER TABLE stu MODIFY id INT AUTO_INCREMENT;
    ```

    

#### 2. 非空约束: not null

* 创建表时添加非空约束

  ```sql
  CREATE TABLE stu(
  	id INT,
      NAME VARCHAR(20) NOT NULL -- name 为非空
  );
  ```

* 创建表后，添加非空约束

  ```sql
  ALTER TABLE stu MODIFY NAME VARCHAR(20) NOT NULL;
  ```

* 删除name的非空约束

  ```sql
  ALTER TABLE stu MODIFY NAME VARCHAR(20);
  
  ```

#### 3. 唯一约束: unique，值不能重复

* 创建表时添加唯一约束

  ```sql
  CREATE TABLE stu(
  	id INT,
      phone_num VARCHAR(20) UNIQUE -- phone_num 为唯一
  );
  ```

* 创建表后，添加唯一约束

  ```sql
  ALTER TABLE stu MODIFY phone_num VARCHAR(20) UNIQUE;
  ```

  **注意**：必须先删除重复才能添加唯一约束

* 删除name的唯一约束，

  ```sql
  ALTER TABLE stu DROP INDEX phone_num;
  ```

  

#### 4. 外键约束: foreign key

让表与表之间产生关系，从而保证数据的准确性

* 创建表时，添加外键约束

  ```sql
  CREATE TABLE 表名(
  	...
      外键列
      CONSTRAINT 外键名 FOREIGN KEY (外键列名) REFERENCES 主表名(主表列名)
  );
  ```

* 删除外键约束

  ```sql
  ALTER TABLE 表名 DROP FOREIGN KEY 外键列名;
  ```

* 创建表后，添加外键约束

  ```sql
  ALTER TABLE 表名 ADD  CONSTRAINT 外键名 FOREIGN KEY (外键列名) REFERENCES 主表名(主表列名);
  ```

* 级联操作

  * 添加外键，设置级联更新： `ON UPDATE CASCADE`

    ```sql
    ALTER TABLE 表名 ADD  CONSTRAINT 外键名 FOREIGN KEY (外键列名) REFERENCES 主表名(主表列名) ON UPDATE CASCADE;
    ```

  * 添加外键，设置级联更新，设置级联删除: `ON DELETE CASCADE`

    ```sql
    ALTER TABLE 表名 ADD  CONSTRAINT 外键名 FOREIGN KEY (外键列名) REFERENCES 主表名(主表列名) ON UPDATE CASCADE ON DELETE CASCADE;
    ```

    

## 数据库的设计

#### 1. 多表之间的关系

 *  关系： 
    * 一对一
    * 一对多
    * 多对多

* **一对多**

  实现方式：在多的一方建立外键，指向另一方的主键

* **多对多** 

  实现方式：需要借助第三张**中间表**。中间表至少包含两个字段，这两个字段作为中间表的外键，分别指向两张表的主键

* **一对一**

  

#### 2. 数据库设计的范式

概念：设计数据库时，需要遵循的一些规范。

目前关系型数据库有六种范式：**第一范式(1NF)、第二范式(2NF)、第三范式(3NF)**、巴斯-科德范式(BCNF)、第四范式(4NF)第五范式(5NF)

分类

* **第一范式(1NF)**：要求数据库表的每一列都是不可分割的**原子数据项**

  存在问题：

  * 存在非常严重的数据冗余
  * 数据添加存在问题
  * 数据删除存在问题

* **第二范式(2NF)**：在1NF的基础上，非码属性必须完全依赖于候选码（在1NF基础上**消除非主属性对主码的部分函数依赖**）

  相关概念：

  * 函数依赖 ：A --> B ，如果通过A属性（属性组）的值可以唯一确定B属性的值，则称B依赖于A

    ​	例如：学号 --> 姓名  ， （学号，课程名）--> 分数

  * 完全函数依赖：A --> B ， 如果A是一个属性组，则B属性值的确定需要依赖于A属性组中的所有的属性值

    ​	例如：（学号，课程名）--> 分数

  * 部分函数依赖：A --> B ， 如果A是一个属性组，则B属性值的确定只需要依赖于A属性组中的某一些属性值

    ​	例如：（学号，课程名）--> 姓名

  * 传递函数依赖：A --> B ，B --> C，如果通过A属性（属性组）的值可以唯一确定B属性的值，在通过B属性（属性组）的值可以唯一确定C属性的值，则称 C 传递函数依赖于 A

    ​	例如：学号 --> 系名， 系名 --> 系主任

  * 码：如果一张表中，一个属性或属性组，被其他所有属性所完全依赖，则称这个属性或属性组为该表的码

    ​	例如：该表中的码为，（学号，课程名）

* **第三范式(3NF)**：在2NF基础上，任何非主属性不依赖于其它非主属性（在2NF基础上**消除传递依赖**）

#### 3. 数据库的备份和还原

* 命令行：

  * 备份： `mysqldump -u用户名 -p密码 > 保持路径; `

  * 还原：

    > 1. 登录数据库
    > 2. 创建数据库
    > 3. 使用数据库
    > 4. 执行文件  `source 文件路径`

  

## 多表查询

* 查询语法：

  ```sql
  SELECT
  	列名列表
  FROM
  	表名列表
  WHERE 。。。
  ```

* 笛卡儿积：

  > 有两个集合A，B，取这两个集合的所有组成情况
  >
  > 要完成多表查询，需要消除无用的数据

多表查询的分类

* **内连接查询**

  * 隐式

    ```sql
    -- 例子 查询员工表的名称，性别。部门表的名称
    SELECT
    	t1.name,
    	t1.gender,
    	t2.name
    FROM    -- 先写FROM的内容
    	emp t1,
    	dept t2
    WHERE
    	t1.`dept_id` = t2.`id`;
    ```

  * 显式

    ```sql
    SELECT 字段列表 FROM 表名1 [INNER] JOIN 表名2 ON 条件; 
    ```

  * 内连接查询基本逻辑

    > 从哪些表中查询数据
    >
    > 条件是什么
    >
    > 查询哪些字段 

* **外链接查询**

  * 左外连接查询

    查询的是左表所有的数据以及其交集部分

    ```sql
    SELECT 字段列表 FROM 表名1 LEFT [OUTER] JOIN 表名2 ON 条件; 
    ```

  * 右外连接查询

    查询的是右表所有的数据以及其交集部分

    ```sql
    SELECT 字段列表 FROM 表名1 RIGHT [OUTER] JOIN 表名2 ON 条
    ```

* **子查询**

  查询中嵌套查询

  ```sql
  -- 例如 查询工资最高的员工信息
  
  -- 1 查询工资最高是多少  如9000
  SELECT MAX(salary) FROM emp;
  -- 2 查询工资 9000 的员工信息的员工信息
  SELECT * FROM emp WHERE EMP.`salary` = 9000;
  
  -- 子查询  一条sql语句就完成
  SELECT * FROM emp WHERE EMP.`salary` = (SELECT MAX(salary) FROM emp);
  ```

子查询的不同结果

* 子查询的结果是单行单列：

  * 子查询可以作为条件，使用运算符去判断。
  * 运算符：` <,	<=, 	>,	>=,	=`
  * 例如：查询工资小于平均工资的员工信息

* 子查询的结果是多行单列（集合）：

  * 子查询可以作为条件，使用运算符`in`去判断。
  * 例如：查询 ‘市场部’ 和 ’财务部‘ 的所有员工信息

* 子查询的结果是多行多列：

  * 子查询可以作**为一张虚拟表**参与查询。

  * 例如：查询入职日期是2020-11-11之后的员工信息及其所在部门信息

    

## 多表查询练习

假设数据库中有 dep（部门表）、emp（员工表）、job（职位表）和 salarygrade （薪资等级表）四个表

#### 1. 查询所有员工信息。查询员工编号、姓名、工资，职务名称、职务描述

* 分析：

  > 1. 员工编号、姓名、工资， 需要查询 emp 表    职务名称、职务描述， 需要查询 job 表
  > 2. 查询条件：emp.job_id = job.id

  ```sql
  SELECT 
  	t1.`id,`  -- 员工编号
  	t1.`ename`, -- 姓名
  	t1.`sarlary`, -- 工资
  	t2.`jname`, -- 职务名称
  	t2.`description` -- 职务描述
  FROM 
  	emp t1,
  	job t2
  WHERE
  	t1.`job_id` = t2.`id`
  ```

  

#### 2. 查询员工编号、姓名、工资，职务名称、职务描述，部门名称、部门位置

* 分析：

> 1. 员工编号、姓名、工资， 需要查询 emp 表    职务名称、职务描述， 需要查询 job 表	部门名称、部门位置， 需要查询 dept 表 
> 2. 查询条件：emp.job_id = job.id  and  emp.dept_id = dept.id

```sql
SELECT 
	t1.`id,`  -- 员工编号
	t1.`ename`, -- 姓名
	t1.`sarlary`, -- 工资
	t2.`jname`, -- 职务名称
	t2.`description`, -- 职务描述
	t3.`dname`, -- 部门名称
	t3.`loc` -- 部门位置
FROM 
	emp t1,
	job t2,
	dept t3
WHERE
	t1.`job_id` = t2.`id` AND t1.`dept_id` = t3.`id`
```



#### 3. 查询员工姓名、工资、工资等级

* 分析：

> 1. 员工姓名、工资， 需要查询 emp 表    工资等级， 需要查询 salarygrade 表
> 2. 查询条件：emp.salary between salarygrade.losalary and salarygrade.hisalary

```sql
SELECT 
	t1.`ename`, -- 姓名
	t1.`sarlary`, -- 工资
	t2.`grade`, -- 工资等级
FROM 
	emp t1,
	salarygrade t2
WHERE
	t1.`salary` BETWEEN t2.`losalary` AND t2.`hisalary`
```



#### 4. 查询员工姓名、工资，职务名称、职务描述，部门名称、部门位置、工资等级

* 分析：

> 1. 员工姓名、工资， 需要查询 emp 表    职务名称、职务描述， 需要查询 job 表	部门名称、部门位置， 需要查询 dept 表 	工资等级， 需要查询 salarygrade 表
> 2. 查询条件：emp.job_id = job.id and emp.dept_id = dept.id and emp.salary between salarygrade.losalary and salarygrade.hisalary

```sql
SELECT 
	t1.`ename`, -- 姓名
	t1.`sarlary`, -- 工资
	t2.`jname`, -- 职务名称
	t2.`description`, -- 职务描述
	t3.`dname`, -- 部门名称
	t3.`loc` -- 部门位置
	t4.`grade`, -- 工资等级
FROM 
	emp t1,
	job t2,
	dept t3,
	salarygrade t4
WHERE
	t1.`job_id` = t2.`id` 
	AND t1.`dept_id` = t3.`id` 
	AND t1.`salary` BETWEEN t4.`losalary` AND t4.`hisalary`
```



#### 5. 查询部门编号、部门名称、部门位置、部门人数

* 分析：

> 1. 部门编号、部门名称、部门位置， 需要查询 dept 表    部门人数， 需要查询 emp 表
> 2. 使用分组查询。按照emp.dept_id完成分组，查询count(id)
> 3. 使用子查询将第2步的查询结果和dept表进行关联查询

```sql
-- 分组查询 按照emp.dept_id完成分组，查询count(id)
SELECT 
	dept_id,COUNT(id)	
FROM 
	emp 
GROUP BY dept_id

-- ---------

SELECT 
	t1.`id`, -- 部门编号
	t1.`dname`, -- 部门名称
	t1.`loc`, -- 部门位置
	t2.total -- 部门人数
FROM
	dept t1,
	(SELECT 
        dept_id,COUNT(id) total
    FROM 
        emp 
    GROUP BY dept_id) t2
WHERE t1.`id` = t2.`dept_id`;
```



#### 6. 查询所有员工的姓名及其直接上级的姓名，没有领导的员工也需要查询

* 分析：

> 1. 员工姓名， 查询 emp 表    直接上级的姓名， 查询 emp 表  
>
>     emp表的 id 和 mgr 是自关联
>
> 2. 查询条件：emp.id = emp.mgr
>
> 3. 查询左表的所有数据，左外连接

```sql
--  普通查询  领导为NULL的员工无法查询
SELECT 
	t1.`ename`, -- 员工姓名
	t1.mgr, -- 员工上级
	t2.`id`, -- 上级 ID
	t2.`ename`, -- 上级姓名
FROM 
	emp t1，
	emp t2
WHERE
	t1.`mgr` = t2.`id`

-- --- 左外链接 没有领导的员工也查询
SELECT 
	t1.`ename`, -- 员工姓名
	t1.mgr, -- 员工上级
	t2.`id`, -- 上级 ID
	t2.`ename`, -- 上级姓名
FROM 
	emp t1
LEFT JOIN
	emp t2
ON
	t1.`mgr` = t2.`id`	
```



## 事务

#### 1. 事务的基本介绍

**概念**：如果一个包含多个步骤的业务操作，被数位管理，那么这些操作要么同时成功，要么同时失败。

**操作**：

* 开启事务 start transaction ;

* 回滚 rollback;

* 提交 commit;

  

MySQL数据库中事务会自动提交

* 一条DML（增删改）语句会自动提交一次事务
* 事务提交的两种方式
  * 自动提交：MySQL会自动提交
  * 手动提交： Oracle 默认手动。 需先开启事务，再手动提交
* 修改事务的默认提交方式
  * 查看：SELECT @@autocommit; -- 1代表自动提交   0代表手动提交
  * 修改：SET @@autocommit = 0;

#### 2. 事务的四大特征

* **原子性**：是不可分割的最新操作单位，要么同时成功，要么同时失败
* **持久性**：当事务提交或回滚后，数据库会持久化的保存是数据
* **隔离性**：多个事务之间，相互独立
* **一致性**：事务操作前后，数据总量不变

#### 3. 事务的隔离级别（了解）

**概念**：多个事务之间隔离的，相互独立的。但是如果多个事务操作同一批数据，则会引发一些问题，设置不同的隔离级别就可以解决这些问题

**存在问题**：

* **脏读**：一个事务，读到另外一个事务中没有提交的数据
* **不可重复读**（虚读）：在同一个事务中，两次读到的数据不一样
* **幻读**：一个事务操作（DML）数据表中所有记录，另一个事务添加了一条数据，则第一个事务查询不到自己的修改

**隔离级别**

1. read uncommited：读未提交
   * 产生的问题：脏读、不可重复读、幻读

2. read commited：读已提交 **（Oracle默认）**
   * 产生的问题：不可重复读、幻读

3. repeatable read：可重复读**（MySQl默认）**
   * 产生的问题：幻读

4. serializable：串行化
   * 解决所有问题

注意：隔离级别从小到大安全级别越来越高，但是效率越来越低

数据库隔离级别查询：`select @@tx_isolation;`

数据库隔离级别设置：` set global transaction isolation level 级别字符串;`



## DCL 授权、管理用户

#### 1. 管理用户

* 添加用户：`CREATE USER '用户名'@'主机名' INDETIFIED BY '密码'`; 

* 删除用户：`DROP '用户名'@'主机名';  ` 

* 修改用户密码：

  ```sql
  -- 一般方法
  UPDATE USER SET PASSWORD = PASSWORD('新密码') = '用户名';
  
  -- DCL 特有
  SET PASSWORD FOR '用户名'@'主机名' = PASSWORD('新密码');
  ```

  mysql中忘记root用户密码：

  > 1.  cmd（管理员运行） -->  net  stop mysl   停止mysql服务 
  > 2. 使用无验证方式启动mysql服务： `mysql --skip-grant-tables`
  > 3. 新 cmd  -->  mysql ，回车登录
  > 4. `use mysql;`
  > 5. `UPDATE USER SET PASSWORD = PASSWORD('新密码') = 'root';`
  > 6. 关闭两个窗口
  > 7. 任务管理器，手动结束mysql.exe 进程
  > 8. 启动 mysql 服务，cdm -->  net  start mysl
  > 9. 使用新密码登录

* 查询用户：

  ```sql
  -- 1. 切换到mysql数据库
  use mysql;
  -- 2. 查询user表
  SELCTE * FROM USER;
  ```

  通配符： % 表示可以在任意主机使用用户登录数据库

#### 2. 权限管理

* 查询权限

  ```sql
  SHOW GRANTS FOR '用户名'@'主机名'; 
  ```

* 授予权限

  ```sql
  GRANT 权限列表 ON 数据库名.表名 TO '用户名'@'主机名';
  ```

* 撤销权限

  ```sql
  REVOKE 权限列表 ON 数据库名.表名 FROM '用户名'@'主机名';
  ```

  