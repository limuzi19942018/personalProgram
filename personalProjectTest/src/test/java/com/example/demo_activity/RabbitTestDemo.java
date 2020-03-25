package com.example.demo_activity;

import com.example.demo_activity.test1.utils.RabbitUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: yongl
 * @DATE: 2020/3/17 16:47
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoActivityApplication.class)
@WebAppConfiguration
public class RabbitTestDemo {
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitTestDemo.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private  RabbitUtils rabbitUtils;


    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("helloTest5","你好中国china3355");
        LOGGER.info("已经发送了!!!!!");
    }

    @Test
    public void testMethod(){
        //声明topic模式交换机
        //rabbitAdmin.declareExchange(new TopicExchange("topic-test",true,false));
        //声明队列名称
        //String queue = rabbitAdmin.declareQueue(new Queue("liyong1", true));
        //声明路由
        TopicExchange topicExchange = new TopicExchange("topic-test", true, false);
        rabbitAdmin.declareExchange(topicExchange);
        Queue queue = new Queue("explain_1_111_222_213_dkkekkkoto788", true);
        rabbitAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with("111_222_1");
        rabbitAdmin.declareBinding(binding);
        //发送消息
        rabbitTemplate.convertAndSend("explain_1_111_222_213_dkkekkkoto788","我是测试11");
        LOGGER.info("我是1，我已经发送了11111");
        rabbitTemplate.convertAndSend("explain_1_111_222_213_dkkekkkoto788","我是测试22");
        rabbitTemplate.convertAndSend("explain_1_111_222_213_dkkekkkoto788","我是测试33");
        //Object obj = rabbitTemplate.convertSendAndReceive("explain_1_111_222_213_dkkekkkoto788", "我是测试44");
        //LOGGER.info("返回的东西是:{}",obj.toString());
    }

    @RabbitListener(queues="explain_1_111_222_213_dkkekkkoto788")
    @RabbitHandler
    public void receive(String msg){
        //topic模式下，一对多，个人理解是：每个人都有一个路由，绑定在同一个队列上，然后每个人监听这个队列，那么所有人都能收到消息
        LOGGER.info("我接收到的消息是:{}",msg);
    }

}
