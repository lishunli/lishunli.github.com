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
安装Ubuntu这部分请参考 [ubuntu 11.04在win7下安装无损硬盘安装双系统的图文教程](http://zxdker.com/post/ubuntu-11-04-win7-yingpan-anzhuang-shuangxitong-tuwen-jiaocheng.htm)
这篇文章中写的也很详细，图文并茂，而且我试过，都没有问题。说说安装过程中可能需要注意的几个地方：		
a)	*sudo umount -l /isodevice*			
这个不清楚，网上都要做，我也做了，我觉得现在这个版本（10.04LTS）应该解决了这个问题，以后有机会可以试试不加这个会如何。		
b)	*分区*			
我选择的是手动分区， 整个区大概有18G，swap 分区1G（我内存2G），/ 分区为 8G，剩下的都给了 /home 分区（也可以自动分区）。		

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

7).	安装wine		
a)	安装		
安装应该很简单，关键是一些配置，比如关于中文字体方面的可能麻烦的，可以参考 [Ubuntu Wine Wiki](http://wiki.ubuntu.org.cn/Wine) 和 [Wine完全使用指南——从基本到高级](http://forum.ubuntu.org.cn/viewtopic.php?t=72933)			
b)	中文乱码		
还是没搞定，本想通过wine来安装一个Evernote和QQ的，不过还是有很多问题，现在也只能通过浏览器，以后很必须的时候再想办法了，e.g.安装个虚拟机。

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

xx).	待续…		
		
如果有什么建议或问题的话，可以通过微博 <http://weibo.com/lishunli> 或 QQ：506817493 或 Email：<leeshunli@qq.com> 联系到我，大家一起交流学习。		
	
<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2012年4月14日<br>
最后更新于2012年4月26日
</p>