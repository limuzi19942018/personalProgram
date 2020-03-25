package com.example.demo_activity.test1.config;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yongl
 * @DATE: 2020/3/17 15:53
 */

@Configuration
public class RabbitConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    private ConnectionFactory connectionFactory;
     final  String queueName="explain_1_111_222_213_dkkekkkoto788";


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
         RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    /*
    * 使用@Bean注解是让项目启动后，加载该类下面的此方法，生成一个名为helloTest的队列
    * */
   @Bean
   public Queue decalreQueue(){
       LOGGER.info("已经声明了一个队列:{}",queueName);
       return new Queue(queueName,true);
   }
}
