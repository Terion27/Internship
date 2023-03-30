package ylab.lesson4.filesort;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class FileSortImpl implements FileSorter {
    private final DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        File resultData = new File("result_data.txt");
        try {
            loadDb(data);
            sortDbToFile(resultData);

        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

        return resultData;
    }

    private void sortDbToFile(File resultData) throws CustomException {
        String sql = "select val from numbers order by val desc";

        try (PrintWriter fResult = new PrintWriter(resultData); Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSql = preparedStatement.executeQuery();
            while (resultSql.next()) {
                fResult.println(resultSql.getLong("val"));
            }
            fResult.flush();

        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    private void loadDb(File data) throws CustomException {
        String sql = "insert into numbers (val) values (?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             FileInputStream fileData = new FileInputStream(data)) {
            Scanner scan = new Scanner(fileData);
            while (scan.hasNext()) {
                preparedStatement.setLong(1, scan.nextLong());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}