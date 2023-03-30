package ylab.lesson4.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ylab.lesson4.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
    private final ConnectionFactory connectionFactory;
    private final DataSource dataSource;

    public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        String message = getStringDelete(personId);
        postMessage(message);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        String message = getStringSave(personId, firstName, lastName, middleName);
        postMessage(message);
    }

    @Override
    public Person findPerson(Long personId) {
        String sqlFind = "select * from person where person_id = ?";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFind)) {
            preparedStatement.setLong(1, personId);
            ResultSet rSql = preparedStatement.executeQuery();
            if (rSql.next()) {
                return new Person(rSql.getLong("person_id"), rSql.getString("first_name"),
                        rSql.getString("last_name"), rSql.getString("middle_name"));
            }
        } catch (SQLException e) {
            System.out.println("findPerson - " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Person> findAll() {
        String sqlFind = "select * from person";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFind)) {
            ResultSet rSql = preparedStatement.executeQuery();
            List<Person> list = new ArrayList<>();
            while (rSql.next()) {
                list.add(new Person(rSql.getLong("person_id"), rSql.getString("first_name"),
                        rSql.getString("last_name"), rSql.getString("middle_name")));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("findAll - " + e.getMessage());
        }

        return Collections.emptyList();
    }

    private String getStringSave(Long personId, String firstName, String lastName, String middleName) {
        return "{\"operation\":\"savePerson\"," +
                "\"personId\":\"" + personId + "\"," +
                "\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"middleName\":\"" + middleName + "\"}";
    }

    private String getStringDelete(Long personId) {
        return "{\"operation\":\"deletePerson\"," +
                "\"personId\":\"" + personId + "\"}";
    }

    private void postMessage(String message) {
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
