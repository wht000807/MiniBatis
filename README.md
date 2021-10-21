# 自实现持久层框架--MiniBatis

### JDBC的操作：

```java
public static void main(String[] args) {
 Connection connection = null;
 PreparedStatement preparedStatement = null;
 ResultSet resultSet = null;
 try {
 // 加载数据库驱动
 Class.forName("com.mysql.jdbc.Driver");
 // 通过驱动管理类获取数据库链接
 connection =
DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?
characterEncoding=utf-8", "root", "root");
 // 定义sql语句？表示占位符
 String sql = "select * from user where username = ?";
 // 获取预处理statement
 preparedStatement = connection.prepareStatement(sql);
 // 设置参数，第⼀个参数为sql语句中参数的序号(从1开始)，第⼆个参数为设置的参数值
preparedStatement.setString(1, "tom");
 // 向数据库发出sql执⾏查询，查询出结果集
 resultSet = preparedStatement.executeQuery();
 // 遍历查询结果集
 while (resultSet.next()) {
 int id = resultSet.getInt("id");
 String username = resultSet.getString("username");
 // 封装User
 user.setId(id);
 user.setUsername(username);
 }
 System.out.println(user);
 }
 } catch (Exception e) {
 e.printStackTrace();
 } finally {
 // 释放资源
 if (resultSet != null) {
     ...
 }
		
}
```



### 传统JDBC问题：

1、 数据库连接创建、释放**频繁**造成系统资源浪费，从⽽影响系统性能。 

2、 Sql语句在代码中**硬编码**，造成代码不易维护，实际应⽤中sql变化的可能较⼤，sql变动需要改变 java代码。 

3、 使⽤preparedStatement向占有位符号传参数存在**硬编码**，因为sql语句的where条件不⼀定，可能 多也可能少，修改sql还要修改代码，系统不易维护。

4、 对结果集解析存在**硬编码**(查询列名)，sql变化导致解析代码变化，系统**不易维护**，如果能将数据 库 记录封装成pojo对象解析⽐较⽅便



### 解决思路：

①使⽤数据库连接池初始化连接资源 

②将sql语句抽取到xml配置⽂件中 

③使⽤反射、内省等底层技术，⾃动将实体与表进⾏属性与字段的⾃动映射



### 自定义框架设计：

**使用端：**引入自定义持久层框架的jar包

提供两部分配置信息：1. 数据库配置信息      

​										2. SQL配置信息：sql语句，参数类型，返回值类型

​		

解决：引入两种配置文件：

  		1. sqlMapConfiguration.xml:存放数据库配置信息，存放mapper.xml的全路径
  		2. mapper.xml:存放sql配置信息



**框架端：** 本质就是对JDBC的封装

1.加载配置文件：根据配置文件路径，加载配置文件成字节输入流，存储在内存中

​	创建Resources类  方法：**InputStream  getResourceAsStream(String path)**



2存储配置⽂件 

读取完成以后以**流的形式存在**，我们不能将读取到的配置信息以流的形式存放在内存中，不好操作，可以创建javaBean来存储 

（1）Configuration : 存放数据库基本信息、Map<唯⼀标识，Mapper> 唯⼀标识：namespace + "." + id 

（2）MappedStatement：sql语句、statement类型、输⼊参数java类型、输出参数java类型 



3.解析配置⽂件 

创建sqlSessionFactoryBuilder类： 

⽅法：sqlSessionFactory.build()： 

第⼀：使⽤dom4j解析配置⽂件，将解析出来的内容封装到Configuration和MappedStatement中 

第⼆：创建SqlSessionFactory的实现类DefaultSqlSession 



4.创建SqlSessionFactory： 

⽅法：openSession() : 获取sqlSession接⼝的实现类实例对象 



5.创建sqlSession接⼝及实现类：主要封装crud⽅法 

⽅法：selectList(String statementId,Object param)：查询所有 

​			selectOne(String statementId,Object param)：查询单个 

具体实现：封装JDBC完成对数据库表的查询操作



6.创建Executor接口及实现类SimpleExecutor实现类

​		query(Configuration,MappedStatement,Object... params): 执行的就是JDBC代码

