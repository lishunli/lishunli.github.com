---
layout: post
title: "Ubuntu下常见服务的安装笔记"
date: 2013-01-13 22:13
comments: true
categories: [Linux, Ubuntu, Tips]
---
前面写过一篇博文[我是这样使用Ubuntu的](http://lishunli.github.com/blog/2012/04/14/ubuntu-personal-use-notes/)，大致介绍了一下如何安装ubuntu和一些使用技巧。最近在学习一些常用的服务，比如nginx，mc等，这里做些笔记。	
<!-- more -->
##开启ssh服务##
使用`sudo apt-get install openssh-server`来安装ssh服务，后面发现在每次使用ssh登录系统的时候，都需要等待比较长的时间，也比较好解决			
``` bash
echo "UseDNS no" >> /etc/ssh/sshd_config
sudo service ssh restart
```	
	
##安装jdk##
参考[Ubuntu 11.04 下安装配置 JDK 7](http://blog.csdn.net/yang_hui1986527/article/details/6677450) 和 [Java安装配置](http://wiki.ubuntu.org.cn/Java%E5%AE%89%E8%A3%85%E9%85%8D%E7%BD%AE)
``` bash ~/.bashrc
vi ~/.bashrc
#在最后加上下面的环境配置语句
export JAVA_HOME=/usr/lib/jvm/jdk1.6.0_38
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```
	
##安装Tomcat##
下载并解压即可，可以修改一些默认的配置，比如8080端口，和Root下默认的manage页面（删除webapps/ROOT/下所有文件，添加index.html）,定制一下404页面（可以支持公益[益播-公益广告](http://yibo.iyiyun.com/)）		
		
重启tomcat服务器是经常要做的一件事情，请新建一个restart.sh，并赋予执行的权限（chmod 744），里面写上下面的shell script，那么每次想重启tomcat的时候，到tomcat/bin目录下，执行./restart.sh就可以了（当然你可以配置tomcat/bin到path下）。
```  bash restart.sh
#!/bin/sh

ps -ef|grep tomcat |awk '{print $2}' |xargs kill -9
./startup.sh
tail ../logs/catalina.out -f
```
	
##安装Memcached##
Memcached 的安装可以自己编译或者直接安装，请参考[Ubuntu下安装Memcached](http://www.mike.org.cn/articles/ubuntu-install-memcached/) 这篇文章，写的很详细，出现的问题也给出了解决方案。		
这里就仅贴出启动和关闭Memcached的shell
``` bash startup.sh
#!/bin/bash

ulimit -SHn 65000
ulimit -l unlimited

/usr/local/memcached/bin/memcached  -d -p 11211 -m 1024 -u root -P /tmp/memcached.pid
```
``` bash shutdown.sh
#!/bin/bash
kill `cat /tmp/memcached.pid`

ps -ef |grep memcached|awk '{print $2}'|xargs -l -t kill 
```
``` bash memcached(开机自启动)
ln -s  /usr/local/memcached/bin/startup.sh /etc/init.d/memcached
update-rc.d memcached defaults
```

##安装nginx##
编译安装最新稳定版nginx请看[Ubuntu 11.10 x64编译安装nginx、PHP 5.3.8、mysql、mongodb、memcached、ssl、smtp](http://www.cnblogs.com/sink_cup/archive/2011/06/29/ubuntu_nginx_php_mongodb_memcache_mysql_ssl_gmail_smtp.html),写的很详细，其中nginx开机自启动的文章 [Ubuntu Nginx 开机自启动](http://www.cnblogs.com/lexus/archive/2010/12/21/1913109.html) 也很不错		
我的启动和关闭shell
``` bash restartup.sh(也可以做启动shell)
#!/bin/sh

PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

ulimit -HSn 65535
ulimit -a

cd `dirname $0`
base_dir=`pwd`

is_force_restart=0
if [ $# -gt 0 -a "x$1" = "xrestart" ]; then
    is_force_restart=1
fi

ports="80"

for p in ${ports}
do
    if [ ${is_force_restart} -eq 1 ]; then
        echo "kill process for port ${p}..."
        fuser -s -n tcp -k -9 ${p}
    fi
done

for one_port in ${ports}
do
    nc -z localhost ${one_port}
    if [ $? -ne 0 ]; then
        echo "start process for port ${one_port}..."
        /usr/local/nginx/sbin/nginx
    else
        echo "process for port ${one_port} is running!"
    fi
done
```
``` bash shutdown.sh(很暴力)
#!/bin/bash

nginx_pid="/usr/local/nginx/logs/nginx.pid"

if [ -f $nginx_pid ]; then
        kill `cat $nginx_pid`
fi

ps -ef |grep nginx|awk '{print $2}'|xargs -l -t kill
```
``` bash nginx(开机自启动)
ln -s  /usr/local/nginx/sbin/restart.sh /etc/init.d/nginx
update-rc.d nginx defaults
```
		
##安装Mysql##
使用`apt-get install mysql-server`来安装mysql服务端，同时终端会请求输入root用户密码，这里本人设置的密码是`lishunli`。	
		
如果要远程登录，请先使用`mysql -uroot -plishunli`进入mysql控制台，再使用`grant all privileges on *.* to root@"%" identified by "lishunli";`分配所有表远程连接的权限给root用户，最后退出mysql控制台，编辑`/etc/mysql/my.cnf`，在该文件中找到 bind-address = 127.0.0.1 的位置，将其更改为 bind-address=你本机的网络IP，比如我这就是`bind-address = 192.168.1.105`	。参考[MySql重启命令与数据库安装目录](http://blogread.cn/it/article/521?f=wb) 和 [MySQL安装指南](http://wiki.ubuntu.org.cn/MySQL%E5%AE%89%E8%A3%85%E6%8C%87%E5%8D%97)		
		
后来发现远程连接mysql很慢，网上给出的解决办法是在`/etc/mysql/my.cnf`配置文件中的`[mysqld]`后面加上`skip-name-resolve`，再`/etc/init.d/mysql restart`重启mysql即可。引自[MYSQL远程连接速度慢的解决方法](http://www.helloox.com/760.html)		
		
Mysql默认的编码格式是latin格式，当然修改成utf8更加方便处理中文
``` bash
vi /etc/mysql/my.cnf
#在[mysqld]下面加入一行
character_set_server = utf8
#在[mysql]下面加入一行
default-character-set = utf8
/etc/init.d/mysql restart
```
		
		

**注:
上面的大部分命令都是以root用户执行的，如果权限不够，请加上sudo**	
	
如果有什么建议或问题的话，可以通过微博 [@李顺利Me](http://weibo.com/lishunli) 或 Email：<leeshunli@qq.com> 联系到我，大家一起交流学习。		
	
<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2013年1月13日
</p>

### 更新历史	
2013-01-13 添加mysql服务，并修改一些默认配置		
2013-01-12 解决ssh登录等待时间长的问题，添加nginx启动和关闭shell		
2013-01-08 继续更新使用中遇到的问题并安装一些服务软件				
2013-01-06 添加开启ssh服务等内容			