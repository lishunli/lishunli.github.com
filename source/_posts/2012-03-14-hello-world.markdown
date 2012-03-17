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
### Backtick Code Blocks
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

### Include Code Snippets
{% include_code helloworld/HelloWorld.java %}

### Gist Embedding
{% gist 2036900 %}

### Inline Code Blocks
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

1. 安装 ruby

	如果你不怕麻烦的话，可以选择装ruby+Devkit，更方便地方法是windows下安装ruby可以通过ruby installer，不过好像经常需要翻Wall过去才能打开， kit也要安装不然在bundle install 这步附近应该会有问题）

2. 安装 python

	这个真的需要，ruby中有用到，
	如果不行的话，请按照这个顺序来一步一步检查是否ok
	安装 python-xxx.msi -> 把python home（e.g. C:\Python27）加到windows环境变量下 -> gem update
	请注意，一定要把python home（e.g. C:\Python27）加到windows环境变量下。
	pywin32-xxx.exe ->
	还有，如果没有python的话，朋友们可能会碰到下面的问题，在Octopress的代码高亮部分（）
	不然在上面的代码高亮include_code部分会有错误出现，

3. 安装octopress
	
	这个官网已经有介绍了，上面都完美做了话，应该比较简单。

4. 中文问题

	设置环境变量

		set LC_ALL=zh_CN.UTF-8
		set LANG=zh_CN.UTF-8

5. 就请欢乐得拥抱Octopress/Jekyll吧。

还有一个问题
对有序序列支持不好，比如上面应该是5的，现在变成了1。

Liquid error: No such file or directory - python -c “import sys; print sys.executable”

%PYTHON_HOME%
http://railsinstaller.org/
http://ruby-taiwan.org/topics/46


http://blog.yesmryang.net/windows-octopress-python/
https://github.com/imathis/octopress/issues/262

[顺利](http://blogjava.net/lishunli)
