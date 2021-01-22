package com.example.swagger2.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
//Profile是方法1（只在dev和test环境下开启）
@Profile({"dev","test"})
public class SwaggerConfig{
    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
    //这个是方法2哦，使用的话在new Docket里添加.Enable方法将参数放入即可
    @Value(value = "${swagger.show}")
    private Boolean swaggerEnabled;

    /**
    * apiInfo() 增加API相关信息
    * 所有的注解
    * .apis(RequestHandlerSelectors.any()) 
    * 指定部分注解1.Api.class(@APi),2.ApiOperation.class(@ApiOperation),3.ApiImplicitParam.class(@ApiImplicitParam)
    *.apis(RequestHandlerSelectors.withMethodAnnotation(Api.class))
    * 指定包路径
    * .apis(RequestHandlerSelectors.basePackage("这里填写需要的路径"))
    * .paths() 这个是包路径下的路径,PathSelectors.any()是包下所有路径
    */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()// 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                .paths(PathSelectors.any())// 对所有路径进行监控
                //创建
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口口文档档")
                .contact(new Contact("romeo", "", "xxx@qq.com"))
                .description("这是SWAGGER_2生成的接口文档")
                .termsOfServiceUrl("NO terms of service")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("v1.0")
                .build();
    }

}