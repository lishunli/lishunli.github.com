---
layout: post
title: "我是这样使用Ubuntu的"
date: 2012-04-14 15:54
comments: true
categories: [Linux, Ubuntu, Tips]
---

最近读了[《鸟哥的Linux私房菜》](http://vbird.dic.ksu.edu.tw/)([豆瓣](http://book.douban.com/subject/2208530/))，并认真学习了如何使用Linux，这里做个小笔记。		
注：以下内容网上很多，而这里是我自己的个人使用笔记，供以后查找和使用，也希望能对一些初步使用者有点帮助。
<!-- more -->
### Windows7下安装Ubuntu 10.04 LTS 
1).	删除卷		
格式化一个盘作为Ubuntu安装分区（如何删除卷，计算机管理 -> 磁盘管理，选择需要格式的盘，右键删除卷）		
		
2).	安装EasyBCD			
安装Ubuntu这部分请参考 [Dual-boot Windows 7 and Ubuntu 12.04 on a PC with UEFI hardware](http://www.linuxbsdos.com/2012/10/11/dual-boot-windows-7-and-ubuntu-12-04-on-a-pc-with-uefi-hardware/)，[ubuntu 11.04在win7下安装无损硬盘安装双系统的图文教程](http://hi.baidu.com/moonlight_0/item/c7c424b8f169cdfc62388eb4)
这篇文章中写的也很详细，图文并茂，而且我试过，都没有问题。说说安装过程中可能需要注意的几个地方：		
a)	*sudo umount -l /isodevice*			
这个不清楚，网上都要做，我也做了，我觉得现在这个版本（10.04LTS）应该解决了这个问题，以后有机会可以试试不加这个会如何。		
b)	*分区*			
我选择的是手动分区， 整个区大概有18G，swap 分区1G（我内存2G），/ 分区为 8G，剩下的都给了 /home 分区（也可以自动分区）。		
c) *部分参考配置*
```
title Install Ubuntu
root (hd0,0)
kernel (hd0,0)/vmlinuz boot=casper iso-scan/filename=/ubuntu-11.04-desktop-i386.iso ro quiet splash locale=zh_CN.UTF-8
initrd (hd0,0)/initrd.lz
```
		
### Ubuntu的使用
1).	修改启动项		
a)	设置Windows7为首启动项	
``` bash
sudo mv /etc/grub.d/30_os-prober /etc/grub.d/06_os-prober
```
b)	更新启动项
```
sudo update-grub
```
2).	修正grub等待时间		
参考这个 [Ubuntu 9.10 修改Grub启动项等待时间](http://lookluk.blogbus.com/logs/56313760.html)
```
sudo gedit /etc/default/grub
sudo update-grub
```
修改 GRUB_TIMEOUT=10，默认10s，修改为自己想要的吧（e.g. GRUB_TIMEOUT=3）		
			
3).	修改Num Lock（小键盘）自动启动开启（亮）	
参考这里 [Ubuntu Linux下开机自动打开数字键的方法（Numlock）](http://www.yucoat.com/linux/ubuntu_enable_numlock_in_boot.html)
```
sudo gedit /etc/rc.local
```
在"exit 0"前面加上
```
for tty in /dev/tty[1-9]*;do
    setleds -D +num < $tty
done
```
4).	更新源		
查看这里 [Ubuntu 10.04更新源大全](http://forum.ubuntu.org.cn/viewtopic.php?t=268843) 		
很清楚，很简单，为了更快地下载速度，我选择网易		
这个步骤可能在上面安装的时候也做一下比较好，因为安装的时候，下载比较慢（比如语言包）。待测试，下次再安装的时候，我来试试。
		
5).	查看配置				
这个是为了以后买预装Linux系统的电脑准备的。记住一些常见的就可以了。来源 [Linux终端模式下查看电脑硬件配置情况](http://www.pcdog.com/edu/linux/19/03/y320447.html)
```
lsb_release -a #查看系统版本
uname -a #查看系统信息
cat /proc/cpuinfo #查看cpu信息
lspci #查看主板信息
free #查内存
df -h #查硬盘
```
{% img /images/ubuntu-personal-use-notes/config.png %}		
		
6).	安装Chrome		
简单，下载chrome（deb包），一般默认就会使用“软件管理器”打开，即可安装，或者使用sudo dpkg -i xxx.deb 命令。		
安装好了，当然要卸载默认的浏览器了——Firefox。个人选择chrome，是觉得比较轻便，随着现在的更加稳定（比如同步收藏夹什么的），现在已经变得很方便了，在不同的机器里用同一个账号几乎不会感觉到有什么差别的。		

