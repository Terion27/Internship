package ylab.lesson2.snils_validator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidatorImpl snilsValidator = new SnilsValidatorImpl();
        System.out.println(snilsValidator.validate("074-809-346-90"));
        System.out.println(snilsValidator.validate("01468870570"));
        System.out.println(snilsValidator.validate("90114404441"));
        System.out.println(snilsValidator.validate("Здесь был лев"));
        System.out.println(snilsValidator.validate(null));
    }
}
