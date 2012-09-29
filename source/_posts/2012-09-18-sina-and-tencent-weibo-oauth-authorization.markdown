---
layout: post
title: "小谈新浪、腾讯微博OAuth授权认证"
date: 2012-09-18 11:14
comments: true
categories: [weibo, OAuth, Tips, 新浪, 腾讯]
---

这篇文章本应该早就写好了，期间工作比较忙耽误了好长时间（起笔是在5月份，请容许我找找借口 O~），中间也经历了Sina weibo Dev只支持OAuth2.0（认证后可以支持OAuth 1.x）等事情，后来这篇文章也一直再修改，等完成的时候发现已经没有当初的整体思路了，这告诉我们，乘热打铁很重要，乱笔头胜与好记性。现在也只能说说一二了。

有一段时间想弄个小程序玩玩，主要是同步不同服务提供商（现在支持新浪和腾讯）之间微博的同步，顺便也学习下一些常用微博的SDK。
谈过微博开放，当然最最主要的还是OAuth授权（当然还有API了），关于什么是OAuth，如何授权，对于开发者的你，应该不会太陌生。
<!-- more -->

为什么会有同步微博的想法了？微博使用的频率严重偏离，比如我，使用新浪微博比腾讯微博多多了，而且这另一个微博又有很多的关系（真的离不开腾讯啊）。网上确实有很多微博同步的，比如 云同步 ...还有很多插件

基于Oauth的微博开发，新浪和腾讯都有不同的优缺点。 新浪微博——优点：SDK简单，封装较好，Oauth授权后易于保存（保存token和tokensecret后下次就不需要再授权就可以使用了）；缺点就是Oauth限制太多了吧，测试授权- 发微博：单用户每应用 30次/小时，太少了吧。 腾讯微博——差不多就是相对的了。



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

新浪微博 回调 URL 不能带参数

说明
双向

对腾讯微博SDK的改动
support memcached.(Serializable)




有想过把服务架好供大家使用，找了很多免费的java服务，也只发现SAE和GAE好用些，后面偶然的机会得到了SAE Java开发的邀请码，后面就花了很长时间把这个服务放到SAE中，后来的种种事情说明了这些都是投劳的。说说我在SAE的种种不快：
1. 这个程序有些服务用到了Memcached，在后来的迁移过程中发现SAE的MC对对象的支持并不是那么好，几乎都取不到（序列化什么都是正常的，本机的MC测试都ok）。MC解决不了，我就转用cookie，这部分的问题不完美的解决了。
2. 后面发现的一个问题，彻底使我放弃了，那就是服务跳转问题，因为是OAuth，涉及到很多的认证需要跳到其它域名下，但是socket是传不去的，没办法，只能借助SAE中的Fetch URL服务（p.s. 收费，扣云豆）来改造sina和tencent weibo sdk里面的一些URL跳转，到后面修改好了部署到SAE，期待地发现 Server Error。


以前天真地想到可能推个产品出来，顺便做些收费的改进，以下是我想到可能的收费模式：
双向
同步频率 
更多的服务提供商，比如 搜狐微博，Twitter等（没有实现）
非收费用户只能同步一个账号（没有实现）
后来发现是我想多了。


不好意思。如果有什么建议或问题的话，可以通过微博 <http://weibo.com/lishunli> 或 QQ：506817493 或 Email：<leeshunli@qq.com> 联系到我，大家一起交流学习。

<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2012年9月18日<br>
</p>