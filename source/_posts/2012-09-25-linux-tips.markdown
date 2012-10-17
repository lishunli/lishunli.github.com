---
layout: post
title: "linux tips"
date: 2012-09-25 21:50
comments: true
categories: [Tips, Linux]
---

这是一个流水的笔记，记录着我在使用Linux中的点点滴滴。从某个时间起，由于公司的服务器用到的是Linux（以前也有，不过没机会接触到），就慢慢的适应和逐步熟练，虽然以前也有些概念，但是Linux和开发是一样的，没有实践很快这部分的知识就流失了，IT人员需要在实战中进步。

常用命令
# 切换到 命令行 模式
vi /etc/initab
3

ps -ef
startx
ps -ef | grep ssh
df -H

man 命令 #帮助

ll = ls -l
last #查询登录用户信息
who
history #打印历史命令
!n #执行第n条history的命令
!! #执行上条命令
!comand #执行最近一次 command 的命令（可以简写），匹配

cd -
change to the previous working directory

grep匹配多个单词
 tail -f  dcfsummerkilltask.log | grep -E '(getMedalNumber-success|calc)'

http://www.commandlinefu.com/commands/browse/sort-by-votes

mtr google.com
mtr, better than traceroute and ping combined
mtr combines the functionality of the traceroute and ping programs in a single network diagnostic tool.
As mtr starts, it investigates the network connection between the host mtr runs on and HOSTNAME. by sending packets with purposly low TTLs. It continues to send packets with low TTL, noting the response time of the intervening routers. This allows mtr to print the response percentage and response times of the internet route to HOSTNAME. A sudden increase in packetloss or response time is often an indication of a bad (or simply over‐loaded) link.

cp filename{,.bak}
quickly backup or copy a file with bash

> file.txt
For when you want to flush all content from a file without removing it (hat-tip to Marc Kilgus).

curl -u user:pass -d status="Tweeting from the shell" http://twitter.com/statuses/update.xml
Update twitter via curl

ssh user@host cat /path/to/remotefile | diff /path/to/localfile -
Useful for checking if there are differences between local and remote files.



linux下ls命令显示文件数量
ls | wc -w
ws是统计语句，-w就是按照词来统计，所以这样就能看到文件数量了。

查看子目录的文件
find   ./   -name   *.log*
find   ./logs/   -name   *.log*

查找并删除文件
find 目录 -name 名称|xargs rm -rf

怎样按文件大小排序查看目录中的文件
ls -l |sort -n
du -sk * | sort -n

过滤一些文件（夹）来压缩
tar zvcf tomcat7-85.tar.gz  --exclude=core.3343 --exclude=logs tomcat7-85 （推荐使用这个，exclude顺序也很重要）
tar --exclude=*.log --exclude *.out --exclude=core.3343  -zvcf tomcat7-85.tar.gz tomcat7-85 

Linux下find一次查找多个指定类型文件，指定文件或者排除某类文件
find . -type f ! -name "*Error.log"
find . -type f ! -name "*Error.log"  |xargs tail -f

cut – 选择文件中字符行上的某些区域
cat xmlevelingsendvip_Error.log  | cut -d , -f10



cd -
快速返回到前一次目录

显示文件函数
 wc -l *

去除重复的行
sort team_member_uid_*.txt | uniq > team2.txt

复制到剪贴板
clip < ~/.ssh/id_rsa.pub


看你在Linux下最常用的命令是哪些？ history | awk '{CMD[$2]++;count++;} END { for (a in CMD )print CMD[ a ]" " CMD[ a ]/count*100 "% " a }' | grep -v "./" | column -c3 -s " " -t |sort -nr | nl | head -n10 

查看系统配置
lsb_release -a #查看系统版本
cat /proc/cpuinfo #查看cpu信息
lspci #查看主板信息
free #查内存
df -h #查硬盘
du -sh * 查看文件夹的大小，*可以替换成某个文件夹
dmesg #用来显示开机信息


关于网络校时： ntpdate
基本上，网络校时需要两个步骤：
   1. 由 time.stdtime.gov.tw 取得最新的时间，并实时更新 Linux 系统时间；
   2. 更改 BIOS 的时间。
[root @test root]# ntpdate 210.72.145.44
[root @test root]# clock –w  

调整linux其实最简单的办法就是让系统自己跟NTP服务器同步
ntpdate 210.72.145.44
129.7.1.66ntp-sop.inria.frserver 210.72.145.44(中国国家授时中心服务器IP地址)
ntp.sjtu.edu.cn (上海交通大学网络中心NTP服务器地址）
202.120.2.101 (上海交通大学网络中心NTP服务器地址）