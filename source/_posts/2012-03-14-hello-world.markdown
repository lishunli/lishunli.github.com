---
layout: post
title: "Hello World"
author: lishunli
date: 2012-03-14 21:33
comments: true
categories: others
---
程序员的第一篇博客，一般都是Hello World，我也习俗下。

## 以下用几种方式(Octopress)来贴HelloWorld的代码
<!-- more -->
* Backtick Code Blocks
``` java HelloWorld.java
/**
 *
 * @author ShunLi
 */
	public class HelloWorld {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
```

* Include Code Snippets
{% include_code helloworld/HelloWorld.java %}

* Gist Embedding
{% gist 2036900 %}

* Inline Code Blocks
{% codeblock lang:java %}
/**
 *
 * @author ShunLi
 */
	public class HelloWorld {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
{% endcodeblock %}
B.T.W. 我以后应该比较常用Backtick code blocks 和 Include code sinipets.

## P.S. 悲催地体验——在Windows下安装Octopress
在Windows下体验Ruby，真的是一件很痛苦的事情，有打算迁移到Ubuntu环境了。
这里记录下载Windows下安装Octopress的步骤，有些真是很痛苦。

* 安装 ruby	
如果你不怕麻烦的话，可以选择装ruby+Devkit(Devkit也要安装不然在bundle install这步附近应该会有问题)，Windows下安装ruby可以通过RubyInstaller(不过好像经常需要翻Wall过去才能打开)，更方便地方法是直接通过[railsinstaller](http://railsinstaller.org/) 来安装，里面包括很多需要的工具。

* 安装 python	
这个真的需要，ruby核心部分有用到。如果没有安装Python的话，在Octopress的代码高亮(include_code 等)部分可能会出现问题：	
比如：
``` ruby
Liquid error: No such file or directory - python -c “import sys; print sys.executable”
# or
Liquid error: undefined method `Py_IsInitialized’ for RubyPython::Python:Module
```	
请先安装 python-xxx.msi，然后把python home（e.g. C:\Python27）加到windows环境变量下	
请注意，一定要把python home加到windows环境变量下。

如果还不行的话，请参考这个[issue](https://github.com/imathis/octopress/issues/262)，应该能够解决。
	

* 安装octopress	
这个官网已经有介绍了，应该比较简单。

* 中文问题	
Windows下如果有中文的话，在generate步骤就会失败，网上给出的解决方法也很简单，就是设置自己本机的环境变量		
![](http://usc.googlecode.com/svn/files/github/images/hello-world/env_vars.png)	
具体的就是这样设置的
```
set LC_ALL=zh_CN.UTF-8
set LANG=zh_CN.UTF-8
```

* 还有一个问题	
对有序序列支持不好，我已经提了一个[bug](https://github.com/imathis/octopress/issues/488)了。

* 就请欢乐地拥抱Octopress/Jekyll吧。

## 后记
Octopress 重装记

1. 参考[本文](http://lishunli.github.com/blog/2012/03/14/hello-world/) ，安装ROR，Python并配置好环境配置（中文编码、python）
2. git clone git@github.com:lishunli/lishunli.github.com.git (change it by yourself)
3. git checkout source
4. bundle install or bundle update
5. rake setup_github_pages
6. rake generate, rake preview, rake deploy
7. rake new_post["title"]		 新建的文章默认是ANSI编码的，这会导致generate步骤失败，请转换为UTF-8格式的文件后重新来过，如果还有问题，尝试其它的解决办法。

顺利更新于2012年12月17日
