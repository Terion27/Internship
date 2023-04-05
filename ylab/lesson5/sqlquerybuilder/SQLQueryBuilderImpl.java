package ylab.lesson5.sqlquerybuilder;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private final DataSource dataSource;

    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rsTable = metaData.getTables(null, null, tableName, null);
            if (rsTable.next()) {
                ResultSet rsColumns = metaData.getColumns(null, null, tableName, null);
                StringBuilder query = new StringBuilder();
                boolean key = false;
                query.append("SELECT");
                while (rsColumns.next()) {
                    String col = (!key) ? " " + rsColumns.getString("COLUMN_NAME") :
                            ", " + rsColumns.getString("COLUMN_NAME");
                    query.append(col);
                    key = true;
                }
                if (key) {
                    query.append(" FROM ").append(tableName);
                    return query.toString();
                } else {
                    System.out.println("Таблице " + tableName + "  без столбцов");
                }
            }
        }
        return null;
    }

    /*
        В чате было множество дискуссий по поводу вывода таблиц. Так однозначного ответа, всё выводить или только
        таблицы, не прозвучало. Если необходимо вывести всё. Необходимо в методе metaData.getTables, последний
        параметр заменить на null - metaData.getTables(null, null, null, null)
     */
    @Override
    public List<String> getTables() throws SQLException {
        List<String> listTables = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                listTables.add(rs.getString(3));
            }
        }

        return listTables;
    }
}
