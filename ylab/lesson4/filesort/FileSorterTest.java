package ylab.lesson4.filesort;

import ylab.lesson4.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

public class FileSorterTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        File data = new File("data.txt");
        FileSorter fileSorter = new FileSortImpl(dataSource);
        File res = fileSorter.sort(data);
        System.out.println("Sorting completed. File is " + res);

    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = """
                  drop table if exists numbers;
                  CREATE TABLE if not exists numbers (
                    val bigint
                  )
                """;

        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
