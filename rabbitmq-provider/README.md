# Rabbitmq and SpringBoot

1. 安装rabbitmq
2. 启动rabbitmq--rabbitmq/sbin/rabbitmq-server.bat
3. 打开rabbitmq的web页面:http://127.0.0.1:15372
4. 登录rabbitmq的管理网站:用户名:guest 密码:guest
5. 创建新用户，并且配置和guest账号一样的权限和`Virtual Hosts`
6. SpringBoot项目的`application.yml`文件需要配置spring.rabbitmq.virtual-host为:\

**Rabbitmq的三个端口(5672/15672/25672)的作用**

[参考博客](https://blog.csdn.net/qq_35387940/article/details/100514134)