---
layout: post
title: "不同概率的抽奖"
date: 2012-10-16 21:56
comments: true
categories: [Tips]
---

今天为大家写个小程序。			
		
工作中有遇到一些抽奖的活动，但是你懂得，抽奖物品的概率肯定不是一样，你会发现好的东西很难抽到，经常抽到一些垃圾的东西，嘿嘿，这就是本文要说的，我们要控制抽奖物品的概率。还有顺便说一句，网上这种小程序几乎没有，很多都是等概率的抽奖balabala...	
					
<!-- more -->
需求很简单，为了更加形象，这里我们列一个表格来显示我们抽奖的物品和对应的概率（没有边框，大家凑合着看看吧，不想改造Octopress的样式了）		
		
<table border="2" width="50%">
    <tr>
        <td>序号</td><td>物品名称</td><td>物品ID</td><td>抽奖概率</td>
    </tr>
	<tr>
        <td>1</td><td>物品1</td><td>P1</td><td>0.2</td>
    </tr>
	<tr>
        <td>2</td><td>物品2</td><td>P2</td><td>0.1</td>
    </tr>
	<tr>
        <td>3</td><td>物品3</td><td>P3</td><td>0.4</td>
    </tr>
	<tr>
        <td>4</td><td>物品4</td><td>P4</td><td>0.3</td>
    </tr>  
	<tr>
        <td>5</td><td>物品5</td><td>P5</td><td>0.0</td>
    </tr>  
	<tr>
        <td>6</td><td>物品6</td><td>P6</td><td>-0.1</td>
    </tr>  
	<tr>
        <td>7</td><td>物品7</td><td>P7</td><td>0.008</td>
    </tr>  	
</table>
		
数据很简单，那么就直接看代码了			
		
VO类，具体对应就是上面表格里的内容
{% include_code lottery/src/main/java/org/usc/usc/lottery/Gift.java %}
		
工具类，真正的不同概率的抽奖就在这里
{% include_code lottery/src/main/java/org/usc/usc/lottery/LotteryUtil.java %}
		
测试类，测试上面的抽奖是否成功，n次抽奖看抽奖结果
{% include_code lottery/src/main/java/org/usc/usc/lottery/LotteryTest.java %}
		
结果
``` 
Gift [index=1, gitfId=P1, giftName=物品1, probability=0.2], count=199139, probability=0.199139
Gift [index=2, gitfId=P2, giftName=物品2, probability=0.1], count=99328, probability=0.099328
Gift [index=3, gitfId=P3, giftName=物品3, probability=0.4], count=396575, probability=0.396575
Gift [index=4, gitfId=P4, giftName=物品4, probability=0.3], count=296997, probability=0.296997
Gift [index=7, gitfId=P7, giftName=物品7, probability=0.0080], count=7961, probability=0.007961
```	
		
		
不同概率的抽奖原理很简单			
就是把0到1的区间分块，而分块的依据就是物品占整个的比重，再根据随机数种子来产生0-1中间的某个数，来判断这个数是落在哪个区间上，而对应的就是抽到了那个物品。随机数理论上是概率均等的，产生的每个数理论上也应该概率均等，那么相应的区间所含数的多少就体现了抽奖物品概率的不同。（p.s. 当然数目是数不清楚的，具体抽象话了点）
			
这个实例的数据可以说明					
1. 概率可以是负数和0，当然实际上中应该不会（p.s. 正常情况下可能真的有0，比如抽个iphone5，当然是抽不到的了，这个时候，构建礼物（List<Gift> gifts）的时候最好就不要加这个进去），还有可以把负数的处理放到抽奖工具类(LotteryUtil)中；		
2. 所有礼物加起来的概率可以不是1，可以认为这里的概率是一个权重；
		
		
小小分享了，倒是觉得大家可以自己先想想，如果你来写这样的小程序，如何来写，有没有其它的创意和想法？如果有什么建议或问题的话，可以通过微博 <http://weibo.com/lishunli> 联系到我，大家一起交流学习。

<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2012年10月17日
</p>


