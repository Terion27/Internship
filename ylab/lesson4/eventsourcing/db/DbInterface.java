package ylab.lesson4.eventsourcing.db;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public interface DbInterface {
    void savePerson(Map<String, String> map, DataSource dataSource);

    void deletePerson(Map<String, String> map, DataSource dataSource);

    void insertPerson(Map<String, String> map, DataSource dataSource) throws SQLException;

    void updatePerson(Map<String, String> map, DataSource dataSource) throws SQLException;

    boolean getPersonById(long personId, DataSource dataSource) throws SQLException;
}
