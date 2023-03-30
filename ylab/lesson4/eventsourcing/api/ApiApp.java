package ylab.lesson4.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import ylab.lesson4.DbUtil;
import ylab.lesson4.RabbitMQUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        DataSource dataSource = getDataSource();
        PersonApiImpl personApi = new PersonApiImpl(connectionFactory, dataSource);
        System.out.println(personApi.findAll());
        personApi.savePerson(1L, "firstName1", "lastName1", "middleName1");
        personApi.savePerson(2L, "firstName2", "lastName2", "middleName2");
        personApi.savePerson(3L, "firstName3", "lastName3", "middleName3");
        System.out.println(personApi.findAll());
        personApi.deletePerson(1L);
        personApi.deletePerson(1L);
        personApi.savePerson(2L, "new_firstName2", "new_lastName2", "new_middleName2");
        System.out.println(personApi.findPerson(3L));
        System.out.println(personApi.findPerson(4L));
        System.out.println(personApi.findAll());
    }

    private static ConnectionFactory initMQ() throws IOException, TimeoutException {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource getDataSource() throws SQLException {
        return DbUtil.buildDataSource();
    }
}
