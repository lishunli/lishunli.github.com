---
layout: post
title: "Jdk1.5并发包学习1——1.5以前的线程相关知识"
date: 2012-03-17 16:18
comments: true
categories: [jdk, concurrent]
---

这篇(系列)文章，是在[《张孝祥-Java多线程与并发库高级应用》](http://edu.csdn.net/heima/video.html)学习中，自己的一些梳理和总结，虽然张孝祥老师先离开了我们，但是他留下来的东西，终生受用，在此缅怀一下张孝祥老师。
强烈建议先看看 《张孝祥-Java多线程与并发库高级应用》。 

## 创建线程的两种传统方式
* 在Thread子类覆盖的run方法中编写运行代码
* 在传递给Thread对象的Runnable对象的run方法中编写代码	

**总结：**		
查看Thread类的run()方法的源代码，可以看到其实这两种方式都是在调用Thread对象的run方法，如果Thread类的run方法没有被覆盖，并且为该Thread对象设置了一个Runnable对象，该run方法会调用Runnable对象的run方法。			

传统创建Thread有两种方法，一种是继承Thread类，一种是实现Runnable接口
一般使用的话，建议使用第二种方法，也就是实现Runnable接口优于继承 Thread
大致这样认为：		
1. Java 是单继承的，如果继承了Thread了，那么就丢失了灵活性	
2. 实现Runnable接口更适合于资源的共享，你可以看看上面的构造器，可以传递同一个 Runnable 的实现类。

**问题：**				
如果在Thread子类覆盖的run方法中编写了运行代码，也为Thread子类对象传递了一个Runnable对象，那么，线程运行时的执行代码是子类的run方法的代码？还是Runnable对象的run方法的代码？

{% include_code concurrent/src/main/java/org/usc/concurrent/TraditionalThread.java %}

## 定时器的应用
Java的定时器很简单，了解 Timer 和 TimerTask 类就可以的，更为复杂的可以关注下 [Quartz](http://quartz-scheduler.org/) 。

{% include_code concurrent/src/main/java/org/usc/concurrent/TraditionalTimer.java %}

## 线程的同步互斥与通信

稍后会介绍 synchronized 和 lock 、volatile 的区别。
总之，要同步互斥的几段代码最好是分别放在几个独立的方法中，这些方法再放在同一个类中，这样比较容易实现它们之间的同步互斥和通信。


## 多个线程访问共享对象和数据的方式
* 如果每个线程执行的代码相同，可以使用同一个Runnable对象，这个Runnable对象中有那个共享数据
	例如，买票系统就可以这么做。
* 如果每个线程执行的代码不同，这时候需要用不同的Runnable对象，有如下两种方式来实现这些Runnable对象之间的数据共享：

1. 将共享数据封装在另外一个对象中，然后将这个对象逐一传递给各个Runnable对象。每个线程对共享数据的操作方法也分配到那个对象身上去完成，这样容易实现针对该数据进行的各个操作的互斥和通信。	

2. 将这些Runnable对象作为某一个类中的内部类，共享数据作为这个外部类中的成员变量，每个线程对共享数据的操作方法也分配给外部类，以便实现对共享数据进行的各个操作的互斥和通信，作为内部类的各个Runnable对象调用外部类的这些方法。
(极端且简单的方式，即在任意一个类中定义一个static的变量，这将被所有线程共享。)

## 实现线程范围的共享变量