<del>
7).	安装wine				
a)	安装		
安装应该很简单，关键是一些配置，比如关于中文字体方面的可能麻烦的，可以参考 [Ubuntu Wine Wiki](http://wiki.ubuntu.org.cn/Wine) 和 [Wine完全使用指南——从基本到高级](http://forum.ubuntu.org.cn/viewtopic.php?t=72933)			
b)	中文乱码		
还是没搞定，本想通过wine来安装一个Evernote和QQ的，不过还是有很多问题，现在也只能通过浏览器，以后很必须的时候再想办法了，e.g.安装个虚拟机。
</del>
		
8).	解决切换到Windows7系统后，时间错误的问题				
我从Ubuntu切换到Windows7后，发现时间是不正确的，修改方法参考 [解决Winddows和Ubuntu的时间差](http://quanyu.blog.163.com/blog/static/12374147220109244824774/)
```
sudo gedit /etc/default/rcS
#找到这一行：UTC=yes，把 yes改为no
```
9).	修改hosts			
这个你懂得是干什么的，推荐一个比较常更新的hosts：<http://code.google.com/p/smarthosts/> ，通过这里你可以下载 hosts，推荐使用 chrome（上面已介绍），强制使用 https，比如 google，YouTube。
如果修改hosts，请查看 [Ubuntu 修改hosts](http://l.14551.org/2009/12/2166)		
```
sudo gedit /etc/hosts  #修改hosts
#可以根据自己的需要添加合适的hosts记录
sudo /etc/init.d/networking restart #保存后重启网络		
```

10).	查看某个文件的完整路径		
这个就是一个简单的shell编程，unix-like系统只有pwd显示当前目录的命令，而没有显示一个文件的完整路径的命令，实际上，有这样的需求也是小部分，很少机会会需要使用到，但是我碰到了，想办法解决，后面在同事的帮助下就写了一个shell脚本，就很容易办到了，关于linux shell，推荐看看 [Linux Shell编程入门](http://www.cnblogs.com/suyang/archive/2008/05/18/1201990.html) 。		
```
touch pwf 
vi pwf #进入编辑模式，粘贴下面的代码后保存

#pwf start
#!/bin/bash

echo `pwd`/$1
#pwf end

chmod 744 pwf #使其有执行权限
# 如果想像pwd那样到处可以执行这个命令（pwf）的话，那么请把pwf所在的文件夹配置到path下。
# e.g. ./pwf test.txt ==> /home/bin/test.txt
```

11).	 如果想删除Ubuntu系统，那么如何做了		
比较简单，参考这篇文章就可以搞定了，[MBR Fix - Fix MBR problems](http://www.sysint.no/nedlasting/mbrfix.htm)，[win7，ubuntu双系统删除ubuntu](http://www.cppblog.com/koson/archive/2010/03/24/110433.html)。	
```
MbrFix /drive 0 fixmbr /yes
```		
	
12).	 快捷键		
完整的Ubuntu里的快捷键可以看这里：[Gnome快捷键](http://wiki.ubuntu.org.cn/Gnome%E5%BF%AB%E6%8D%B7%E9%94%AE) ，不过为了方便使用和习惯，我加了几个常用更便捷的快捷键。Ubuntu 的默认显示桌面的快捷键是 Ctrl + Alt + D组合键，还是挺复杂的，我想修改成像windows一样Win + D，这样就比较好用；Ubuntu 的打开终端的快捷键是 Ctrl + Alt + T组合键，想修改成类似windows风格的—— win + R 这个快捷键。		
修改如下：		
打开终端输入： gconf-editor，到“Apps->Metacity->Global keybingdings" 出找 “show desktop”编辑值为<Super>d即可（win键在Ubuntu中名为<Super>）；找到“run_comman_termina”编辑值为<Super>r即可使用 win + R 来打开终端了。			
{% img /images/ubuntu-personal-use-notes/shortcuts.png %}		
如果想要更好的定制ubuntu的话，gconf-editor 里面有很多有用的东西，比如地址导航栏修改为显示路径等...		

13).	 使用vim替换vi		
vim比vi强大多了，vim属于vi的超集，而且能够解决很多习惯的问题，所以还是有必要使用vim替换vi的。简单地话，可以直接使用“alias vi=vim”命令即可，不过这样只能在这次启动的时候有用，下次启动后，这条就无效了，如果想下次还能使用，那么就需要修改用户自启动配置文件.bashrc，具体的修改如下： 
```	
sudo apt-get install vim-gtk #安装vim，Ubuntu默认好像没有安装vim

cd ~ #用户 home 目录
vi .bashrc #后添加下面的alias语句到最后一行（或者合适的位置）
alias vi=vim

source .bashrc # 立即生效
```
{% img /images/ubuntu-personal-use-notes/load.png %}			
		
