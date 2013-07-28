---
layout: post
title: "Ubuntu下常见服务的安装笔记"
date: 2013-01-13 22:13
comments: true
categories: [Linux, Ubuntu, Tips]
---
前面写过一篇博文[我是这样使用Ubuntu的](http://www.blogjava.net/lishunli/archive/2012/04/26/376655.html)，大致介绍了一下如何安装ubuntu和一些使用技巧。最近在学习一些常用的服务，比如nginx，mc等，这里做些笔记。	
<!-- more -->
###开启ssh服务###
使用`sudo apt-get install openssh-server`来安装ssh服务，后面发现在每次使用ssh登录系统的时候，都需要等待比较长的时间，也比较好解决			
``` bash
echo "UseDNS no" >> /etc/ssh/sshd_config
sudo service ssh restart
```	
ps. ssh服务最好需要固定的ip，如果做，请参考[ubuntu 12.04 以固定 IP 地址连接网络并配置DNS](http://blog.csdn.net/tzb251316192/article/details/7520210)		
	
###安装jdk###
参考[Ubuntu 11.04 下安装配置 JDK 7](http://blog.csdn.net/yang_hui1986527/article/details/6677450) 和 [Java安装配置](http://wiki.ubuntu.org.cn/Java%E5%AE%89%E8%A3%85%E9%85%8D%E7%BD%AE)
``` bash ~/.bashrc
vi ~/.bashrc
#在最后加上下面的环境配置语句
export JAVA_HOME=/usr/lib/jvm/jdk1.6.0_38
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```
	
###安装Tomcat###
下载并解压即可，可以修改一些默认的配置，比如8080端口，和Root下默认的manage页面（删除webapps/ROOT/下所有文件，添加index.html）,定制一下404页面（可以支持公益[益播-公益广告](http://yibo.iyiyun.com/)）		
		
重启tomcat服务器是经常要做的一件事情，请新建一个restart.sh，并赋予执行的权限（chmod 744），里面写上下面的shell script，那么每次想重启tomcat的时候，到tomcat/bin目录下，执行./restart.sh就可以了（当然你可以配置tomcat/bin到path下）。
```  bash restart.sh
#!/bin/sh

ps -ef|grep tomcat |awk '{print $2}' |xargs kill -9
./startup.sh
tail ../logs/catalina.out -f
```
	
###安装Memcached###
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

###安装nginx###
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
		
###安装Mysql###
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
		
###安装redis###
安装很简单，参考官网上面的即可
``` bash
wget http://redis.googlecode.com/files/redis-2.6.10.tar.gz
tar xzf redis-2.6.10.tar.gz
cd redis-2.6.10
make
```
make之前先用 `make test` 进行测试下，发现会有`You need Tcl 8.5 installed to run the Redis test...` 这样的错误，意思是redis需要Tcl 8.5+，目前本ubuntu机器没有，那就安装了，`apt-get install tcl8.5` 即可解决make的tcl依赖问题		
	
安装后可以进行一些简单的测试，redis-2.6.10/src 里面有 redis-server（服务端，默认不是静默开启，请不要关闭） 和 redis-cli（客户端），就可以进行一些简单的操作，具体的命令请参考官网[Command reference – Redis](http://redis.io/commands) 或者 @huangz1990 共享的redis命令中文翻译页面 [Redis 命令参考](https://redis.readthedocs.org/en/latest/)。	
	
redis安装好后，可以配置开机启动，官网安装文档中也给出了ubuntu如何配置redis开机启动，在 [Redis Quick Start](http://redis.io/topics/quickstart)的 Installing Redis more properly 部分，整理出步骤
``` bash
# 确保你已经复制 redis-server 和 redis-cli 脚本到 /usr/local/bin下，编译的时候 make && make install 即可
# 创建一写文件夹来放置redis的配置文件和数据
sudo mkdir /etc/redis
sudo mkdir /var/redis
# 复制init脚步，在redis发行包的utils下已经有这个脚本，e.g. /usr/local/redis-2.6.10/utils/redis_init_script
sudo cp utils/redis_init_script /etc/init.d/redis_6379
# 可以根据需要修改init脚步，默认最好，如果想修改默认的端口号可以根据需要修改
sudo vi /etc/init.d/redis_6379
# 创建数据文件夹
sudo mkdir /var/redis/6379
# 复制默认的配置文件
sudo cp redis.conf /etc/redis/6379.conf
# 修改默认的配置文件
sudo vi /etc/redis/6379.conf
	# 设置静默安装
		daemonize yes
	# 设置pid文件
		pidfile /var/run/redis_6379.pid
	# 设置日志文件（可以根据需要修改日志级别）
		logfile /var/log/redis_6379.log
	# 设置主目录（很重要）
		dir /var/redis/6379
# 配置开机启动
sudo update-rc.d redis_6379 defaults
# 启动服务
/etc/init.d/redis_6379 start
```
好了，如果没有问题至此redis_6379已经安装成功并能开机启动。	
	
	
###安装Git###
到[git-core](https://code.google.com/p/git-core/downloads/list)下载最新的源码，并解压编译安装
``` bash
wget https://git-core.googlecode.com/files/git-1.8.3.4.tar.gz
tar zvxf git-1.8.3.4.tar.gz
cd git-1.8.3.4
./configure
make && make install
git --version
```	
		
###安装zsh###
安装[zsh](http://www.zsh.org/) shell并通过[oh-my-zsh](https://github.com/robbyrussell/oh-my-zsh/)配置
``` bash
apt-get install zsh

wget https://github.com/robbyrussell/oh-my-zsh/raw/master/tools/install.sh -O - | sh

# 个人的一些插件安装
# autojump
git clone git://github.com/joelthelion/autojump.git
cd autojump
./install.sh

```	
`source ~/.zshrc` 或者重开Terminal即可享受zsh的快捷。	

更多参考via		
[终极 Shell | MacTalk-池建强的随想录](http://macshuo.com/?p=676)	
[我在用的mac软件(2)-终端环境之zsh和z(*nix都适用) - Code Rush](http://foocoder.com/blog/wo-zai-yong-de-macruan-jian-2.html/)		
[使用 Zsh 的九个理由](http://lostjs.com/2012/09/27/zsh/) ps. 原文有些图片已经404了，大家google下可以看到很多copy

个人的zsh配置
``` bash ~/.zshrc
# autojump
[[ -s /etc/profile.d/autojump.zsh ]] && . /etc/profile.d/autojump.zsh

export LC_ALL=en_US.UTF-8  
export LANG=en_US.UTF-8

# alias
alias vi='vim'
alias grep='grep --color=auto'

```			
		
		
**注:
上面的大部分命令都是以root用户执行的，如果权限不够，请加上sudo**	
	
如果有什么建议或问题的话，可以通过微博 [@李顺利Me](http://weibo.com/lishunli) 或 Email：<leeshunli@qq.com> 联系到我，大家一起交流学习。		
	
<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2013年1月13日
</p>

#### 更新历史
2013-07-28 添加安装zsh shell	
2013-07-23 添加安装git服务	
2013-02-25 添加安装redis服务，并配置开机启动	
2013-01-13 添加mysql服务，并修改一些默认配置		
2013-01-12 解决ssh登录等待时间长的问题，添加nginx启动和关闭shell		
2013-01-08 继续更新使用中遇到的问题并安装一些服务软件				
2013-01-06 添加开启ssh服务等内容			