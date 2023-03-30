package ylab.lesson4.persistentmap;

import ylab.lesson4.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        try {
            DataSource dataSource = initDb();
            PersistentMap persistentMap = new PersistentMapImpl(dataSource);
            persistentMap.init("Map1");
            persistentMap.put("key1", "val1");
            persistentMap.put("key2", "val2");
            System.out.println(persistentMap.get("key1"));
            System.out.println(persistentMap.containsKey("no_key"));
            persistentMap.init("Map2");
            persistentMap.put("key1", "val1");
            System.out.println(persistentMap.getKeys());
            persistentMap.init("Map1");
            System.out.println(persistentMap.getKeys());
            persistentMap.init("Map4");
            persistentMap.put("key1", "val1");
            System.out.println(persistentMap.getKeys());
            persistentMap.init("Map1");
            persistentMap.remove("key1");
            System.out.println(persistentMap.getKeys());
            persistentMap.clear();
            System.out.println(persistentMap.getKeys());
            persistentMap.init("Map4");
            System.out.println(persistentMap.getKeys());
        } catch (
                SQLException e) {
            System.out.println("Ошибка при обращении к БД " + e.getMessage());
        }
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = """
                  drop table if exists persistent_map;
                  CREATE TABLE if not exists persistent_map (
                    map_name varchar,
                    KEY varchar,
                    value varchar
                  );
                """;

        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
