---
layout: post
title: "技术分享——献给可爱的你们"
date: 2012-03-30 10:19
comments: true
categories: others
---

今天是我在演天的最后一天了，本想写一大段感激加温馨的话，发现这也是我不擅长的，所以以这种方式感谢大家对我的照顾和关心（好快，来演天都两年了）。	

这是一篇博客，记载了我在演天工作中一些比较常用的工具和一些“另类”的技术，本想以技术串讲的方式，后来想以文字的方式也许更能留得住回忆。
<!-- more -->
### 一、软件（利器）
#### 1.1 Everything	
Everything可以快速的搜索你本地硬盘（仅支持NTFS格式）的所有文件，速度秒杀一切工具，缺点就是只能根据文件名来搜索，不能根据内容来（这个Google Desktop Search也不太好用），但是这已经足够了。	
详情请 via [善用佳软-Everything:速度最快的文件名搜索工具](http://xbeta.info/everything-search-tool.htm) 。		
Tips：尽量加一个快速打开的快捷键，比如我设置为F7，并过滤一些不想显示的文件，比如 .class 文件。		
{% img /images/first-last-day/everything.gif 800 600 'Everything' 'Everything' %}

#### 1.2 Evernote			
一款极致的免费笔记资料管理软件，支持多平台（支持PC、手机多平台... 遗憾的是不支持Linux）的数据网络同步，有了它，你就可以随时记录一些“灵感”，让你不在担心“哎，昨天我想什么来着？”，借用[阿秋博客](http://liwenqiu.github.com/)里的一句话——The palest ink is better than the best memory。		

#### 1.3 Unlocker		
Java开发中，很多会有依赖或者关联关系，有的时候，你想移动或者删除一个文件（夹），但是发现“它正在使用”，那这个时候你就可以使用[Unlocker](http://www.emptyloop.com/unlocker/), 它还可以帮助你，弹出USB的时候报设备正在使用的问题。	

{% img /images/first-last-day/screenshot-20120326102540.png %}{% img /images/first-last-day/snapshot-000.png %}		

#### 1.4 Chrome		
这个不用我说了，只要你喜欢，什么浏览器都行，用着习惯就好。
			
### 二、开源库
#### 2.1 jdbcdslog-exp		
介绍[jdbcdslog-exp]之前我先来介绍下[jdbcdslog]，jdbcdslog是一款jdbc spy的开源项目，主要用来记录SQL中参数的真实值，使用Hibernate的你，应该知道，Log中SQL有很多的 "?" ，这就是参数，但是Hibernate并不能帮助我们显示出来，有这样的需求就可以使用jdbcdslog，当然jdbcdslog的功能不止这些，详情请浏览jdbcdslog的网站 <http://code.google.com/p/jdbcdslog/> 和使用指南 <http://code.google.com/p/jdbcdslog/wiki/UserGuide> 。	

jdbcdslog-exp是jdbcdslog的加强版（原始功能全部都有），目前由我一个人在维护，她的主页在 <http://code.google.com/p/jdbcdslog-exp/> (github 上也有，就不贴了，搜搜就找到了)，主要是在原来的基础上，改进了SQL显示的效果，提供更多的配置，让你随心所欲的使用，并支持Maven。以前写过一篇博客——[更有效地跟踪Bug——记录带有详细参数值的SQL](http://www.blogjava.net/lishunli/archive/2011/12/05/365526.html)，对比分析了几种常见的 jdbc spy 工具并介绍了 jdbcdslog-exp，大家可以看看。你还可以通过SVN Checkout下来代码
```
svn checkout http://jdbcdslog-exp.googlecode.com/svn/trunk/
```

这里详细说一下在我们的项目中如何使用：		
**1). MSS-E(Weblogic)**			
a)	把下载好的jdbcdslog.jar(e.g. jdbcdslog-1.0.6.2.jar) 放到 domains\msseDomain\lib 下;		
b)	配置classpath，Windows下修改 msseDomain\bin\setDomainEnv.cmd 中的 set PRE_CLASSPATH= ... ,加上 jdbcdslog 的文件路径;		
c)	可以使用 Datasource(也就是修改 primary 下的 appctx-mss-app-main.xml中的 xxDataSource ，参考jdbcdslog 的 Wiki)，建议使用 jdbc config，进入 msseDomain\config\jdbc 这个文件夹，具体修改如下：		
``` xml
<driver-name>oracle.jdbc.xa.client.OracleXADataSource</driver-name>
==>
<driver-name>org.jdbcdslog.ConnectionPoolXADataSourceProxy</driver-name>	

<url>jdbc:oracle:thin:@10.100.53.85:1521:cmn</url>
==>
<url>jdbc:oracle:thin:@10.100.53.85:1521:cmn?targetDS=oracle.jdbc.xa.client.OracleXADataSource</url>	
```	
请按照上面的规则，替换jdbc文件夹下所有的配置文件（有7个，实际上也可以不用所有的都替换，替换需要的就可以了，还有最好先备份，方便随时还原或者切换jdbc配置文件）。		
d) 配置log4j，可以记录jdbcdslog 的 log， 修改 msseDomain\extconf\log4j.xml，添加
``` xml 
  <!-- logger: jdbcdslog -->
  <logger name="org.jdbcdslog.StatementLogger">
    <level value="INFO"/>
  </logger>
  
  <logger name="org.jdbcdslog.ResultSetLogger">
    <level value="OFF"/>
  </logger> 
  
   <logger name="org.jdbcdslog.SlowQueryLogger">
    <level value="OFF"/>
  </logger>  
  
  <logger name="org.jdbcdslog.ConnectionLogger">
    <level value="OFF"/>
  </logger>
```	
		
