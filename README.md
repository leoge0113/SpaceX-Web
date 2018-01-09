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
- 捕获全局错误

    实现GlobalExceptionResolver implements HandlerExceptionResolver 
    配置bean：
    <!--全局异常捕捉 -->
        <bean class="com.cainiao.exception.GlobalExceptionResolver" />
- redis 开启远程登录
    
    redis.conf
    1. \#bind localhost
    2. protected mode no
    3. 重启  
- spring 定时任务开发
- 集群预研之一致性hash

    分布式环境下评估hash算法好坏依据。
    1、平衡性(Balance)：平衡性是指哈希的结果能够尽可能分布到所有的缓冲中去，这样可以使得所有的缓冲空间都得到利用。很多哈希算法都能够满足这一条件。
    
    2、单调性(Monotonicity)：单调性是指如果已经有一些内容通过哈希分派到了相应的缓冲中，又有新的缓冲加入到系统中。哈希的结果应能够保证原有已分配的内容可以被映射到原有的或者新的缓冲中去，而不会被映射到旧的缓冲集合中的其他缓冲区。 
    
    3、分散性(Spread)：在分布式环境中，终端有可能看不到所有的缓冲，而是只能看到其中的一部分。当终端希望通过哈希过程将内容映射到缓冲上时，由于不同终端所见的缓冲范围有可能不同，从而导致哈希的结果不一致，最终的结果是相同的内容被不同的终端映射到不同的缓冲区中。这种情况显然是应该避免的，因为它导致相同内容被存储到不同缓冲中去，降低了系统存储的效率。分散性的定义就是上述情况发生的严重程度。好的哈希算法应能够尽量避免不一致的情况发生，也就是尽量降低分散性。 
    
    4、负载(Load)：负载问题实际上是从另一个角度看待分散性问题。既然不同的终端可能将相同的内容映射到不同的缓冲区中，那么对于一个特定的缓冲区而言，也可能被不同的用户映射为不同 的内容。与分散性一样，这种情况也是应当避免的，因此好的哈希算法应能够尽量降低缓冲的负荷。
    
    ```
    1 2 3 4 5 6 7 8 (key)
    mod 2:
    2 1 2 1 2 1 2 1
    mod 4:
    2 3 4 1 2 3 4 1
    
    
    1 2 3 4 5 6 7 8 9(key)
    mode 3：
    2 3 1 2 3 1 2 3 1
    mod 5
    2 3 4 1 2 3 4 1 2
    ```
    ==数据库分库分表时，选择2的幂次方，单调性比较好。mod 2^n 可以称为一致性hash==

-  redis m-s集群部署，代码调测
   
   遇到的问题总结见文档《redis cluster部署与开发问题总结》
- java8 optional 特性应用 