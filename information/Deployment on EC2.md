# 在AWS EC2上部署前端和后端
### 前提
以下所有的操作均在EC2下进行，以centOS系统为例。
### Docker
一个开源的应用容器引擎，支持让开发者打包应用以及依赖到一个轻量级、可移植的容器中。
#### 镜像和容器
镜像和容器的关系，像是类和实例的关系。在docker上可以pull到大量优秀的镜像，从系统到数据库再到语言环境，使用者可以方便的由镜像创建容器，直接使用。
** 注意：加上 -itd 容器挂在后台一直运行。
### Docker for Mysql
https://www.runoob.com/docker/docker-install-mysql.html
### Docker for MongoDB
https://www.runoob.com/docker/docker-install-mongodb.html
### 使用Docker部署springboot后端
有关代码参见https://www.jb51.net/article/178001.htm
#### 从0开始
1. 在pom.xml的properties中添加
2. 在pom.xml的plugins中添加插件 
3. 在src/main/docker下创建Dockerfile文件，说明如何构建镜像 
(注：四行，开头分别是FROM, VOLUME, ADD, ENTRYPOINT。jar包的文件名注意改成自己target文件夹下的文件名。)
4. 安装docker
5. 安装java和maven环境
#### 使用Docker部署后端
6. 将项目拷贝到centOS上(git)，进入项目路径
7. 改动application.properties设置数据库连接
8. 删除target文件夹下的两个jar包(如果有的话)
9. mvn package docker:build 构建镜像，当看到BUILD SUCCESS代表构建成功
10. docker images 查看拥有的所有镜像
11. docker run -itd -p 8080:8080 -t springboot/spring-boot-docker(改成后端自己的镜像名称)
12. docker ps 查看正在运行的容器
13. 访问 ip:8080
14. docker stop CONTAINER_ID 关闭后端
15. docker restart CONTAINER_ID 重启已经关闭的后端
### 使用Docker部署React前端
有关代码部分请参考https://www.ucloud.cn/yun/39613.html
#### 从0开始
1. 安装npm,node等前端需要的环境，安装docker,docker-compose（便于管理容器）
2. docker pull nginx:latest 下载nginx镜像，nginx是一种服务器。
#### 使用Docker部署前端
3. 在前端项目下建立docker-compose.yml（参考上述网页）
4. npm run build 将前端项目打包
5. docker-compose up -d 自动创建容器并在后台运行
6. docker-compose down 关闭前端服务器
