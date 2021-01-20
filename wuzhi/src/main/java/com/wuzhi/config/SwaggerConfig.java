package com.wuzhi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2//测试运行页面:http://localhost:8181/swagger-ui.html
public class SwaggerConfig {
    //配置swagger2的Bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("A")//多人协作开发可能会搞出多个部分，但是我们只要一个就可以了
                .apiInfo(apiInfo())
                .enable(true)//是否启动，在项目验收的时候应该要关闭，不要给阿猫阿狗进去,一般是flag来判断是不是在生产环境，但是这次就偷懒不搞了
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wuzhi.controlled"))//意思是只扫描这个包
//                .apis(RequestHandlerSelectors.withClassAnnotation(RequestMapping.class))
                .build();

    }
    private ApiInfo apiInfo(){
        Contact contact = new Contact("五只", "", "2645173675@qq.con");

        return  new ApiInfo(
                "接口文档",
                "0.1版本，现在还是测试用的接口",
                "0.1",
                "http://localhost:8181/wuzhi",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
