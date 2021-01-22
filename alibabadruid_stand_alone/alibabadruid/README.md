# Alibaba Druid

1. `pom.xml`

   ```xml
   		<dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid</artifactId>
               <version>1.1.9</version>
           </dependency>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <scope>runtime</scope>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-jdbc</artifactId>
           </dependency>
           <!-- https://mvnrepository.com/artifact/log4j/log4j -->
           <dependency>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
               <version>1.2.17</version>
           </dependency>
   ```

2. `application.yml`
   > [配置属性说明](https://www.cnblogs.com/cymiao/p/8432681.html)

   ```yml
   spring:
     datasource:
       username: root
       password: 123456789
       url: jdbc:mysql://localhost:3306/alibabadruid?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
       driver-class-name: com.mysql.cj.jdbc.Driver
       type: com.alibaba.druid.pool.DruidDataSource
       #   数据源其他配置
       initialSize: 5
       minIdle: 5
       maxActive: 20
       maxWait: 60000
       timeBetweenEvictionRunsMillis: 60000
       minEvictableIdleTimeMillis: 300000
       validationQuery: SELECT 1 FROM DUAL
       testWhileIdle: true
       testOnBorrow: false
       testOnReturn: false
       poolPreparedStatements: true
       #   配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
       filters: stat,wall,log4j
       maxPoolPreparedStatementPerConnectionSize: 20
       useGlobalDataSourceStat: true
       connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
   ```

   

3. `config>DataSourcesConfig.java`

   ```java
   package com.example.alibabadruid.config;
   
   import com.alibaba.druid.pool.DruidDataSource;
   import com.alibaba.druid.support.http.StatViewServlet;
   import com.alibaba.druid.support.http.WebStatFilter;
   import org.springframework.boot.context.properties.ConfigurationProperties;
   import org.springframework.boot.web.servlet.FilterRegistrationBean;
   import org.springframework.boot.web.servlet.ServletRegistrationBean;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import javax.sql.DataSource;
   import java.util.Arrays;
   import java.util.HashMap;
   import java.util.Map;
   
   @Configuration
   public class DataSourcesConfig {
   
       @Bean
       @ConfigurationProperties(prefix = "spring.datasource")
       public DataSource getDataSources() {
           return new DruidDataSource();
       }
   
       /**
        * 配置druid后台管理的servlet
        * 访问连接http://127.0.0.1:port/druid
        * @return
        */
       @Bean
       public ServletRegistrationBean druidServlet() {
           ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
           Map<String, String> initParams = new HashMap<>();
           initParams.put("loginUsername", "admin");
           initParams.put("loginPassword", "admin");
           initParams.put("allow", "");//默认就是允许所有访问
           initParams.put("deny", "192.168.15.21");//禁止访问的ip
           bean.setInitParameters(initParams);
           return bean;
       }
   
       /**
        * 配置druid后台管理的filter
        *
        * @return
        */
       @Bean
       public FilterRegistrationBean druidFilter() {
           FilterRegistrationBean bean = new FilterRegistrationBean();
           bean.setFilter(new WebStatFilter());
           Map<String, String> initParams = new HashMap<>();
           //设置不拦截的路径  *.cs *.js    /druid/*
           initParams.put("exclusions","*.js,*.css,/druid/*");
           bean.setInitParameters(initParams);
           //设置filter拦截 那些请求
           bean.setUrlPatterns(Arrays.asList("/*"));
           return bean;
       }
   
   
   }
   ```


4. 访问Alibaba Druid的统计分析界面

   `http:\\host:port\druid`

