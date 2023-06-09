package ylab.lesson5.sqlquerybuilder;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SQLQueryExtenderTest {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        SQLQueryBuilder queryBuilder = applicationContext.getBean(SQLQueryBuilder.class);
        List<String> tables = queryBuilder.getTables();
        System.out.println(queryBuilder.queryForTable("missing_table"));
        System.out.println();
        // вот так сгенерируем запросы для всех таблиц что есть в БД
        for (String tableName : tables) {
            System.out.println(tableName);
            System.out.println(queryBuilder.queryForTable(tableName));
            System.out.println();
        }
    }
}
