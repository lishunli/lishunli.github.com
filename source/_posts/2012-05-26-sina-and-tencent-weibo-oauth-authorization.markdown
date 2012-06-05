---
layout: post
title: "小谈新浪、腾讯微博OAuth授权认证"
date: 2012-05-26 11:14
comments: true
categories: [weibo, OAuth, Tips, 新浪, 腾讯]
---

最近有点时间就想弄个小程序玩玩，主要是同步不同服务提供商（现在支持新浪和腾讯）之间微博的同步，顺便也学习下一些常用微博的SDK。
谈过微博开放，当然最最主要的还是OAuth授权（当然还有API了），关于什么是OAuth，如何授权，对于开发者的你，应该很简单就能获取到。
<!-- more -->

基于Oauth的微博开发，新浪和腾讯都有不同的优缺点。 新浪微博——优点：SDK简单，封装较好，Oauth授权后易于保存（保存token和tokensecret后下次就不需要再授权就可以使用了）；缺点就是Oauth限制太多了吧，测试授权- 发微博：单用户每应用 30次/小时，太少了吧。 腾讯微博——差不多就是相对的了。


小谈新浪、腾讯微博Oauth授权认证
优缺点
授权后如何处理

sina提供了Oauth 1.a 向 2 的转化，而tx没有，sdk 默认是1

腾讯微博 转发部分
部署到 GAE 或者 SAE

关于封装OAuth，和使用API，这部分网上很多文章，但是发现很多文章应该也只能算学习的一些经验，感觉确实商业或者稍微大型一点项目经验的分享。比如是这样的

{% img /images/sina-and-tencent-weibo-oauth-authorization/ER.png %}	
{% img /images/sina-and-tencent-weibo-oauth-authorization/tables.png %}	

{% include_code sina-and-tencent-weibo-oauth-authorization/weibo.sql lang:sql %}


实际上我感觉记录腾讯微博的授权后信息就是一个废柴，根本没有多大的时间用途，还不于在某些特殊情况丢失token的时候，email通知用户重新授权这样好点（当前前途是已经保存OAuth在Memcached 中，放在Map中太不稳定了）。

强迫症发了，每月至少一篇文章还是要坚持的。

本文中的源码因为用到了公司的一些基础包，所以不会提供下载，不好意思。如果有什么建议或问题的话，可以通过微博 <http://weibo.com/lishunli> 或 QQ：506817493 或 Email：<leeshunli@qq.com> 联系到我，大家一起交流学习。

<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2012年5月31日<br>
</p>