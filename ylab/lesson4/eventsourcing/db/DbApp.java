package ylab.lesson4.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import ylab.lesson4.DbUtil;
import ylab.lesson4.RabbitMQUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DbApp {
    public static void main(String[] args) throws Exception {  // По условию, не обрабатывать исключение.
        String queueName = "queue";
        DataSource dataSource = initDb();

        ConnectionFactory connectionFactory = initMQ();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // Строка избыточна, если запускать ApiApp первым первый раз.
            channel.queueDeclare(queueName, true, false, false, null);
            // -----
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
                if (message != null) {
                    messageProcessing(new String(message.getBody()), dataSource);
                }
            }
        }
    }

    private static void messageProcessing(String message, DataSource dataSource) {
        DbImpl dbImpl = new DbImpl();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(message, Map.class);
            String operation = map.get("operation");
            if (operation.equals("savePerson")) {
                dbImpl.savePerson(map, dataSource);
            } else if (operation.equals("deletePerson")) {
                dbImpl.deletePerson(map, dataSource);
            } else {
                System.out.println("Неизвестная операция");
            }
        } catch (Exception e) {
            System.out.println("Ошибка, переданные данные некорректны");
        }
    }

    private static ConnectionFactory initMQ() throws IOException, TimeoutException {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = """
                  drop table if exists person;
                  create table if not exists person (
                    person_id bigint primary key,
                    first_name varchar,
                    last_name varchar,
                    middle_name varchar
                  )
                """;

        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
