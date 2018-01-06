## 最初的梦想
三步走：
- 构建高（物理库，内存库）可用ssm web应用；
- web服务性能提升：tomcat集群与页面分离；
- 拆分为分布式架构
## maven依赖
![image](https://github.com/leoge0113/elegant_ssm/blob/master/image/ElegantSSM.jpg)

## 开发点滴
- 类构造函数特殊性造成该类对象protostuff反序列化问题

    已修复。
- 添加lambda表达式练习代码

    为了写redis操作的代码。
    函数接口是个泛型接口，lambda怎么写。
- redis 应用

    添加，
    删除（模糊/精确），查找，清空等。
    
    应用场景：为什么用内存库，什么时候添加，expiretime为什么要，什么时候清空
- service层里spring数据库事务的应用
- 自动义runtimeexception使用
- controler里使用注解@valid对form表单进行校验
- 自定义注解使用
- 采用AOP的方式判断验证结果
- Druid网络统计与监控
    
