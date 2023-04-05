package ylab.lesson5.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.stereotype.Component;

@Component
public class InputRabbitClientImpl implements InputRabbitClient {
    private static final String QUEUE_NAME = "input";
    private final ConnectionFactory connectionFactory;
    private final MessageFilter messageFilter;

    public InputRabbitClientImpl(ConnectionFactory connectionFactory, MessageFilter messageFilter) {
        this.connectionFactory = connectionFactory;
        this.messageFilter = messageFilter;
    }

    @Override
    public void inputMessage() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_NAME, true);
                if (message != null) {
                    messageFilter.filtering(new String(message.getBody()));
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка подключения к RabbitMQ");
        }
    }
}

