# Fitting Room backend #

组件：

- JDK 11
- Tomcat 9
- Redis 6
- MariaDB 10
- Nginx 1.18

## 运行 ##

- 修改 src/main/resources/application.properties 项目配置文件
- 修改 src/main/resources/log4j2.xml 日志配置文件
- 执行 script/mariadb/create.sql
- 执行 script/redis/create.sql
- 执行 script/shell/create.sql
- IDE 启动项目

## 打包 ##

- 修改 src/main/resources/application.properties 项目配置文件
- 修改 src/main/resources/log4j2.xml 日志配置文件
- 执行 mvn package

## 部署 ##

主机版本 Ubuntu 20.4

- `apt update && apt upgrade`

### Redis ###

官方源中的是 5.x 版本，ACL 最低支持版本 6.0，所以采用了 PPA 安装

- `apt install software-properties-common`
- `add-apt-repository ppa:redislabs/redis`
- `apt-get update`
- `apt-get install redis`
- `vim /etc/redis/redis.conf`
```
...
# 反注释
aclfile /etc/redis/users.acl
...
```
- `touch /etc/redis/users.acl && chown redis:redis /etc/redis/users.acl`
- `systemctl restart redis-server && systemctl status redis-server`

### MariaDB ###

- `apt install mariadb-server`
- `mysql_secure_installation`

### JDK ###

- `apt install openjdk-11-jdk`

### Tomcat ###

- `apt install tomcat9 rng-tools5`（rng-tools5 是生成熵的，防止重启 tomcat 后随机数产生不足造成阻塞）

由于 Ubuntu apt 源安装的 Tomcat 9 被限制在沙盒中运行，而系统内部写入的静态资源需要提出来，方便日后其他组件处理
所以对于程序中需要访问的文件夹要开启相应权限

- `export SYSTEMD_EDITOR="vim" && systemctl edit tomcat9`
```
# 添加下行
[Service]
ReadWritePaths=/fr
```
- `systemctl daemon-reload && systemctl restart tomcat9 && systemctl status tomcat9`

### Nginx ###

- `apt install nginx`
- `gpasswd -a www-data tomcat`
- `vim /etc/nginx/sites-enabled/default`
```
## 修改为
server {
    listen 80 default_server;
    listen [::]:80 default_server
    root /fr/image/;
    server_name _;
    location / {
    }
}
```
- `systemctl restart nginx`

## Web App ###

- `rm /var/lib/tomcat9/webapps/fr.war`
- `mkdir fr`
- 本地执行 `scp -P 17027 -i ~/.ssh/aliyun_hd1.pem target/fitting-room-1.0-SNAPSHOT.war root@116.62.149.236:/root/fr/`
- 本地执行 `scp -P 17027 -i ~/.ssh/aliyun_hd1.pem -r script root@116.62.149.236:/root/fr/`
- `cd fr/script`
- `mariadb -uroot -p < mariadb/drop.sql && mariadb -uroot -p < mariadb/create.sql`
- `bash redis/drop.sh && bash redis/create.sh`
- `bash shell/create.sh /fr`
- `cd ..`
- `cp fitting-room-1.0-SNAPSHOT.war /var/lib/tomcat9/webapps/fr.war`

