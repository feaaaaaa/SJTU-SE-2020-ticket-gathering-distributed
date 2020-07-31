# AWS基本介绍
亚马逊网络服务，云计算服务平台，为用户提供计算和储存资源。其中包括一系列的产品，具体分为三类：

+ 存储类 S3
+ 服务器资源类（计算）EC2
+ 数据库类 

# EC2
一种计算服务，相当于云端给你分配的一部分计算空间，这块空间作为一个EC2实例，可以被认为是一个虚拟机

EC2侧重点有所不同，有的可以侧重cpu，做一些计算量比较高的东西；也可以是重存储的

ps：6号说的用端口和IP访问好像是要IP是公开的那种，我发现这种方法好像不行

#### 前面还比较好理解，后面就比较难理解了，主要涉及到EC2的配置问题（配置环境，永远的痛）

前面说EC2像虚拟机，因为他就是个什么都没装的linux，基本什么东西都得自己装，还可以用AMI（EC2镜像）提前对这个虚拟机的操作系统进行配置和应用的打包

其次，由于不是把软件装在本地，所以要远程登陆，使用SSH进行远程登陆，再利用命令行进行下载

后面有几个名词，在配置的时候稍微注意一下：
安全组 类似防火墙，对数据包的端口、协议、IP进行检测
弹性IP 为EC2分配一个公网IP地址

# 配置问题
首先最基本的AMI和CE2的配置，这个我看的真的一头雾水，很多都是自己也说不清楚，直接跟着教程来，等审核过了去踩坑（配置教程也千奇百怪的）

因为什么都得自己装，网上安装的东西简直眼花缭乱，考虑到我们应该是主要用react和spring
这里先记一下可能要装的东西：

1、js运行环境：node.js  
https://www.jianshu.com/p/d68d2ee7343b

2、react 网上没怎么查到EC2有react，好像有个类似react的东西

3、tomcat  
https://blog.csdn.net/cencfeng11/article/details/79213538

4、springboot  
https://blog.csdn.net/dnc8371/article/details/106720985

#### 具体配置感觉还是得等审核过了，所以现在还停留在理论状态（理论部分我已经看晕了），实际操作应该会好点

ps：这玩意自己写的，网上很多都不说人话，我用个人翻译过来的

nodejs

+  SSH
+  Pem 文件（来自于AWS EC2）实例

# AWS具体配置

网站：https://aws.amazon.com/cn/education/awseducate/  
登录  
用户：mark2333@sjtu.edu.cn  
密码：oligei!2020

AWS ACCOUNT --> AWS educate starter account -->AWS console  
这个页面就是AWS的控制台了，用云服务官网登录好像也行，之后点管理控制台同样可以跳转过来

这个控制台就是AWS提供的资源，选择启动虚拟机EC2，会跳转到ami的配置，
具体需要配置安全组：需要的端口，22的SSH，自定义的3000端口，80的http端口（这次还没用到）

如果有配置完AMI可以直接在实例里找到并连接，由于我已经配置完了，所以可以在实例找到一个oligei的ubuntu系统的配置，连接即可，接下来是连接教程：

总体而言，是用ssh远程登录虚拟机，这里只说windows怎么配（mac好像自带ssh）：  
下载putty，官方有相关教程，一步步做即可  
链接：https://docs.aws.amazon.com/zh_cn/AWSEC2/latest/UserGuide/putty.html?icmpid=docs_ec2_console  

下面说几个细节（我踩的坑）:  
![putty](https://docs.aws.amazon.com/zh_cn/AWSEC2/latest/UserGuide/images/putty-session-config.png)  

+ host name在ec2实例页面会给出，复制即可
+ 这里配置完连接的参数可以save，下次直接load即可  
+ 在connection里面可以设置登录的用户，连接后会直接用这一用户名登录
+ open后出现命令行，也就是ssh远程登录成功，login的用户名不同的系统不一样（不是自己定的），ubuntu是ubuntu，后面要求输入的passphrase是自己定义的（oligei），这样就成功登陆了
+ 之后就是一般的命令行操作了，sudo安装环境即可
+ npm run跑工程
