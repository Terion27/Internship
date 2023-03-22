package ylab.lesson3.transliterator;

import java.util.HashMap;
import java.util.Map;

public class TableTransliteration {
    public static final Map<Character, String> TABLE = new HashMap<>();

    private TableTransliteration() {
    }

    static {
        TABLE.put('А', "A");
        TABLE.put('Б', "B");
        TABLE.put('В', "V");
        TABLE.put('Г', "G");
        TABLE.put('Д', "D");
        TABLE.put('Е', "E");
        TABLE.put('Ё', "E");
        TABLE.put('Ж', "ZH");
        TABLE.put('З', "Z");
        TABLE.put('И', "I");
        TABLE.put('Й', "I");
        TABLE.put('К', "K");
        TABLE.put('Л', "L");
        TABLE.put('М', "M");
        TABLE.put('Н', "N");
        TABLE.put('О', "O");
        TABLE.put('П', "P");
        TABLE.put('Р', "R");
        TABLE.put('С', "S");
        TABLE.put('Т', "T");
        TABLE.put('У', "U");
        TABLE.put('Ф', "F");
        TABLE.put('Х', "KH");
        TABLE.put('Ц', "TS");
        TABLE.put('Ч', "CH");
        TABLE.put('Ш', "SH");
        TABLE.put('Щ', "SHCH");
        TABLE.put('Ы', "Y");
        TABLE.put('Ь', "");
        TABLE.put('Ъ', "IE");
        TABLE.put('Э', "E");
        TABLE.put('Ю', "IU");
        TABLE.put('Я', "IA");
    }
}
