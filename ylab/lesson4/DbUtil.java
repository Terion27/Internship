package ylab.lesson4;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

  private static final String POSTGRES = "postgres";

  private DbUtil(){}
  
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
    dataSource.setUser(POSTGRES);
    dataSource.setPassword(POSTGRES);
    dataSource.setDatabaseName(POSTGRES);
    dataSource.setPortNumbers(new int[]{5432});
    dataSource.getConnection().close();
    return dataSource;
  }
}
