# 给票网

## 开发者

...崔绍杰

## 简介

OligeiWeb是我们此次项目目标——聚票网的名字，其作用是将来自不同票源的相同门票整合到一起，供用户选取，为用户节约时间，节约金钱。

## 地址
1. 公网ip：202.120.40.8
2. 端口：jenkins 30331， eureka 30332， gateway 30333， frontend 30334

## 如何获得源码

我们将源码保存在：https://github.com/o-ligei/SJTU-SE-2020-ticket-gathering-distributed

可通过以下命令获得最新源码：

`$ git clone https://github.com/o-ligei/SJTU-SE-2020-ticket-gathering-distributed.git`

## 功能需求

1. 可以用手机号和邮箱进行注册，以注册好的用户为单位进行购票、充值、竞拍等服务
2. 用户分为普通用户和管理员，正常注册的用户是普通用户，管理员通过直接修改数据库得到
3. 用户可以通过输入目标门票的名字、地点、表演者等信息进行检索，从而找到自己想要的门票
4. 网站可以根据用户的浏览信息进行推荐，推荐内容显示在侧边栏中
5. 门票还按活动类型和地点进行分类，可以有效筛选门票
6. 用户可以对管理员发布的套票（即固定活动、固定票数）进行竞价，截止日期到之后价高者得
7. 用户可以进行充值，以购票或竞价
8. 用户可以查看已有订单
9. 管理员可以删除、增加门票，发布订单

## 非功能需求

### 安全性

- 对后端的请求（除注册、登录外）均通过JWT进行认证（Authentication）和授权（Authorization），对用户“是否能请求资源”和“能请求什么样的资源”都进行了限制
- 数据库内用户的密码均进行加密处理，**balabala@刘遇时**

### 性能需求

- 后端采用springcloud架构，将服务器压力分摊开，降低响应时间
- 后端暴露的所有接口均进行性能检测，实现了在100并发下，相应时间低于两秒的要求，大部分接口的响应时间都远远低于2s，详见此处

### 可靠性

- 后端返回的信息以(status,message,data)格式进行了统一的包装，方便错误处理

- 后端所有函数对绝大部分的可预见异常进行了捕捉和处理，避免异常引起的程序错误

### 易用性

- 对所有的函数进行了注释（包括功能、参数、返回值、异常、开发者、开发时间），并生成了开发文档，详见此处
- 画面美观，用到了**balabala！@周洹羽**库改进UI
- 对后端可能发生的大部分错误都进行了捕获处理，并且有选择性地反馈给用户

### 可维护性与可扩展性

- 前端的主要组件都进行了模块化，便于复用及修改
- 后端采用分层架构，并按需写就了util和dto层

## 技术栈

1. 前端：react + antd ui
2. 后端：采用springcloud微服务，使用了包括eureka,feign等工具
3. 数据库：mysql+mongodb+neo4j
4. 自动化部署：jenkins+docker
5. 日志：log4j
6. 搜索分词工具：lucene
7. 单元测试：junit+mock
8. 性能测试：jmeter+prometheus+grafana
9. 远程服务监控：skywalking

## 前端总体设计

主要使用了ant design框架，主要工作目录src的结构如下

- view  存放呈现给用户的各个视图，比如LoginView.js是登录界面
- component 存放各个抽象的组件，不同组件相互结合构成了视图，比如HeaderInfo.js（页头）、HomeCategaoryPage.js(分类展示栏)、Navigate.js(推荐栏)等组件共同构成了HomeView.js(主界面)
- css 存放视图或组件的CSS文件
- const 存放常量数据，比如icon.js存放分类栏中的图标
- resources 存放图片，比如跑马灯中的图片carousel.png、carousel2.png
- service 存放向后端请求数据的函数，比如userService.js存放了前端调用后端userController层接口的函数
- utils 存放工具函数，比如service中的函数统一调用ajax.js中对fetch API进行封装的函数

## 后端

后端总体采用springboot+springcloud的组合，所以分为四个工程。在直接与数据库交互的工程中采用了分层架构和对接口编程。

- eureka  **@周洹羽 解释一下**
- gateway 接口工程，用以方便前端的调用，将前端的请求转发给工作工程
- feign 处理与竞价有关操作，分为entity,dao,service,controller,dto(serice层对信息再组合)，
- ticket-gathering 处理除竞价以外的操作，分层架构与feign类似

## 数据库设计

本项目采用mysql,mongodb,neo4j,redis

###  Mysql+MongoDB

结构性数据运用mysql储存，mongodb作为其补充用来储存长度过长的数据

  tg_users(userid,username,gender,email,phone,password,type,usericon(MongoDB),balance)

  tg_activities(activityid,title,actor,timescale,venue,imgurl,description(MongoDB))

  tg_actitems(actitemid,activityid,website,prices(MongoDB))

    prices: { timecnt: 3, tickets: [ {time:’2020-07-08’, classcnt: 3, class: [{price: 300, num: 100} , {} , {} ] } , {} , {} ] }

  tg_orders(orderid,userid,actitemid,price,amount,showtime,ordertime)

  tg_auctions(auctionid,actitemid,userid,isover,initprice,orderprice,ddl,showtime,ordertime,amount)

### Neo4j for recommendation and classification

  Tripartite graph：User,Activity,Property

  Property: city,category(subcategory),actor

  (Activity,Property) for classification and similarity recommendation: easy to achieve in neo4j

  (User,Activity) for personalized recommendation: Personal Ranks Algorithm 

    https://blog.csdn.net/qq_40006058/article/details/83444131 for more

### Redis

利用redis访问响应时间短，速度快的优势，将搜索关键词和得到的结果作为键值对放入redis中，来作为缓存使用，加快搜索响应速度。

## 服务器

使用软院提供的三台服务器，其中一台可以连公网(A)，两台限制在内网(B,C)。配置均为4核8G。

### A: jenkins + frontend + eureka + gateway

### B: ticketgathering + mysql + mongodb

### C: auction + neo4j + redis

## 自动化部署

### docker

我们的所有后端服务，数据库及大部分工具都采用docker容器进行部署，最大限度地减少手动启动服务的工作量，便于管理。

### jenkins

后端的所有服务采用jenkins进行CI/CD，免去了命令行启动服务的繁琐操作。其中，我们将jenkins设在gateway服务器上，其能够连接远端服务器，对所有服务器上的所有服务进行自动化部署。

## 测试

### 前端测试

### 后端单元测试

1. 使用 Junit对各层的公有函数进行单元测试，覆盖率100%
2. 所有测试均进行mock处理，并同时测试了大多数错误情况
3. 测试文档地址：

### 性能测试

## 远程监控
