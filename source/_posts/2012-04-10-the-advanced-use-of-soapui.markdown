---
layout: post
title: "SoapUI的进阶使用"
date: 2012-04-10 22:00
comments: true
categories: SoapUI
---
关于SoapUI的简单使用，大家可以参考网上并自己摸索下，这里就不说了，下面说的是一些不常见的用法（但是又很有用），再加一些性能测试方面的经验。		

#### File Import
很多时候会有 File Import（Upload）类的Functions的，如果要用SoapUI做集成测试的话，免不了要处理 byte[] 类型的参数，那么如何通过SoapUI传递byte\[](File)的参数了，实际上，也很简单，看图就很清楚了。			
{% img /images/first-last-day/screenshot-20120327104943.png%}

#### Property Transfer
Property Transfer 就是在SoapUI不同的Test Steps之间传递数据，具体的可以看看文档 [Transferring Property Values](http://www.soapui.org/Functional-Testing/transfering-property-values.html)。通过下面的实例图你就创建了一个Property Transfer并添加了一个Transfer Value，而每个Transfer Value都有两个部分，一个是Source，一个是Target，都可以选择不同的Step和不同的Property。		
{% img /images/first-last-day/screenshot-20120328181006.png %}			
如何配置这些内容，请看一个例子。e.g.				
			
假设在一个系统中有一个function：send msg step，它需要一个参数，而这个参数是另一个Step：login 成功后的response，那么这种情况就可以这样：		
{% img /images/first-last-day/screenshot-20120328173450.png %}		
贴上要写的配置信息：
``` xml Source
declare namespace ns1='http://mic2.taifook.com/';
//ns1:loginResponse/return
```
``` xml Target
declare namespace mic2='http://mic2.taifook.com/';
//mic2:send/arg0
```

#### Groovy
在SoapUI中可以写一些脚本，这个脚本语言就是 [Groovy](http://groovy.codehaus.org/)  ，实际上平常我们需要使用的Groovy还是很简单的（因为和Java很类似），下面举一些例子来看看：		
e.g.1
``` groovy rand
import static java.util.UUID.randomUUID
randomUUID() as String
```
这个就是用UUID作为一个随机字符串的例子，在Test Step中可以这样使用（上面Groovy Script 文件名为 rand）
``` xml
<arg2>${rand#result}</arg2>
```
e.g.2
``` groovy getAccount
def num = Integer.parseInt(testRunner.testCase.getPropertyValue( "count" )) 
num = (++num) % 10
testRunner.testCase.setPropertyValue( "count", num + "")
String[] acList = [
"02-0000000-22",
"02-1000001-22",
"02-2000002-22",
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
这个就是顺序并轮流从一个长度为10的字符串数组里取一个元素（第一行有些错误，一开始得不到 count's PropertyValue ,你可以用类似第三行先 set count = 0 后删除，或者手动添加 count PropertyValue，如何添加？请自己google下），这个可以用来做一些性能测试的一些数据输入（可以做到尽量不同），用法和上面一样
``` xml
<arg3>    
&lt;MSG>&lt;REC instrCode="985" ... acId="${getAccount#result}" ... />&lt;/MSG>
</arg3>
```
e.g.3
``` groovy initData
import au.com.bytecode.opencsv.CSVReader;

def filename = testRunner.testCase.getPropertyValue( "filename" )
log.info("[load data] CSV File Path and Name: " + filename)
log.info("[load data] Reading the CSV...")

CSVReader reader = new CSVReader(new FileReader(filename));

def isHeader = true
List approvalList = []

while ((nextLine = reader.readNext()) != null) {
	if (isHeader) {
	    isHeader = false
	} else {
		approvalList.add nextLine  
	}
}
//set property to test case context 
def loopCount = 0
log.info("[load data] Number of Approval: " + approvalList.size())
context.setProperty("approvalList",approvalList)
context.setProperty("loopCount",loopCount);

// get property
log.info("[loop start] Current Loop Count: " + context.loopCount);
def msg = context.approvalList.remove(0)

testRunner.testCase.testSuite.getTestCaseByName("Approval").setPropertyValue("userid",msg[0])
testRunner.testCase.testSuite.getTestCaseByName("Approval").setPropertyValue("password",msg[1])
testRunner.testCase.testSuite.getTestCaseByName("Approval").setPropertyValue("key",msg[2])
testRunner.testCase.testSuite.getTestCaseByName("Approval").setPropertyValue("body","&lt;![CDATA[" + msg[3].trim() + "]]&gt;")

context.setProperty("loopCount", ++context.loopCount);
```
看到没有，这个例子就很像Java了，用其它的Jar包（opencsv-xxx.jar，请把它放到SoapUI安装路径下的lib目录下）里的方法来读csv格式的文件，然后把读出来的数据放到List中并存到context中，后面还用到了getTestCaseByName获得当前的Test Case（本例中TestCase名为Approval，请注意：不是Test Step名），并赋值以供使用。使用这里的值，也很简单
``` xml
<arg0>${#TestCase#userid}</arg0>
```
		
这里的例子只是冰山一角，更多的请参考：		
[Tips & Tricks](http://www.soapui.org/Scripting-Properties/tips-a-tricks.html)			
[Property Expansion](http://www.soapui.org/Scripting-Properties/property-expansion.html)				

#### Load Tests		 
[Load Tests](http://www.soapui.org/Getting-Started/load-testing.html) 就是使SoapUI连续多次执行一个Test Steps，很简单就可以创建了，在【Load Tests】选项【New LoadTest】就可以了，大概会是下面的样子，具体的测试方法你就可以亲自试试了（比如Limit,Threads等配置）。		
{% img /images/first-last-day/screenshot-20120328181558.png %}				

这个也是在工作中的一个分享，对象是有使用SoapUI经验的同事，所以，您看这部分，可能会有一些不清楚的，如果有什么建议或问题的话，可以通过微博 <http://weibo.com/lishunli> 或 QQ：506817493 或 Email：<leeshunli@qq.com> 联系到我，大家一起交流学习。

<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2012年4月10日
</p>
     