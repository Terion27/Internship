package ylab.lesson5.messagefilter;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

@Component
public class DbClient {
    private final DataSource dataSource;
    private static final String TABLE_NAME = "censor_table";

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean findByWord(StringBuilder strBuilder) {
        boolean res = false;
        String sqlSelect = "select word from " + TABLE_NAME + " where LOWER(word) = LOWER(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlSelect)) {
            preStat.setString(1, strBuilder.toString());
            ResultSet resultSql = preStat.executeQuery();
            res = resultSql.next();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
        return res;
    }

    public void initTable() {
        String pathFile = "src/main/java/io/ylab/intensive/lesson05/messagefilter/file.txt";
        String sql = "insert into " + TABLE_NAME + " (word) values (?)";
        try (FileInputStream fStream = new FileInputStream(pathFile);
             Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             Scanner scan = new Scanner(fStream)) {
            if (findTable(dataSource)) {
                clearTable(dataSource);
            } else {
                createTable(dataSource);
            }

            while (scan.hasNextLine()) {
                ps.setString(1, scan.nextLine());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        } catch (IOException e) {
            System.out.println("Ошибка открытия файла");
        }
    }

    private boolean findTable(DataSource dataSource) {
        boolean key = false;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rsTable = metaData.getTables(null, null, TABLE_NAME, null);
            key = rsTable.next();

        } catch (SQLException e) {
            System.out.println("Ошибка поиска таблицы");
        }
        return key;
    }

    private void createTable(DataSource dataSource) {
        String ddl = ""
                + "create table " + TABLE_NAME + " ("
                + "id bigserial NOT NULL, "
                + "word varchar, "
                + "CONSTRAINT " + TABLE_NAME + "_pkey PRIMARY KEY(id))";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы");
        }
    }

    private void clearTable(DataSource dataSource) {
        String query = "TRUNCATE " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы");
        }
    }
}
