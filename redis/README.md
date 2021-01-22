# Redis
在SpringBoot中使用Redis的关键
1. 在运行环境安装`Redis`
2. 在`pom.xml`文件中添加`redis`的依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    ```
3. 在application.yml文件中设置`redis`的参数
    ```yml
   spring:
     redis:
       host: 127.0.0.1
       password: 123456789
       database: 2
       port: 6379
       jedis:
         pool:
           max-wait: 30000
           max-active: 100
           max-idle: 20
           min-idle: 0
       timeout: 3000
   ```
4. 创建`RedisConfig.java`
5. 创建`RedisUtil.java`来间接使用`RedisTemplate`
6. 创建`Status.java`
7. 使用`RedisUtil.java`的函数来操作`Redis`数据库