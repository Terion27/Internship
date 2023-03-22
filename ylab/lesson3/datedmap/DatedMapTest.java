package ylab.lesson3.datedmap;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMapImpl data = new DatedMapImpl();
        data.put("add1", "string 1");
        data.put("add2", "string 2");
        data.put("add3", "string 3");
        System.out.println(data.get("add1") + " - " + data.getKeyLastInsertionDate("add1"));
        System.out.println(data.get("add2") + " - " + data.getKeyLastInsertionDate("add2"));
        System.out.println(data.get("add3") + " - " + data.getKeyLastInsertionDate("add3"));
        System.out.println(data.containsKey("add1"));
        System.out.println(data.containsKey("add5"));
        System.out.println(data.containsKey("add3"));
        data.remove("add2");
        System.out.println(data.keySet());
        System.out.println("value key - add2: " + data.get("add2"));
        System.out.println("Insertion data key - add2: " + data.getKeyLastInsertionDate("add2"));

        Thread.sleep(2000);  // для проверки корректности даты изменения значения ключа.
        data.put("add1", "new string");
        System.out.println(data.get("add1") + " - " + data.getKeyLastInsertionDate("add1"));
    }
}