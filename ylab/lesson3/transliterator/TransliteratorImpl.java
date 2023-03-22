package ylab.lesson3.transliterator;

public class TransliteratorImpl implements Transliterator {

    @Override
    public String transliterate(String source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if (TableTransliteration.TABLE.containsKey(source.charAt(i))) {
                result.append(TableTransliteration.TABLE.get(source.charAt(i)));
                continue;
            }
            result.append(source.charAt(i));
        }
        return result.toString();
    }
}
