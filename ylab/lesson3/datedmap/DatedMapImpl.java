package ylab.lesson3.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap {
    private final Map<String, String> map;
    private final Map<String, Date> mapLastDate;

    public DatedMapImpl() {
        map = new HashMap<>();
        mapLastDate = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        map.put(key, value);
        mapLastDate.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return map.getOrDefault(key, null);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
        mapLastDate.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return mapLastDate.getOrDefault(key, null);
    }
}
