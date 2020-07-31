### swagger简介  
swagger给前后端提供沟通桥梁的接口说明文件，由后端给出，提供包括url、传递参数、描述接口功能等接口信息，也可以直接对接口进行测试

### 配置安装  
springboot可以十分方便的整合swagger，具体操作如下：
1. pom.xml添加依赖：  
```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```
不要忘记maven的import提示
        
2. 创建配置的java类（如swaggerConfig）
3. swaggerConfig代码：
```
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.reins.bookstore.controller")) 
                //注意这里要改成自己controller的所在位置
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("聚票网后端接口")
                        .description("聚票网后端接口如下：")
                        .version("9.0")
                        .license("The Apache License")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }
}
```

4. 启动类添加@EnableSwagger2  
启动类是xxxApplication的java文件一般在java文件夹的根目录上，其上有@SpringBootApplication

至此配置完成

###自定义用法
+ @Api(tags="用户",description = "Operations about user")   
在contoller类前，标注controller整个类的功能  
+ @ApiOperation(value="获取用户信息",tags={"获取用户信息copy"},notes="注意问题点")  
在方法前
+ @ApiParam(name="用户对象",value="传入json格式",required=true)  
在@RequestParam("userId")和参数之间，描述参数



