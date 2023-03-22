package ylab.lesson3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator
                .transliterate("HELLO! ПРИВЕТ! привет!  Go, МАЛЬЧИК!");
        System.out.println(res);
    }
}
