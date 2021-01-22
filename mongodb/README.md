# MongoDb
## 前期准备
1. 安装MongoDB并配置系统环境变量
2. 启动MongoDB服务
   ```shell
   mongod --dbpath E:\softwork\MongoDB\Server\4.4\data --logpath=E:\softwork\MongoDB\Server\4.4\log\mongodb.log --logappend
   ```
3. 进入MongoDB客户端
   * 进入数据库`mongodbtest1`
     ```test1
     use test1
     ```
   * 创建用户
       ```mongodb
        db.createUser({
            user: 'root',  // 用户名
            pwd: '123456789',  // 密码
            roles:[{
                role: 'readWrite',  // 角色
                db: 'test1'  // 数据库名
            }]
        })
       ```    
4. 找到 MongoDB 安装目录，打开 `mongod.cfg`文件
   
   将`#security:`修改为`security:authorization: enabled`
5. 重新启动MongoDB服务器
6. 进入MongoDB客户端
   ```shell
   mongo -u root -p root
   ```
## SpringBoot 配置
1. pom.xml
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    ```
2. 在`entity`中定义集合(collection)
    ```java
    package com.example.mongodb.entity;
    
    import lombok.Data;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.Document;
    
    import java.io.Serializable;
    @Data
    @Document(collection = "demo_collection")
    public class Demo implements Serializable {
    
        @Id
        private Long id;
    
        private String title;
    
        private String description;
    
        private String by;
    
        private String url;
    }
    ```
3. 在`service`中定义集合(collection)相应的`service`接口和`servicelmpl`实现类
通过`java`实现mongodb操作的关键在于`MongoTemplate`、`Criteria`、`Query`、`Update`子施四个类
    * `MongoTemplate`--实现sql操作
    * `Criteria`--sql条件
    * `Query`--查询实体
    * `Update`--更新实体
    
    接口：
    
    DemoService.java
    ```java
    package com.example.mongodb.service;
    
    import com.example.mongodb.entity.Demo;
    
    import java.util.List;
    
    public interface DemoService {
    
        void saveDemo(Demo demo);
    
        void removeDemo(Long id);
    
        void updateDemo(Demo demo);
    
        Demo findDemoById(Long id);
    
        List<Demo> findDemoByUrl(String url);
    }
    ```
   
    实现类：
    
    DemoServiceImpl.java
    ```java
    package com.example.mongodb.service;
    
    import com.example.mongodb.entity.Demo;
    import org.springframework.dao.QueryTimeoutException;
    import org.springframework.data.mongodb.core.MongoTemplate;
    import org.springframework.data.mongodb.core.query.Criteria;
    import org.springframework.data.mongodb.core.query.Query;
    import org.springframework.data.mongodb.core.query.Update;
    import org.springframework.stereotype.Component;
    import org.apache.commons.lang.StringUtils;
    
    import javax.annotation.Resource;
    import java.util.List;
    
    /**
     * 描述: Demo DAO 实现
     *
     * @author yanpenglei
     * @create 2018-02-03 16:57
     **/
    @Component
    public class DemoServiceImpl implements DemoService {
    
        @Resource
        private MongoTemplate mongoTemplate;
    
        @Override
        public void saveDemo(Demo demo) {
            mongoTemplate.save(demo);
        }
    
        @Override
        public void removeDemo(Long id) {
            Criteria criteria = Criteria.where("id").is(id);
            Query query = new Query(criteria);
            mongoTemplate.remove(query,Demo.class);
        }
    
        @Override
        public void updateDemo(Demo demo) {
            Criteria criteria = Criteria.where("id").is(demo.getId());
            Query query = new Query(criteria);
            Update update= new Update().set("title", demo.getTitle()).set("description", demo.getDescription())
                    .set("by", demo.getBy()).set("url", demo.getUrl());
            mongoTemplate.updateFirst(query,update,Demo.class);
        }
    
        @Override
        public Demo findDemoById(Long id) {
            Criteria criteria = Criteria.where("id").is(id);
            Query query = new Query(criteria);
            Demo demo = mongoTemplate.findOne(query, Demo.class);
            return demo;
        }
    
        @Override
        public List<Demo> findDemoByUrl(String url) {
            Criteria criteria = Criteria.where("url").is(url);
            Query query = new Query(criteria);
            return mongoTemplate.find(query,Demo.class);
        }
    
        static {
            StringUtils.isNumeric("");
        }
    }
    ```