## Unit Testing
链接：http://softwaretestingfundamentals.com/unit-testing/
### what is mocking
链接：https://stackoverflow.com/questions/2665812/what-is-mocking
mocking可以理解为模拟，是为了孤立测试某个单元，使之不受其他单元影响，从而模拟出一种孤立环境
### what is unit testing
unit testing是软件测试的一个最小级别。在这个级别中，只测试孤立的program,funtion,procedure,method,etc。Unit testing frameworks, drivers, stubs, and **mock/ fake objects** 都可以用于unit testing.
### when is it performed
united test是最先开始的测试
### how to do it:white box testing
链接：http://softwaretestingfundamentals.com/white-box-testing/
基于对组件或系统内部结构的分析；选择合适的cases。(这不废话吗)
### Unit Testing Tasks
1. Prepare
2. Review
3. Rework
4. Baseline

### Tips
1. 为你的语言找合适的工具
2. cases不用面面俱到，找真正影响性能的
3. 孤立测试，不要让开发环境影响测试环境
4. 测试数据符合现实
5. 在修复缺陷前，要写实例暴露这些缺陷
6. 测试用例要彼此孤立
7. 测试要覆盖程序的方方面面
8. 用VCS(版本控制系统)
9. 除了测试正确性，还要测试性能
10. 测试要可持续

## Documentation-Driven Development
### what is REST APIs
REpresentational State Transfer Application Programming Interface
1. 就是用URL定位资源，用HTTP描述操作
2. 把网页请求当成资源来看待
3. 看Url就知道要什么
4. 看http method就知道干什么
5. 看http status code就知道结果如何
### what is OpenAPI Specification
链接：https://swagger.io/docs/specification/about/
一种针对REST APIs的API描述标准，可以描述整个API，包括可接触的用户、可执行的操作、输入参数、输出、权限、证书等等。可以用YAML或JSON来写，对人类和机器都便于阅读。
### what is Swagger
以OpenAPI Specification结合为核心的一组开源工具。帮助你设计、建立、编写、阅读REST APIs，主要包括Swagger Editor, Swagger UI, Swagger Codegen
### Basic Structure
链接：https://swagger.io/docs/specification/basic-structure/
(YAML基础语法：https://www.ruanyifeng.com/blog/2016/07/yaml.html)
