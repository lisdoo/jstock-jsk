package com.goldpac.instantissue.launcher;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;

public class MqFactory {

    public static Map<String, RabbitTemplate> templates = new HashMap<>();
    static CachingConnectionFactory connectionFactory;

    public MqFactory() {
    }

    public static synchronized RabbitTemplate get(String code) {

        if (MqFactory.connectionFactory == null) {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory("ali47");
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("lenovo.112");
            MqFactory.connectionFactory = connectionFactory;
        }

        if (!templates.containsKey(code)) {
            RabbitAdmin ra = new RabbitAdmin(connectionFactory);
            Queue queue = new Queue(code);
            ra.declareQueue(queue);

            RabbitTemplate template = new RabbitTemplate(connectionFactory);
            template.setRoutingKey(queue.getName());
            template.setDefaultReceiveQueue(queue.getName());

            templates.put(queue.getName(), template);

            return template;
        } else {
            return MqFactory.templates.get(code);
        }
    }

    public static synchronized void release() {

        for (RabbitTemplate template: templates.values()) {
            template.stop();
        }
        if (connectionFactory != null) {
            connectionFactory.destroy();
        }
        templates.clear();
        connectionFactory = null;
    }
}
