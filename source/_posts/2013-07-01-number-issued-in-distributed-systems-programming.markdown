---
layout: post
title: "分布式系统中发号程序设计猜想"
date: 2013-07-01 23:04
comments: true
categories: [Tips, other]
---


分布式系统中发号程序设计猜想

多jvm

保证唯一性

按照业务规则直接生成号，这个简单，


但是假如只能从生成好的一批号中领取一个，怎样设计比较合理？



生成唯一的主键，uuid RandomStringUtils
目前的解决方案是insert，使用分布式锁
