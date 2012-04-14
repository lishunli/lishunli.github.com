---
layout: post
title: "ubuntu-personal-use-notes"
date: 2012-04-14 15:54
comments: true
categories: 
---

最近读了[《鸟哥的Linux私房菜》](http://book.douban.com/people/lishunli/collect)，并认真学习了如何使用Linux，这里做个小笔记。		
注：以下内容网上很多，而这里是我自己的个人使用笔记，供以后查找和使用，也希望能对一些初步使用者有点帮助。

### Windows7下安装Ubuntu 10.04 LTS 
1.	删除卷		
格式化一个盘作为Ubuntu安装分区（如何删除卷，计算机管理 -> 磁盘管理，选择需要格式的盘，右键删除卷）
2.	安装EasyBCD			
安装Ubuntu这部分请参考 [ubuntu 11.04在win7下安装无损硬盘安装双系统的图文教程](http://zxdker.com/post/ubuntu-11-04-win7-yingpan-anzhuang-shuangxitong-tuwen-jiaocheng.htm)
这篇文章中写的也很详细，图文并茂，而且我试过，都没有问题。说说其中几个注意的地方：		
a)	*sudo umount -l /isodevice*			
这个不清楚，网上都要做，我也做了，我觉得现在这个版本（10.04LTS）应该解决了这个问题，以后有机会可以试试不加这个会如何。		
b)	*分区*			
我选择的是手动分区， 整个区大概有18G，swap 分区1G（我内存2G），/ 分区为 8G，剩下的都给了 /home 分区。		

### Ubuntu的使用
1.	修改启动项
a)	设置Windows7为首启动项
sudo mv /etc/grub.d/30_os-prober /etc/grub.d/06_os-prober
b)	更新启动项
sudo update-grub
2.	修正grub等待时间
参考这个 http://lookluk.blogbus.com/logs/56313760.html
sudo gedit /etc/default/grub
修改 GRUB_TIMEOUT=10，默认10s，修改为自己想要的吧（e.g. GRUB_TIMEOUT=3）
3.	修改Num Lock（小键盘）自动启动开启（亮）
参考这里 http://www.yucoat.com/linux/ubuntu_enable_numlock_in_boot.html
sudo gedit /etc/rc.local
在"exit 0"前面加上
for tty in /dev/tty[1-9]*;do
    setleds -D +num < $tty
done
4.	更新源
查看这里 http://forum.ubuntu.org.cn/viewtopic.php?t=268843 
很清楚，很简单，为了更快地下载速度，我选择网易
这个步骤可能在上面安装的时候也做一下比较好，因为安装的时候，下载比较慢（比如语言包）。待测试，下次再安装的时候，我来试试。
5.	查看配置
这个是为了以后买预装Linux系统的电脑准备的。
记住一些常见的就可以了。
lsb_release -a #查看系统版本
cat /proc/cpuinfo #查看cpu信息
lspci #查看主板信息
free #查内存
df -h #查硬盘
来源 http://www.pcdog.com/edu/linux/19/03/y320447.html
6.	安装Chrome
简单，下载chrome（deb包），一般默认就会使用“软件管理器”打开，即可安装，或者使用sudo dpkg -i xxx.deb 命令。
安装好了，当然要卸载默认的浏览器了，Firefox

Chrome不同系统间同步


7.	安装wine
a)	安装
b)	中文乱码
还是没搞定，本想通过wine来安装一个Evernote的，不过还是有很多问题，现在也只能通过浏览器，或者以后想办法，安装个虚拟机了。
8.	解决切换到Windows7系统后，时间错误的问题
但我从Ubuntu切换到Windows7后，发现时间是不正确的，修改方法如下：
参考 http://benyouhui.it168.com/thread-1633701-1-1.html
sudo gedit /etc/default/rcS
找到这一行：UTC=yes，把 yes改为no
9.	修改hosts
这个你懂得是干什么的，推荐一个比较常更新的hosts(好像现在也不常更新了)：http://code.google.com/p/smarthosts/ ，通过这里你可以下载 hosts，推荐使用 chrome（上面已介绍），强制使用 https，比如 google，YouTube。
如果修改hosts，请查看 http://14551.org/2009/12/2166 
a)	修改hosts
sudo gedit /etc/hosts
b)	添加hosts记录
c)	保存后重启网络
sudo /etc/init.d/networking restart
10.	查看某个文件的完整路径
这个就是一个简单的shell编程，unix-like系统只有pwd显示当前目录的命令，而没有显示一个文件的完整路径的命令，实际上，有这样的需求也是小部分，很少机会会需要使用到，但是我碰到了，想办法解决，后面在同事的帮助下就写了一个shell脚本，就很容易办到了

关于linux shell，推荐看看 http://www.cnblogs.com/suyang/archive/2008/05/18/1201990.html 。

11.	 如果想删除Ubuntu系统，那么如何做了
比较简单，参考这篇文章就可以搞定了，http://www.cppblog.com/koson/archive/2010/03/24/110433.html。
12.	 快捷键
13.	 使用vim替换vi
sudo apt-get install vim-gtk

cd ~
vi .bashrc
后添加下面语句到最后一行（或者合适的位置）
alias vi=vim

source .bashrc # 立即生效

14.	待续…


顺利
2012年3月4日
更新于2012年3月4日
