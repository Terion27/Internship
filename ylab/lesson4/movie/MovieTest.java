package ylab.lesson4.movie;

import ylab.lesson4.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

public class MovieTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        MovieLoader movieLoader = new MovieLoaderImpl(dataSource);

        File dataFile = new File("movies.csv");
        movieLoader.loadData(dataFile);

        /**
          Тут написать в комментариях запрос получения всех
          Как я понимаю, тут нужно писать запрос на получение фильмов по жанрам
          String sqlSelect = "SELECT subject, COUNT(subject) FROM movie GROUP BY subject";
         */
    }

    private static DataSource initDb() throws SQLException {
        String createMovieTable = """
                    drop table if exists movie;
                    CREATE TABLE IF NOT EXISTS movie (
                        id bigserial NOT NULL,
                        year int4,
                        length int4,
                        title varchar,
                        subject varchar,
                        actors varchar,
                        actress varchar,
                        director varchar,
                        popularity int4,
                        awards bool,
                        CONSTRAINT movie_pkey PRIMARY KEY (id)
                    );
                """;
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMovieTable, dataSource);
        return dataSource;
    }
}
