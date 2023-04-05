package ylab.lesson5.eventsourcing.api;

import ylab.lesson5.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DbClient implements DbInterface {
    private final DataSource dataSource;

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Person findPerson(long personId) {
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
}
