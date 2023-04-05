package ylab.lesson5.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.stereotype.Component;

@Component
public class RabbitClient {
    private static final String QUEUE_NAME = "queue";
    private final ConnectionFactory connectionFactory;

    public RabbitClient(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getMessage() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // Строка избыточна, если запускать ApiApp первым первый раз.
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            GetResponse message = channel.basicGet(QUEUE_NAME, true);
            if (message != null) {
                return new String(message.getBody());
            }
        } catch (Exception e) {
            System.out.println("Ошибка подключения к RabbitMQ");
        }
        return "";
    }

}
