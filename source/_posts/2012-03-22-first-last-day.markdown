---
layout: post
title: "技术分享——献给可爱的你们"
date: 2012-03-22 10:19
comments: true
categories: others
---

今天是最后一天了，本想写一大段感激加温馨的话，发现这也是我不擅长的，所以以这种方式感谢大家对我的照顾。
	
这是一篇博客，记载了我在演天工作中一些比较常用的工具和一些技术的分享。

#### 软件（利器）
* Everything

* Evernote			
一款极致的免费笔记资料管理软件，支持多平台的数据网络同步（支持PC、手机多平台... 遗憾的是不支持Linux)，有了它，你就可以随时记录一些“灵感”，让你不在担心“哎，昨天我想什么来着？”，借用阿秋博客里的一句话——The palest ink is better than the best memory.		
p.s. 本想截个图的，不过感觉有点小隐私，就不打扰大家了。	

* Unlocker

* Chrome		
这个不用我说了，只要你喜欢，什么浏览器都行。i just love chrome.
* Data copier
* 加密

#### 开源库
http://code.google.com/p/jdbcdslog-exp/ （支持weblogic，jboss，tomcat等）
显示很完整的sql的 （我fork http://code.google.com/p/jdbcdslog/），支持maven dependency（我已经提交到maven中央仓库了，可以直接配置就可以了）
实际上，在目前的项目中几乎用不到maven，而是把 jar包放到 weblogic 等的 classpath下面即可。
```
<dependency>
    <groupId>com.googlecode.usc</groupId>
    <artifactId>jdbcdslog</artifactId>
    <version>1.0.6.2</version>
</dependency>
```
http://www.blogjava.net/lishunli/archive/2011/12/05/365526.html

http://code.google.com/p/table-data-copier/
这个是以前做OG的时候，用的，后面就放大，做一个使用版本

#### SoapUI
```
declare namespace ns1='http://mic2.taifook.com/';
//ns1:loginResponse/return
```

```
declare namespace mic2='http://mic2.taifook.com/';
//mic2:send/arg0
```
``` groovy getAccount
def num = Integer.parseInt(testRunner.testCase.getPropertyValue( "count" )) 
num = (++num) % 10
testRunner.testCase.setPropertyValue( "count", num + "")
String[] acList = [
"02-0000000-22",
"02-1000001-22",
"02-200000A-22",
"02-3000003-22",
"02-4000004-22",
"02-5000005-22",
"02-6000006-22",
"02-7000007-22",
"02-8000008-22",
"02-9000009-22"
]
acList[num]

```

file import
transportation
groovy



#### Eclipse常用插件


#### Eclipse常用快捷键




**内部分享，谨防泄密，**	
**版权所有，翻版必纠。**	
**(It's a joke, you can do anything if you could)**

*B.T.W 这是我的第一次，借用某游戏中的一句话：我要走了，下次在玩吧！*

<p align="right">
顺利<br>		
2012年3月30日
</p>
