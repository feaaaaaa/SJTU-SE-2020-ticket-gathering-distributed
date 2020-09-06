# 给票网

## 开发者

...崔绍杰

## 简介

OligeiWeb是我们此次项目目标——聚票网的名字，其作用是将来自不同票源的相同门票整合到一起，供用户选取，为用户节约时间，节约金钱。

## 地址

1. 前端地址：
2. skywalking监控地址：

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

1. 画面美观，用到了**balabala！@周洹羽**库改进UI
2. 后端返回的信息以(status,message,data)进行了统一的包装，方便前端处理
3. 对后端可能发生的大部分错误都进行了捕获处理，并且有选择性地反馈给用户



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

本项目采用mysql,mongodb,neo4j三种数据库

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

