# Alibaba Druid Colony

## 项目结构图

![项目结构图](https://raw.githubusercontent.com/GBSTS/StaticSource/main/image/202012311355.png)

## `SpringBoot`项目使用多个数据库(或者集群)的关键！

使用数据库连接池，并且分别定义对于不同的数据库的一系列`config`、`entity`、`mapper`、`dao`、`service`、······

## 整体项目代码

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

   ```yaml
   spring:
     datasource:
       type: com.alibaba.druid.pool.DruidDataSource
       # 数据源其他配置
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
       # 配置监控统计拦截的filters，去除后监控界面sql无法统计，'wall'用于防火墙
       filters: stat,wall,log4j
       maxPoolPreparedStatementPerConnectionSize: 20
       useGlobalDataSourceStat: true
       connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
   
   #配置了两个数据源
   #masterdb数据库
   master:
     datasource:
       url: jdbc:mysql://localhost:3306/masterdb?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
       username: root
       password: 123456789
       driver-class-name: com.mysql.cj.jdbc.Driver
   
   #clusterdb数据库
   cluster:
     datasource:
       url: jdbc:mysql://localhost:3306/clusterdb?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
       username: root
       password: 123456789
       driver-class-name: com.mysql.cj.jdbc.Driver
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

4. 两个数据库分别的配置

   * `masterdb`数据库---`MasterDataSourceConfig.java`

     ```java
     package com.example.alibabadruid.config;
     
     import com.alibaba.druid.pool.DruidDataSource;
     import org.apache.ibatis.session.SqlSessionFactory;
     import org.mybatis.spring.SqlSessionFactoryBean;
     import org.mybatis.spring.annotation.MapperScan;
     import org.springframework.beans.factory.annotation.Qualifier;
     import org.springframework.beans.factory.annotation.Value;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.context.annotation.Primary;
     import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
     import org.springframework.jdbc.datasource.DataSourceTransactionManager;
     
     import javax.sql.DataSource;
     import java.sql.SQLException;
     
     /**
      * 主数据源配置
      */
     @Configuration
     @MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
     public class MasterDataSourceConfig {
     
         /**
          * 配置多数据源 关键就在这里 这里配置了不同的数据源扫描不同mapper
          */
         static final String PACKAGE = "com.example.alibabadruid.dao.master";
         static final String MAPPER_LOCATION = "classpath:/mappers/master/*Mapper.xml";
     
         /**
          * 连接数据库信息 这个其实更好的是用配置中心完成
          */
         @Value("${master.datasource.url}")
         private String url;  
           
         @Value("${master.datasource.username}")
         private String username;  
           
         @Value("${master.datasource.password}")
         private String password;  
           
         @Value("${master.datasource.driver-class-name}")
         private String driverClassName;
     
     
         /**
          * 下面的配置信息可以读取配置文件，其实可以直接写死 如果是多数据源的话 还是考虑读取配置文件
          */
         @Value("${spring.datasource.initialSize}")
         private int initialSize;  
           
         @Value("${spring.datasource.minIdle}")
         private int minIdle;  
           
         @Value("${spring.datasource.maxActive}")
         private int maxActive;  
           
         @Value("${spring.datasource.maxWait}")
         private int maxWait;  
           
         @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
         private int timeBetweenEvictionRunsMillis;  
           
         @Value("${spring.datasource.minEvictableIdleTimeMillis}")
         private int minEvictableIdleTimeMillis;  
           
         @Value("${spring.datasource.validationQuery}")
         private String validationQuery;  
           
         @Value("${spring.datasource.testWhileIdle}")
         private boolean testWhileIdle;  
           
         @Value("${spring.datasource.testOnBorrow}")
         private boolean testOnBorrow;  
           
         @Value("${spring.datasource.testOnReturn}")
         private boolean testOnReturn;  
           
         @Value("${spring.datasource.poolPreparedStatements}")
         private boolean poolPreparedStatements;  
           
         @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
         private int maxPoolPreparedStatementPerConnectionSize;  
           
         @Value("${spring.datasource.filters}")
         private String filters;
           
         @Value("{spring.datasource.connectionProperties}")
         private String connectionProperties;  
         
         
         @Bean(name = "masterDataSource")
         @Primary //标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。
         public DataSource masterDataSource() {
             DruidDataSource dataSource = new DruidDataSource();
             dataSource.setUrl(url);  
             dataSource.setUsername(username);  
             dataSource.setPassword(password);  
             dataSource.setDriverClassName(driverClassName);  
               
             //具体配置 
             dataSource.setInitialSize(initialSize);  
             dataSource.setMinIdle(minIdle);  
             dataSource.setMaxActive(maxActive);  
             dataSource.setMaxWait(maxWait);  
             dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
             dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
             dataSource.setValidationQuery(validationQuery);  
             dataSource.setTestWhileIdle(testWhileIdle);  
             dataSource.setTestOnBorrow(testOnBorrow);  
             dataSource.setTestOnReturn(testOnReturn);  
             dataSource.setPoolPreparedStatements(poolPreparedStatements);  
             dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
     
             /**
              * 这个是用来配置 druid 监控sql语句的 非常有用 如果你有两个数据源 这个配置哪个数据源就监控哪个数据源的sql 同时配置那就都监控
              */
             try {
                 dataSource.setFilters(filters);
             } catch (SQLException e) {
             	e.printStackTrace();
             }
             dataSource.setConnectionProperties(connectionProperties);  
             return dataSource;
         }
     
         @Bean(name = "masterTransactionManager")
         @Primary
         public DataSourceTransactionManager masterTransactionManager() {
             return new DataSourceTransactionManager(masterDataSource());
         }
     
         @Bean(name = "masterSqlSessionFactory")
         @Primary
         public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
                 throws Exception {
             final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
             sessionFactory.setDataSource(masterDataSource);
             sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MasterDataSourceConfig.MAPPER_LOCATION));
             
             return sessionFactory.getObject();
         }
     }
     ```

   * `clusterdb`数据库---`ClusterDataSourceConfig.java`

     ```java
     package com.example.alibabadruid.config;
     
     import com.alibaba.druid.pool.DruidDataSource;
     import org.apache.ibatis.session.SqlSessionFactory;
     import org.mybatis.spring.SqlSessionFactoryBean;
     import org.mybatis.spring.annotation.MapperScan;
     import org.springframework.beans.factory.annotation.Qualifier;
     import org.springframework.beans.factory.annotation.Value;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.context.annotation.Primary;
     import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
     import org.springframework.jdbc.datasource.DataSourceTransactionManager;
     
     import javax.sql.DataSource;
     import java.sql.SQLException;
     /**
      * 次数据源 另一个数据源配置
      */
     @Configuration
     @MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
     public class ClusterDataSourceConfig {
     
         /**
          * 配置多数据源 关键就在这里 这里配置了不同数据源扫描不同的mapper
          */
         static final String PACKAGE = "com.example.alibabadruid.dao.cluster";
         static final String MAPPER_LOCATION = "classpath:/mappers/cluster/*Mapper.xml";
     
         @Value("${cluster.datasource.url}")
         private String url;
     
         @Value("${cluster.datasource.username}")
         private String username;
     
         @Value("${cluster.datasource.password}")
         private String password;
     
         @Value("${master.datasource.driver-class-name}")
         private String driverClass;
     
         @Bean(name = "clusterDataSource")
         public DataSource clusterDataSource() {
             DruidDataSource dataSource = new DruidDataSource();
             dataSource.setUrl(url);
             dataSource.setUsername(username);
             dataSource.setPassword(password);
             dataSource.setDriverClassName(driverClass);
     
             //具体配置
             try {
                 dataSource.setFilters("stat,wall,slf4j");
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             return dataSource;
         }
     
         @Bean(name = "clusterTransactionManager")
         public DataSourceTransactionManager clusterTransactionManager() {
             return new DataSourceTransactionManager(clusterDataSource());
         }
     
         @Bean(name = "clusterSqlSessionFactory")
         public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
                 throws Exception {
             final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
             sessionFactory.setDataSource(clusterDataSource);
     
             sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
             return sessionFactory.getObject();
         }
     }
     ```

5. 两个数据库分别的`entity`

   * `masterdb`数据库对应的实体类

     ```java
     package com.example.alibabadruid.entity.master;
     
     
     public class Rights {
     	private Integer rightid;
     	private String url;
     	private String rolenames;
     
     	public void setRightid(Integer rightid) {
     		this.rightid = rightid;
     	}
     	
     	public Integer getRightid() {
     		return this.rightid;
     	}
     	
     	public void setUrl(String url) {
     		this.url = url;
     	}
     	
     	public String getUrl() {
     		return this.url;
     	}
     	
     	public void setRolenames(String rolenames) {
     		this.rolenames = rolenames;
     	}
     	
     	public String getRolenames() {
     		return this.rolenames;
     	}
     
     	public Rights() {
     	}
     
     	public Rights(Integer rightid, String url, String rolenames) {
     		this.rightid = rightid;
     		this.url = url;
     		this.rolenames = rolenames;
     	}
     
     	@Override
         public boolean equals(Object o) {
             if (this == o) { return true; }
             if (o == null || getClass() != o.getClass()) {return false;}
             Rights that = (Rights) o;
             return rightid.equals(that.rightid);
         }
     
         @Override
         public int hashCode() {
             return java.util.Objects.hash(rightid);
         }
         
         @Override
         public String toString() {
             return "Rights{" +
     				"rightid=" + rightid +
     						",url='" + url + "'" + 
     						",rolenames='" + rolenames + "'" + 
     		                '}';
         }
     	
     }
     ```

   * `clusterdb`数据库对应的实体类

     ```java
     package com.example.alibabadruid.entity.cluster;
     
     
     public class Rights {
     	private Integer rightid;
     	private String url;
     	private String rolenames;
     
     	public void setRightid(Integer rightid) {
     		this.rightid = rightid;
     	}
     	
     	public Integer getRightid() {
     		return this.rightid;
     	}
     	
     	public void setUrl(String url) {
     		this.url = url;
     	}
     	
     	public String getUrl() {
     		return this.url;
     	}
     	
     	public void setRolenames(String rolenames) {
     		this.rolenames = rolenames;
     	}
     	
     	public String getRolenames() {
     		return this.rolenames;
     	}
     
     	public Rights() {
     	}
     
     	public Rights(Integer rightid, String url, String rolenames) {
     		this.rightid = rightid;
     		this.url = url;
     		this.rolenames = rolenames;
     	}
     
     	@Override
         public boolean equals(Object o) {
             if (this == o) { return true; }
             if (o == null || getClass() != o.getClass()) {return false;}
             Rights that = (Rights) o;
             return rightid.equals(that.rightid);
         }
     
         @Override
         public int hashCode() {
             return java.util.Objects.hash(rightid);
         }
         
         @Override
         public String toString() {
             return "Rights{" +
     				"rightid=" + rightid +
     						",url='" + url + "'" + 
     						",rolenames='" + rolenames + "'" + 
     		                '}';
         }
     	
     }
     ```

6. 两个数据库分别的`dao`

   * `masterdb`数据库对应的`dao`

     ```java
     package com.example.alibabadruid.dao.master;
     
     import java.util.List;
     
     import com.example.alibabadruid.entity.master.Rights;
     import org.apache.ibatis.annotations.Mapper;
     
     @Mapper
     public interface MasterRightsDao {
     
     	/**
          * 查询所有记录
          *
          * @return 返回集合，没有返回空List
          */
     	List<Rights> listAll();
     
     
     	/**
          * 根据主键查询
          *
          * @param rightid 主键
          * @return 返回记录，没有返回null
          */
     	Rights getById(Integer rightid);
     	
     	/**
          * 新增，插入所有字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
     	int insert(Rights rights);
     	
     	/**
          * 新增，忽略null字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
     	int insertIgnoreNull(Rights rights);
     	
     	/**
          * 修改，修改所有字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
     	int update(Rights rights);
     	
     	/**
          * 修改，忽略null字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
     	int updateIgnoreNull(Rights rights);
     	
     	/**
          * 删除记录
          *
          * @param rights 待删除的记录
          * @return 返回影响行数
          */
     	int delete(Rights rights);
     	
     }
     ```

   * `clusterdb`数据库对应的`dao`

     ```java
     package com.example.alibabadruid.dao.cluster;
     
     import com.example.alibabadruid.entity.cluster.Rights;
     import org.apache.ibatis.annotations.Mapper;
     
     import java.util.List;
     
     @Mapper
     public interface ClusterRightsDao {
     
     	/**
          * 查询所有记录
          *
          * @return 返回集合，没有返回空List
          */
     	List<Rights> listAll();
     
     
     	/**
          * 根据主键查询
          *
          * @param rightid 主键
          * @return 返回记录，没有返回null
          */
     	Rights getById(Integer rightid);
     	
     	/**
          * 新增，插入所有字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
     	int insert(Rights rights);
     	
     	/**
          * 新增，忽略null字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
     	int insertIgnoreNull(Rights rights);
     	
     	/**
          * 修改，修改所有字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
     	int update(Rights rights);
     	
     	/**
          * 修改，忽略null字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
     	int updateIgnoreNull(Rights rights);
     	
     	/**
          * 删除记录
          *
          * @param rights 待删除的记录
          * @return 返回影响行数
          */
     	int delete(Rights rights);
     	
     }
     ```

7. 两个数据库分别的`mapper`

   * `masterdb`数据库对应的`mapper`

     ```xml
     <?xml version="1.0" encoding="UTF-8" ?>
     <!DOCTYPE  mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
     <mapper namespace="com.example.alibabadruid.dao.master.MasterRightsDao">
         <resultMap id="BaseResultMap" type="com.example.alibabadruid.entity.master.Rights">
             <result column="rightid" property="rightid" />
             <result column="url" property="url" />
             <result column="rolenames" property="rolenames" />
         </resultMap>
     
         <!-- 表字段 -->
         <sql id="baseColumns">
              t.rightid
             , t.url
             , t.rolenames
             </sql>
     
         <!-- 查询全部 -->
         <select id="listAll" resultMap="BaseResultMap">
             SELECT
             <include refid="baseColumns" />
             FROM rights t
         </select>
     
         <!-- 根据主键获取单条记录 -->
         <select id="getById" resultMap="BaseResultMap" parameterType="Integer">
             SELECT
             <include refid="baseColumns" />
             FROM rights t
             WHERE rightid = #{rightid}
         </select>
     
         <!-- 插入全部字段 -->
         <insert id="insert" parameterType="com.example.alibabadruid.entity.master.Rights"
                 keyProperty="rightid" keyColumn="rightid" useGeneratedKeys="true"
         >
             INSERT INTO rights
             <trim prefix="(" suffix=")" suffixOverrides=",">
                 url,
                 rolenames,
             </trim>
             <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
                 #{url},
                 #{rolenames},
             </trim>
         </insert>
     
         <!-- 插入不为NULL的字段 -->
         <insert id="insertIgnoreNull" parameterType="com.example.alibabadruid.entity.master.Rights"
                 keyProperty="rightid" keyColumn="rightid" useGeneratedKeys="true"
         >
             INSERT INTO rights
             <trim prefix="(" suffix=")" suffixOverrides=",">
     
                 <if test="url != null">
                     url,
                 </if>
                 <if test="rolenames != null">
                     rolenames,
                 </if>
             </trim>
             <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
                 <if test="url != null" >
                     #{url},
                 </if>
                 <if test="rolenames != null" >
                     #{rolenames},
                 </if>
             </trim>
         </insert>
     
         <!-- 更新,更新全部字段 -->
         <update id="update" parameterType="com.example.alibabadruid.entity.master.Rights">
             UPDATE rights
             <set>
                 url=#{url},
                 rolenames=#{rolenames},
             </set>
             WHERE rightid = #{rightid}
         </update>
     
     
         <!-- 更新不为NULL的字段 -->
         <update id="updateIgnoreNull" parameterType="com.example.alibabadruid.entity.master.Rights">
             UPDATE rights
             <set>
                 <if test="url != null" >
                     url=#{url},
                 </if>
                 <if test="rolenames != null" >
                     rolenames=#{rolenames},
                 </if>
             </set>
             WHERE rightid = #{rightid}
         </update>
     
     
         <!-- 根据主键删除记录 -->
         <delete id="delete" parameterType="com.example.alibabadruid.entity.master.Rights">
     		DELETE FROM rights
     		WHERE rightid = #{rightid}
     	</delete>
     
     
     </mapper>
     ```

   * `clusterdb`数据库对应的`mapper`

     ```xml
     <?xml version="1.0" encoding="UTF-8" ?>
     <!DOCTYPE  mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
     <mapper namespace="com.example.alibabadruid.dao.cluster.ClusterRightsDao">
         <resultMap id="BaseResultMap" type="com.example.alibabadruid.entity.cluster.Rights">
             <result column="rightid" property="rightid" />
             <result column="url" property="url" />
             <result column="rolenames" property="rolenames" />
         </resultMap>
     
         <!-- 表字段 -->
         <sql id="baseColumns">
              t.rightid
             , t.url
             , t.rolenames
             </sql>
     
         <!-- 查询全部 -->
         <select id="listAll" resultMap="BaseResultMap">
             SELECT
             <include refid="baseColumns" />
             FROM rights t
         </select>
     
         <!-- 根据主键获取单条记录 -->
         <select id="getById" resultMap="BaseResultMap" parameterType="Integer">
             SELECT
             <include refid="baseColumns" />
             FROM rights t
             WHERE rightid = #{rightid}
         </select>
     
         <!-- 插入全部字段 -->
         <insert id="insert" parameterType="com.example.alibabadruid.entity.cluster.Rights"
                 keyProperty="rightid" keyColumn="rightid" useGeneratedKeys="true"
         >
             INSERT INTO rights
             <trim prefix="(" suffix=")" suffixOverrides=",">
                 url,
                 rolenames,
             </trim>
             <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
                 #{url},
                 #{rolenames},
             </trim>
         </insert>
     
         <!-- 插入不为NULL的字段 -->
         <insert id="insertIgnoreNull" parameterType="com.example.alibabadruid.entity.cluster.Rights"
                 keyProperty="rightid" keyColumn="rightid" useGeneratedKeys="true"
         >
             INSERT INTO rights
             <trim prefix="(" suffix=")" suffixOverrides=",">
     
                 <if test="url != null">
                     url,
                 </if>
                 <if test="rolenames != null">
                     rolenames,
                 </if>
             </trim>
             <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
                 <if test="url != null" >
                     #{url},
                 </if>
                 <if test="rolenames != null" >
                     #{rolenames},
                 </if>
             </trim>
         </insert>
     
         <!-- 更新,更新全部字段 -->
         <update id="update" parameterType="com.example.alibabadruid.entity.cluster.Rights">
             UPDATE rights
             <set>
                 url=#{url},
                 rolenames=#{rolenames},
             </set>
             WHERE rightid = #{rightid}
         </update>
     
     
         <!-- 更新不为NULL的字段 -->
         <update id="updateIgnoreNull" parameterType="com.example.alibabadruid.entity.cluster.Rights">
             UPDATE rights
             <set>
                 <if test="url != null" >
                     url=#{url},
                 </if>
                 <if test="rolenames != null" >
                     rolenames=#{rolenames},
                 </if>
             </set>
             WHERE rightid = #{rightid}
         </update>
     
     
         <!-- 根据主键删除记录 -->
         <delete id="delete" parameterType="com.example.alibabadruid.entity.cluster.Rights">
     		DELETE FROM rights
     		WHERE rightid = #{rightid}
     	</delete>
     
     
     </mapper>
     ```

8. 两个数据库分别的`service`

   * `masterdb`数据库对应的`service`

     ```xml
     package com.example.alibabadruid.service.master;
     
     import com.example.alibabadruid.dao.master.MasterRightsDao;
     import com.example.alibabadruid.entity.master.Rights;
     import org.springframework.stereotype.Service;
     
     import javax.annotation.Resource;
     import java.util.List;
     
     @Service
     public class MasterRightsService {
     
         @Resource
         private MasterRightsDao masterRightsDao;
     
         /**
          * 查询所有记录
          *
          * @return 返回集合，没有返回空List
          */
         public List<Rights> listAll() {
         	return masterRightsDao.listAll();
         }
     
     
         /**
          * 根据主键查询
          *
          * @param rightid 主键
          * @return 返回记录，没有返回null
          */
         public Rights getById(Integer rightid) {
         	return masterRightsDao.getById(rightid);
         }
     	
         /**
          * 新增，插入所有字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
         public int insert(Rights rights) {
         	return masterRightsDao.insert(rights);
         }
     	
         /**
          * 新增，忽略null字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
         public int insertIgnoreNull(Rights rights) {
         	return masterRightsDao.insertIgnoreNull(rights);
         }
     	
         /**
          * 修改，修改所有字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
         public int update(Rights rights) {
         	return masterRightsDao.update(rights);
         }
     	
         /**
          * 修改，忽略null字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
         public int updateIgnoreNull(Rights rights) {
         	return masterRightsDao.updateIgnoreNull(rights);
         }
     	
         /**
          * 删除记录
          *
          * @param rights 待删除的记录
          * @return 返回影响行数
          */
         public int delete(Rights rights) {
         	return masterRightsDao.delete(rights);
         }
     	
     }
     ```

   * `clusterdb`数据库对应的`service`

     ```xml
     package com.example.alibabadruid.service.cluster;
     
     import com.example.alibabadruid.dao.cluster.ClusterRightsDao;
     import com.example.alibabadruid.entity.cluster.Rights;
     import org.springframework.stereotype.Service;
     
     import javax.annotation.Resource;
     import java.util.List;
     
     @Service
     public class ClusterRightsService {
     
         @Resource
         private ClusterRightsDao clusterRightsDao;
     
         /**
          * 查询所有记录
          *
          * @return 返回集合，没有返回空List
          */
         public List<Rights> listAll() {
         	return clusterRightsDao.listAll();
         }
     
     
         /**
          * 根据主键查询
          *
          * @param rightid 主键
          * @return 返回记录，没有返回null
          */
         public Rights getById(Integer rightid) {
         	return clusterRightsDao.getById(rightid);
         }
     	
         /**
          * 新增，插入所有字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
         public int insert(Rights rights) {
         	return clusterRightsDao.insert(rights);
         }
     	
         /**
          * 新增，忽略null字段
          *
          * @param rights 新增的记录
          * @return 返回影响行数
          */
         public int insertIgnoreNull(Rights rights) {
         	return clusterRightsDao.insertIgnoreNull(rights);
         }
     	
         /**
          * 修改，修改所有字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
         public int update(Rights rights) {
         	return clusterRightsDao.update(rights);
         }
     	
         /**
          * 修改，忽略null字段
          *
          * @param rights 修改的记录
          * @return 返回影响行数
          */
         public int updateIgnoreNull(Rights rights) {
         	return clusterRightsDao.updateIgnoreNull(rights);
         }
     	
         /**
          * 删除记录
          *
          * @param rights 待删除的记录
          * @return 返回影响行数
          */
         public int delete(Rights rights) {
         	return clusterRightsDao.delete(rights);
         }
     	
     }
     ```