**2). Report Server(JBoss)**		
a) 把下载好的jdbcdslog.jar(e.g. jdbcdslog-1.0.6.2.jar) 放到 jboss_xxx\server\rs\lib 下;	 	
b) 修改 jboss_xxx\server\rs\deploy\oracle-xa-ds.xml 的 datasource，按照下面规则全部替换就可以了 
``` xml
<xa-datasource-class>oracle.jdbc.xa.client.OracleXADataSource</xa-datasource-class> 
==>  
<xa-datasource-class>org.jdbcdslog.ConnectionPoolXADataSourceProxy</xa-datasource-class>

<xa-datasource-property name="URL">jdbc:oracle:thin:@10.100.53.85:1521:cmn</xa-datasource-property>
==> 
<xa-datasource-property name="URL">jdbc:oracle:thin:@10.100.53.85:1521:cmn?targetDS=oracle.jdbc.xa.client.OracleXADataSource</xa-datasource-property>
```
c) 配置log4j，可以记录jdbcdslog 的 log，修改 jboss_xxx\server\rs\conf\log4j.xml，添加
``` xml
  <!-- logger: jdbcdslog -->
  <logger name="org.jdbcdslog.StatementLogger">
    <level value="INFO"/>
	<appender-ref ref="RS_FILE"/>
  </logger>
  
  <logger name="org.jdbcdslog.SlowQueryLogger" additivity="false">
    <level value="INFO"/>
  </logger>  
  
  <logger name="org.jdbcdslog.ConnectionLogger" additivity="false">
    <level value="INFO"/>
  </logger>

  
  <logger name="org.jdbcdslog.ResultSetLogger" additivity="false">
    <level value="INFO"/>
  </logger>  
```

具体的效果，请看我如何操作：		
{% img /images/first-last-day/jdbcdslog-exp.gif 800 600 'jdbcdslog-exp' 'jdbcdslog-exp' %}	

#### 2.2 TableDataCopier		
TableDataCopier 是copy某个数据库中某个表的数据到另一个数据库中（存在同样的表结构），e.g. 能够copy SIT DB 的 mc_instr 到 Local DB 的 mc_instr。具体的可以看看它在google code的主页：<http://code.google.com/p/table-data-copier/>

这个是以前做 OG Capture 的时候用的，主要是想从SIT搬一些数据过来，后面就做了一个统一点、有界面的程序，方便使用。现在看看，好像也没有多大的用途，不过还是分享出来，也许大家以后用得着。你也可以通过SVN Checkout下来代码	
```
svn checkout http://table-data-copier.googlecode.com/svn/trunk/
```
也贴个运行图（仅支持 jdk1.6+）：		
{% img /images/first-last-day/table-data-copier.gif 800 600 'TableDataCopier' 'TableDataCopier' %}	

