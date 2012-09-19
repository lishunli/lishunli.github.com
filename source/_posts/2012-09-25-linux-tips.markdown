---
layout: post
title: "linux tips"
date: 2012-09-25 21:50
comments: true
categories: [Tips, Linux]
---

����һ����ˮ�ıʼǣ���¼������ʹ��Linux�еĵ��εΡ���ĳ��ʱ�������ڹ�˾�ķ������õ�����Linux����ǰҲ�У�����û����Ӵ�����������������Ӧ������������Ȼ��ǰҲ��Щ�������Linux�Ϳ�����һ���ģ�û��ʵ���ܿ��ⲿ�ֵ�֪ʶ����ʧ�ˣ�IT��Ա��Ҫ��ʵս�н�����

��������
# �л��� ������ ģʽ
vi /etc/initab
3

ps -ef
startx
ps -ef | grep ssh
df -H

man ���� #����

ll = ls -l
last #��ѯ��¼�û���Ϣ
who
history #��ӡ��ʷ����
!n #ִ�е�n��history������
!! #ִ����������
!comand #ִ�����һ�� command ��������Լ�д����ƥ��

cd -
change to the previous working directory

grepƥ��������
 tail -f  dcfsummerkilltask.log | grep -E '(getMedalNumber-success|calc)'

http://www.commandlinefu.com/commands/browse/sort-by-votes

mtr google.com
mtr, better than traceroute and ping combined
mtr combines the functionality of the traceroute and ping programs in a single network diagnostic tool.
As mtr starts, it investigates the network connection between the host mtr runs on and HOSTNAME. by sending packets with purposly low TTLs. It continues to send packets with low TTL, noting the response time of the intervening routers. This allows mtr to print the response percentage and response times of the internet route to HOSTNAME. A sudden increase in packetloss or response time is often an indication of a bad (or simply over�\loaded) link.

cp filename{,.bak}
quickly backup or copy a file with bash

> file.txt
For when you want to flush all content from a file without removing it (hat-tip to Marc Kilgus).

curl -u user:pass -d status="Tweeting from the shell" http://twitter.com/statuses/update.xml
Update twitter via curl

ssh user@host cat /path/to/remotefile | diff /path/to/localfile -
Useful for checking if there are differences between local and remote files.



linux��ls������ʾ�ļ�����
ls | wc -w
ws��ͳ����䣬-w���ǰ��մ���ͳ�ƣ������������ܿ����ļ������ˡ�

�鿴��Ŀ¼���ļ�
find   ./   -name   *.log*
find   ./logs/   -name   *.log*

���Ҳ�ɾ���ļ�
find Ŀ¼ -name ����|xargs rm -rf

�������ļ���С����鿴Ŀ¼�е��ļ�
ls -l |sort -n
du -sk * | sort -n

����һЩ�ļ����У���ѹ��
tar zvcf tomcat7-85.tar.gz  --exclude=core.3343 --exclude=logs tomcat7-85 ���Ƽ�ʹ�������exclude˳��Ҳ����Ҫ��
tar --exclude=*.log --exclude *.out --exclude=core.3343  -zvcf tomcat7-85.tar.gz tomcat7-85 

Linux��findһ�β��Ҷ��ָ�������ļ���ָ���ļ������ų�ĳ���ļ�
find . -type f ! -name "*Error.log"
find . -type f ! -name "*Error.log"  |xargs tail -f

cut �C ѡ���ļ����ַ����ϵ�ĳЩ����
cat xmlevelingsendvip_Error.log  | cut -d , -f10



cd -
���ٷ��ص�ǰһ��Ŀ¼

��ʾ�ļ�����
 wc -l *

ȥ���ظ�����
sort team_member_uid_*.txt | uniq > team2.txt

���Ƶ�������
clip < ~/.ssh/id_rsa.pub


������Linux����õ���������Щ�� history | awk '{CMD[$2]++;count++;} END { for (a in CMD )print CMD[ a ]" " CMD[ a ]/count*100 "% " a }' | grep -v "./" | column -c3 -s " " -t |sort -nr | nl | head -n10 

�鿴ϵͳ����
lsb_release -a #�鿴ϵͳ�汾
cat /proc/cpuinfo #�鿴cpu��Ϣ
lspci #�鿴������Ϣ
free #���ڴ�
df -h #��Ӳ��
du -sh * �鿴�ļ��еĴ�С��*�����滻��ĳ���ļ���
dmesg #������ʾ������Ϣ


��������Уʱ�� ntpdate
�����ϣ�����Уʱ��Ҫ�������裺
   1. �� time.stdtime.gov.tw ȡ�����µ�ʱ�䣬��ʵʱ���� Linux ϵͳʱ�䣻
   2. ���� BIOS ��ʱ�䡣
[root @test root]# ntpdate 210.72.145.44
[root @test root]# clock �Cw  

����linux��ʵ��򵥵İ취������ϵͳ�Լ���NTP������ͬ��
ntpdate 210.72.145.44
129.7.1.66ntp-sop.inria.frserver 210.72.145.44(�й�������ʱ���ķ�����IP��ַ)
ntp.sjtu.edu.cn (�Ϻ���ͨ��ѧ��������NTP��������ַ��
202.120.2.101 (�Ϻ���ͨ��ѧ��������NTP��������ַ��