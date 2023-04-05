package ylab.lesson5.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RabbitClient {
    private final ConnectionFactory connectionFactory;

    public RabbitClient(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void postMessage(String message) {
        String exchangeName = "excName";
        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");
            channel.basicPublish(exchangeName, "*", null, message.getBytes());

        } catch (Exception e) {
            System.out.println("Сообщение не отправлено!");
        }
    }
}
