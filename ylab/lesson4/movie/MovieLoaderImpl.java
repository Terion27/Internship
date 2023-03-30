package ylab.lesson4.movie;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieLoaderImpl implements MovieLoader {
    private final DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        List<Movie> movies = new ArrayList<>();
        try {
            loadFromFileToMovie(file, movies);
            loadDb(movies);

        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadDb(List<Movie> movies) throws CustomException {
        String sql = """
                    insert into movie (year, length, title, subject, actors, actress, director, popularity, awards)
                    values (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Movie movie : movies) {
                if (movie.getYear() == null) {
                    ps.setNull(1, Types.INTEGER);
                } else {
                    ps.setInt(1, movie.getYear());
                }
                if (movie.getLength() == null) {
                    ps.setNull(2, Types.INTEGER);
                } else {
                    ps.setInt(2, movie.getLength());
                }
                ps.setString(3, movie.getTitle());
                ps.setString(4, movie.getSubject());
                ps.setString(5, movie.getActors());
                ps.setString(6, movie.getActress());
                ps.setString(7, movie.getDirector());
                if (movie.getPopularity() == null) {
                    ps.setNull(8, Types.INTEGER);
                } else {
                    ps.setInt(8, movie.getPopularity());
                }
                ps.setBoolean(9, movie.getAwards());
                ps.addBatch();
            }
            ps.executeBatch();

        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    private void loadFromFileToMovie(File file, List<Movie> movies) throws CustomException {
        try (FileInputStream fileStream = new FileInputStream(file)) {
            Scanner scan = new Scanner(fileStream);
            scan.nextLine();
            scan.nextLine();
            Integer num;
            while (scan.hasNextLine()) {
                String[] str = scan.nextLine().split(";");
                Movie movie = new Movie();
                num = (str[0].equals("") ? null : Integer.parseInt(str[0]));
                movie.setYear(num);
                num = (str[1].equals("") ? null : Integer.parseInt(str[1]));
                movie.setLength(num);
                movie.setTitle(str[2]);
                movie.setSubject(str[3]);
                movie.setActors(str[4]);
                movie.setActress(str[5]);
                movie.setDirector(str[6]);
                num = (str[7].equals("") ? null : Integer.parseInt(str[7]));
                movie.setPopularity(num);
                movie.setAwards(str[8].equals("Yes"));
                movies.add(movie);
            }
        } catch (IOException e) {
            throw new CustomException(e);
        }
    }
}
