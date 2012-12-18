---
layout: post
title: "Spring + Weblogic 下的JMS使用"
date: 2012-12-16 13:59
comments: true
categories: Tips
---

本篇文章没有做过多整理，是以前自己写的一个小笔记，希望有缘的你读过有些收获
	
1). weblogic 配置 JMS步骤	
1.1 创建 JMS 服务器		
1.2 创建持久性存储		
1.3 创建 JMS 模块		
<!-- more -->		
下图来自于Weblogic成功配置JMS后的截图，具体的配置过程网上很多，请参考完成。			
{% img /images/jms-usage-in-spring-and-weblogic/jsm-in-weblogic.png %}

2). Spring 配置 applicationContext.xml

``` xml applicationContext.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:jee="http://www.springframework.org/schema/jee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
     http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-2.0.xsd">
 
    <!-- Use Weblogic JMS -->
    <jee:jndi-lookup id="batch.jmsFactory" jndi-name="jms/batch/connectionFactory">
       <jee:environment>
           java.naming.factory.initial=weblogic.jndi.WLInitialContextFactory
           java.naming.provider.url=t3://127.0.0.1:7001
       </jee:environment>
    </jee:jndi-lookup>
    <jee:jndi-lookup id="batch.reqQueue.destination" jndi-name="jms/batch/reqQueue">
       <jee:environment>
           java.naming.factory.initial=weblogic.jndi.WLInitialContextFactory
           java.naming.provider.url=t3://127.0.0.1:7001
       </jee:environment>
    </jee:jndi-lookup>
 
    <!-- JmsTemplate -->
    <bean id="jmsBatchQueueTemplate" class="org.springframework.jms.core.JmsTemplate">
       <property name="pubSubDomain" value="false" />
       <property name="connectionFactory" ref="batch.jmsFactory" />
       <property name="defaultDestination" ref="batch.reqQueue.destination" />
    </bean>
 
    <bean id="batchSenderAndReceiver" class="org.usc.jms.SenderAndReceiver">
       <property name="jmsTemplate" ref="jmsBatchQueueTemplate" />
    </bean>
 
    <bean id="batchReceiverQueueListenerContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">
       <property name="concurrentConsumers" value="1" />
       <property name="connectionFactory" ref="batch.jmsFactory" />
       <property name="destination" ref="batch.reqQueue.destination" />
       <property name="messageListener" ref="batchSenderAndReceiver" />
    </bean>
 
</beans>
```
	
3). 测试代码
``` java SenderAndReceiver.java
package org.usc.jms;
 
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
 
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author ShunLi
 */
public class SenderAndReceiver implements MessageListener{
    private JmsTemplate jmsTemplate;
 
    public JmsTemplate getJmsTemplate() {
       return jmsTemplate;
    }
 
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
       this.jmsTemplate = jmsTemplate;
    }
 
    public void sendMessage() {
       jmsTemplate.convertAndSend("Hello world!(" + System.currentTimeMillis() + ")");
    }
 
    public void onMessage(Message msg) {
       try {
           System.out.println("msg is "+((TextMessage)msg).getText());
       } catch (JMSException e) {
           e.printStackTrace();
       }
    }
}
 
 
    public static void main(String[] args) {
       ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       SenderAndReceiver jmsQueueTemplate = (SenderAndReceiver) ctx.getBean("batchSenderAndReceiver");
       jmsQueueTemplate.sendMessage();
    }

```

4). 注意点	
4.1. java.naming.factory.initial=weblogic.jndi.WLInitialContextFactory 需要在classpath 下面加上 weblogic.jar		
4.2. SenderAndReceiver 即是Sender 又是 Listener（Recevier）			
4.3. 大致处理流程是这样的	
	Sender send msg -> Queue/Topic -> 触发Listener（异步），Listener onMessage 处理 收到的 Message。	
p.s. 测试代码不太优美，SenderAndReceiver 既作为了一个消息发布者，又作为了消息接收者，实践中，尽量分开			
		
5). 补充知识		
传递域：点对点（PTP）消息传递域和发布/订阅消息传递域。			
点对点消息传递域的特点如下：	
	每个消息只能有一个消费者。
	消息的生产者和消费者之间没有时间上的相关性。无论消费者在生产者发送消息的时候是否处于运行状态，它都可以提取消息。
发布/订阅消息传递域的特点如下：	
	每个消息可以有多个消费者。
	生产者和消费者之间有时间上的相关性。订阅一个主题的消费者只能消费自它订阅之后发布的消息。
	
JMS规范允许客户创建持久订阅，这在一定程度上放松了时间上的相关性要求。持久订阅允许消费者消费它在未处于激活状态时发送的消息。