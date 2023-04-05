package ylab.lesson5;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

public class DbUtil {

  private DbUtil() {
  }

  public static void applyDdl(String ddl, DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement()) {
      statement.execute(ddl);
    }
  }

  /*
   * Настройки подключения НЕ МЕНЯЕМ!
   * Надо настроить БД таким образом, чтобы она работала со следующими 
   * настройками
   */
  public static DataSource buildDataSource() throws SQLException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerNames(new String[]{"localhost"});
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumbers(new int[]{5432});
    dataSource.getConnection().close();
    return dataSource;
  }
}
