---
layout: post
title: "微信公众平台SDK(Java)"
date: 2013-05-17 23:20
comments: true
categories: [open source, java, sdk, wechat, mp, github]
---

现在PHP越来越流行了，连微信公众平台官方也只提供了PHP的SDK Demo。

自己


关注我，这是一个测试账号，还没时间和心思去运营她

逐步完善中，如果觉得可以请star它，随时关注最新的更新。

XML->解析->哪类消息（PUSH）->相应的解析->根据业务逻辑生成回应->XML
时序图


wechat-mp-sdk
wechat-mp-web




没有提供log4j的配置了，请相应地添加

这不仅仅是简单的提供一个demo，而且进行了很多封装，使用起来更加容易


http://mp.weixin.qq.com/mp/appmsg/show?__biz=MjM5MjgwNjgwMA==&amp;appmsgid=10000245&amp;itemidx=1&uin=MTA4MzQ5MjkyMA%3D%3D&key=4e8d3432ef9e25d891b742fabc4d60a1f50b5cbb3d16458fad635db59035cd2e1e507bc1e3f0c5edbd097bff86465b2efaa738f31ec2c0e29e3625cd349726bf5293003c3ec1c3d441c18433b591c8929e27440a26f995033e3304ff9828617bf04cc86d9f9eb9c4b3855dd9c9c7159c2250fe3c2105d84574b193d52d8ce1a2&devicetype=android-15&version=24050122&lang=zh_CN

《微信公众平台入门到精通》可以快速地加深对微信公众平台的理解，建议可以看看。

也可以像 xxx 一样，试着在SAE上搭建个简单的demo试试效果（好像SAE Java是要申请的，自从云豆用完后就没关注了）

之前实际上也写过Weibo（新浪和腾讯） OAuth的实例，一直没


搜索 javatech 或者扫一扫

微信还有一个开放平台，不过貌似只有手机部分

小吐槽


模拟登陆等获取

jaxb cddata 不好实现






http://www.zhihu.com/question/20956354


回复现在开放程度还不高，好多回复类型和权限还没开放
只能回复一条，不能像编辑模式中可以发送多条


微信公众平台
1. xml <-> object
2. string template
3. 根据MsgType来生成相应的Msg对象
4. 根据MsgType来确定相应的处理类
好的设计：策略，解释器，工厂，建造者