14). 重启tomcat服务器		
又是shell编程，可见了解shell很重要啊。新建一个restart.sh，并赋予执行的权限（chmod 744），里面写上下面的shell script，那么每次想重启tomcat的时候，到tomcat/bin目录下，执行./restart.sh就可以了（当然你可以配置tomcat/bin到path下）。
```  bash restart.sh
#!/bin/sh

ps -ef|grep tomcat |awk '{print $2}' |xargs kill -9
./startup.sh
tail ../logs/catalina.out -f
```

15). 后台运行		
在一些情况下，需要长时间的执行一些命令，正常情况下，linux执行命令的时候，会等待命令执行的结果（成功或失败），那么这个时候你可能就需要等待很长时间了，此时就可以把这些命令放到后台进行，也很简单，直接在命令后面加上  & 符号就可以了，在配合 fg, bg, jobs -l 等命令，就很轻松的干其它事情了。更详细的请参考 [Linux 技巧：让进程在后台可靠运行的几种方法](http://www.ibm.com/developerworks/cn/linux/l-cn-nohup/)		

16). 开启ssh服务
``` bash
sudo apt-get install openssh-server
```
	
17）使用root用户	
终端下执行命令的时候经常需要root用户的情况下，可以最开始就使用root用户
```
sudo su
```
或者直接在Ubuntu中使用用root帐号
```
sudo passwd root
```
设置密码后就可以用使用root账号了
	
18) 修改为英文		
10.04 版本还是有点问题，修改后，home文件夹下的类似“下载”文件夹还是中文，下次安装的时候记得默认选择英文语言
	
19) ubuntu下终端路径只显示当前目录	
参考 [ubuntu下终端路径只显示当前目录](http://www.issacy.com/archives/519.html)
	
20) 安装jdk	
参考[Ubuntu 11.04 下安装配置 JDK 7](http://blog.csdn.net/yang_hui1986527/article/details/6677450) 和 [Java安装配置](http://wiki.ubuntu.org.cn/Java%E5%AE%89%E8%A3%85%E9%85%8D%E7%BD%AE)
``` bash ~/.bashrc
export JAVA_HOME=/usr/lib/jvm/jdk1.6.0_38
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```
	
21) 安装Tomcat	
下载并解压即可
	
22) 固定ip		
开通ssh服务后，如果ip经常变动的话，就会很不方便。固定ip就会更会的提供服务。		
参考[ubuntu 12.04 以固定 IP 地址连接网络并配置DNS](http://blog.csdn.net/tzb251316192/article/details/7520210)
``` bash /etc/network/interfaces
auto eth0
iface eth0 inet static
    address 192.168.1.105
    netmask 255.255.255.0
    gateway 192.168.1.1
    dns-nameservers 202.96.134.133 202.96.128.166
```

23) 安装Memcached			
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
update-rc.d /etc/init.d/memcached defaults
```

24) 安装nginx		
编译安装最新稳定版nginx请看[Ubuntu 11.10 x64编译安装nginx、PHP 5.3.8、mysql、mongodb、memcached、ssl、smtp](http://www.cnblogs.com/sink_cup/archive/2011/06/29/ubuntu_nginx_php_mongodb_memcache_mysql_ssl_gmail_smtp.html),写的很详细，其中nginx开机自启动的文章 [Ubuntu Nginx 开机自启动](http://www.cnblogs.com/lexus/archive/2010/12/21/1913109.html) 也很不错
		
25)
		
xx).	待续…		
		
注:
上面的大部分命令都是以root用户执行的，如果权限不够，请加上sudo	
	
如果有什么建议或问题的话，可以通过微博 <http://weibo.com/lishunli> 或 Email：<leeshunli@qq.com> 联系到我，大家一起交流学习。		
	
<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2012年4月14日
</p>

### 更新历史	
2013-01-08 继续更新使用中遇到的问题并安装一些服务软件 [22,23,24]			
2013-01-06 添加开启ssh服务等内容 [16,17,18,19,20,21]	
2013-01-03 更新文章的死亡链接，使用后添加更多的注意点	
2012-04-26 添加重启tomcat服务器脚本和后台运行 [14,15]		
2012-04-14 初步完成部分的使用习惯	