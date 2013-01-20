---
layout: post
title: "在Octopress中为markdown的超链接加上target=&quot;_blank&quot;"
date: 2013-01-20 21:42
comments: true
categories: Tips
---
Markdown 支持两种形式的链接语法： 行内 和 参考 两种形式，两种都是使用角括号来把文字转成连结。而个人觉得大部分情况下还是使用行内的形式。
<!-- more -->
	
行内形式是直接在后面用括号直接接上链接：	
`This is an [example link](http://example.com/).`		
输出 HTML 为：		
`<p>This is an <a href="http://example.com/">example link</a>.</p>`
		
你也可以选择性地加上 title 属性：		
`This is an [example link](http://example.com/ "With a Title").`		
输出 HTML 为：		
`<p>This is an <a href="http://example.com/" title="With a Title">example link</a>.</p>`		
		
		
以上引自[Markdown 语法说明(简体中文版)](http://wowubuntu.com/markdown/basic.html)
	
	
但是你也看到了，生成的超链接默认是在本窗口打开的，为了有更好地阅读体验，我们往往是希望你在新窗口中打开超链接，而并不影响阅读本文。markdown目前应该还不支持这种语法的，当然markdown是支持html的，你可以直接使用`<a href="http://blogjava.net/lishunli" target="_blank">my blog</a>`来达到要求。	

使用markdown的原因是简洁，为了这个简单的需求而使用臃肿的html就有点得不偿失了，如果这样，还倒不如选择接受markdown这种默认的超链接形式。而jekyll/Octopress可以很自由地定制需要的功能，使 新窗口中打开链接 变得很容易。
	
	
Octopress的Issues [Open links in a new window](https://github.com/imathis/octopress/issues/410)就给出了比较完美的答案，简单hack a 标签。	
请在{YOUR_OCTOPRESS}\source\_includes\custom\head.html文件后面添加下面的代码 (YOUR_OCTOPRESS是你Octopress的主目录)
``` javascript
function addBlankTargetForLinks () {
  $('a[href^="http"]').each(function(){
		$(this).attr('target', '_blank');
	});
}
 
$(document).bind('DOMNodeInserted', function(event) {
	addBlankTargetForLinks();
});
```
代码来源 <https://gist.github.com/4523641>
	
	
朋友们，[test it, please click me](http://blogjava.net/lishunli)	
		
		
<p align="right">
<a href = "http://blogjava.net/lishunli" target="_blank">顺利</a><br>		
2013年1月20日
</p>
	