package com.example.rmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class MyRestController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private Receiver receiver;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        System.out.println("Sending message...");
        //convertAndSend calls method channel.basicPublish of amqp lib
        rabbitTemplate.convertAndSend(RmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return "test success";
    }
}