### 三、SoapUI		
关于SoapUI的简单使用，大家都很清楚，这里就不说了，下面说的是一些不常见的用法，再加一些性能测试方面的经验（这部分好早好早以前Lilian就有想法叫我讲的，现在只能靠这个机会弥补下了）。
#### 3.1 File Import
MSSE 中还是有很多 File Import（Upload）类的Functions的，如果要用SoapUI做INT的话，免不了要处理 byte[] 类型的参数，那么如何通过SoapUI传递byte\[](File)的参数了，实际上，也很简单，看图就很清楚了（如果看不清，麻烦请另存后再看大图，全篇文章通用）。			
{% img /images/first-last-day/screenshot-20120327104943.png 562.5 514.5 'File Import' 'File Import'%}

#### 3.2 Property Transfer
Property Transfer 就是在SoapUI不同的Test Steps之间传递数据，具体的可以看看文档 [Transferring Property Values](http://www.soapui.org/Functional-Testing/transfering-property-values.html)。通过下面的实例图你就创建了一个Property Transfer并添加了一个Transfer Value，而每个Transfer Value都有两个部分，一个是Source，一个是Target，都可以选择不同的Step和不同的Property。		
{% img /images/first-last-day/screenshot-20120328181006.png %}			
如何配置这些内容，请看一个例子。e.g.				
			
在Mic2中，send msg step 需要一个参数，而这个参数是 login step 成功后的response，那么这种情况就可以这样：		
{% img /images/first-last-day/screenshot-20120328173450.png 602 436 %}		
贴上要写的配置信息：
``` xml Source
declare namespace ns1='http://mic2.taifook.com/';
//ns1:loginResponse/return
```
``` xml Target
declare namespace mic2='http://mic2.taifook.com/';
//mic2:send/arg0
```

#### 3.3 Groovy
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
这个就是顺序并轮流从一个长度为10的字符串数组里取一个元素（第一行有些错误，一开始得不到 count's PropertyValue ,你可以用类似第三行先 set count = 0 后删除，或者手动添加 count PropertyValue，如何添加？请看共享文档），这个可以用来做一些性能测试的一些数据输入（可以做到尽量不同），用法和上面一样
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

#### 3.4 Load Tests		 
[Load Tests](http://www.soapui.org/Getting-Started/load-testing.html) 就是使SoapUI连续多次执行一个Test Steps，很简单就可以创建了，在【Load Tests】选项【New LoadTest】就可以了，大概会是下面的样子，具体的测试方法你就可以亲自试试了（比如Limit,Threads等配置）。		
{% img /images/first-last-day/screenshot-20120328181558.png %}			
	
共享中有一份MIC2 SoapUI User Manual.doc的文档，是前面测试Mic2性能时候写的，加上MIC2-soapui-project.xml 和 MIC2 SoapUI Test.eml(email)，应该就包括了上面的所有SoapUI知识了。

### 四、Eclipse常用插件
#### 4.1 Easy Explore
[Easy Explore](http://sourceforge.net/projects/easystruts/) 顾名思义，就是使在 Eclipse 里面浏览本地文件变的很简单。官方的插件有些些不足，不过还好，网上有人已经修改了，请看 [改造easyexplore插件](http://www.blogjava.net/terrine/archive/2008/12/29/121555.html) ，可以下载里面的plugin并安装，重启即可使用。

#### 4.2 KeepResident	 
[KeepResident](http://suif.stanford.edu/pub/keepresident/index.html) 是一款改进Eclipse性能的插件，个人感觉还是不错的，大家可以试用下。我的配置如下：		
{% img /images/first-last-day/screenshot-20120326161418.png 458 360 %}

#### 4.3 quickmarks
[quickmarks](http://eclipse-tools.sourceforge.net/quickmarks/index.html) 是Eclipse中的一款书签插件，很简单实用，追踪代码非常有帮助。使用超简单的， Ctrl+Shift+[0-9] 加上或者取消书签， Alt+[0-9] 打开对应的书签。

#### 4.4 eclipse-fullscreen
[eclipse-fullscreen](http://code.google.com/p/eclipse-fullscreen/) 是一款能够让eclipse全屏的小插件，会让你的代码编辑窗口变得最大，充满整个屏幕。(小屏幕有福了，我留着备用）。

### 五、Eclipse最常用快捷键
以前也整理过一次（[Eclipse/MyEclipse最最常用的快捷键](http://www.blogjava.net/lishunli/archive/2010/01/07/308612.html)），现在重新整理一次，尽量保持最简单最实用。子曰：工欲善其事，必先利其器。

<table>
	<tr><td>快捷键</td><td>描述</td></tr>
	<tr><td>Alt+/</td><td>代码提示</td></tr>
	<tr><td>Ctrl+D</td><td>删除当前行</td></tr>
	<tr><td>Ctrl+Shift+O</td><td>作用是缺少的Import语句被加入，多余的Import语句被删除</td></tr>
	<tr><td>Ctrl+1</td><td>快速修复(最经典的快捷键,就不用多说了)</td></tr>
	<tr><td>Ctrl+Shift+F</td><td>格式化当前代码</td></tr>
	<tr><td>Ctrl+Shift+C</td><td>在代码窗口中是这种注释（再次按下，取消注释）</td></tr>
	<tr><td>Ctrl+F</td><td>查找/替换（这个谁都知道）</td></tr>
	<tr><td>Ctrl+Shift+T</td><td>打开类型（查找文件很方便）</td></tr>
	<tr><td>Ctrl+Shift+R</td><td>打开资源，和Ctrl+Shift+T不同的是，T是打开Java类文件，而R是所有文件</td></tr>
	<tr><td>F2</td><td>显示详细信息</td></tr>
	<tr><td>F3</td><td>跳到声明或定义的地方（这个我现在一般都使用Ctrl+鼠标左击了）</td></tr>
	<tr><td>Ctrl+T</td><td>快速显示当前类的继承结构</td></tr>
	<tr><td>Ctrl+/</td><td>在代码窗口中是这种注释（推荐使用Ctrl +Shift + C注释）</td></tr>
	<tr><td>Ctrl+Shift + / </td><td>块注释，不推荐使用，格式化后如果再想取消就比较麻烦了</td></tr>
	<tr><td>Ctrl+Q</td><td>定位到最后编辑的地方</td></tr>
	<tr><td>Ctrl[+Shift]+K</td><td>参照选中的Word快速定位到下[上]一个</td></tr>
	<tr><td>Alt+[↓ or ↑]</td><td>当前行和下/上面一行交互位置(特别实用,可以省去先剪切,再粘贴了)</td></tr>
	<tr><td>Ctrl+Alt+[↓ or ↑]</td><td>复制当前行到下/上一行(复制增加)</td></tr>
	<tr><td>Ctrl+Shift+X</td><td>把当前选中的文本全部变为大写</td></tr>
	<tr><td>Ctrl+Shift+Y</td><td>把当前选中的文本全部变为小写</td></tr>
	<tr><td>Alt+Shift+R</td><td>重命名(当然，F2也能有一样的作用)</td></tr>
	<tr><td>Alt+Shift+M</td><td>抽取方法 (这是重构里面最常用的方法之一了)</td></tr>
	<tr><td>Alt+Shift+L</td><td>抽取本地变量</td></tr>
</table>			

### 六、我的联系方式：		
Tel: 13724388694	
QQ: 506817493		
E-mail: <leeshunli@qq.com> , <lishunli.me@gmail.com>	
MSN: <lishunli@live.com>	
Weibo: [@李顺利Me](http://weibo.com/lishunli)	
Twitter: [@lishunli](http://twitter.com/lishunli)	
Facebook: [李顺利](http://www.facebook.com/lishunli)		
Blog：<http://blogjava.net/lishunli> , <http://lishunli.github.com/>			
（够详细吧，找到我，很简单！）

**内部分享，谨防泄密，**	
**版权所有，翻版必究。**	
**(It's a joke, you can do anything you want to do!)**

*B.T.W 这是我的第一次，借用某游戏中的一句话：我要走了，下次再玩吧！*		

<p align="right">
顺利<br>		
2012年3月30日
</p>

[jdbcdslog-exp]: (http://code.google.com/p/jdbcdslog-exp/) "jdbcdslog-exp"
[jdbcdslog]: (http://code.google.com/p/jdbcdslog/) "jdbcdslog"