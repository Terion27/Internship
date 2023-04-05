package ylab.lesson5.messagefilter;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class OutputRabbitClientImpl implements OutputRabbitClient {
    private final ConnectionFactory connectionFactory;

    public OutputRabbitClientImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void outputMessage(String message) {
        String exchangeName = "exchangeName";
        String queueName = "output";
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
