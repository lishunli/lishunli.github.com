---
layout: post
title: "GitHub多账户使用"
date: 2012-03-18 13:43
comments: true
categories: [github, ssh]
---

在[github]托管了一些项目，而且自己创建的几个github账号， 比如有 lishunli, jdbcdslog 等。 github 使用ssh进行验证连接，但是如果你本地创建一个ssh key的话，等你切换到另一个账号的话，添加ssh key，就会有“SSH 已经被使用”的出错信息，当然这个时候想到的就是，使用多个SSH Key，那么如何能够让Github知道你使用的多个key了（Github 默认只会找 id_rsa 的 key）。
当然解决的办法也很简单，就是配置 ssh 的 config， 具体请参考 [多个github帐号的SSH key切换](http://omiga.org/blog/archives/2269) ，这篇文章已经介绍了很详细了，我这里只说一些关键的地方：

* 不需要使用ssh-add命令来添加ssh keys，我本机测试过，生成很多keys，只要config配置正确，都ok
* config 的配置关键点在于： 
```	ruby
Host github.cn
  HostName github.com
```
这里Host的怎么配置，你github的repo ssh 连接url就要相应的修改成这样，比如如上面的配置，原连接地址是 
```
git@github.com:lishunli/GitHub-Multi-Accounts.git
```
那么根据上面的配置，就要把github.com 修改成github.cn, 那么ssh解析的时候就会自动的把github.cn 转换为 github.com，这样地址就一样了。修改后就是
```
git@github.cn:lishunli/GitHub-Multi-Accounts.git
```
这样的配置，类似hosts，当然你可以任意配置上面的Host，例如可以这样
```	
Host lishunli.github.com
  HostName github.com
```		  
那么 你git clone 或者 git remote add origin 后面就应该类似这样
```		
git@lishunli.github.com:YourAcccountName/YourRepoName.git
# 原始是
git@github.com:YourAcccountName/YourRepoName.git
```
是不是很简单，记得把原HostName变换成你配置的Host就可以了。<br>搜索的时候，经常受到的是官方帮助文档[Manage multiple clients](http://help.github.com/manage-multiple-clients/), 说是创建Org，但是据我所知，并不能很好的解决多账户问题（可能本人所知有限，并没有很好地体会Org的作用），想要了解更多 github org，请参看<br>[如何在 GitHub 建立组织](http://forcefront.com/tag/organization/)<br>
[组织和团队](http://www.worldhello.net/gotgithub/04-work-with-others/030-organization.html)

附本人的ssh config 文件
{% include_code lang:ruby github-multi-accounts/config %}

无图无真相<br>
![hello lishunli](/images/github-multi-accounts/infos.png)

这篇文章没有点点技术,主要用于以下用途：

* 仅记之；
* 学习并使用Markdown，简单快捷高效地写文章(本文用markdown编辑而成),你可以通过这里观看效果 [GitHub多账户使用](http://lishunli.github.com/blog/2012/03/18/github-multi-accounts/)；
* 学习git，学习使用[Octopress](http://octopress.org/)。

[github]: https://github.com/
[顺利]: http://www.blogjava.net/lishunli/