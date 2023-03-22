package ylab.lesson2.snils_validator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        if (snils == null) return false;
        snils = snils.replace(" ", "").replace("-", "");
        if (snils.matches("\\d{11}+")) {
            int controlNumber = Integer.valueOf(snils.substring(9), 10);
            int sum = 0;
            for (int i = 0; i < snils.length() - 2; i++) {
                sum += Character.digit(snils.charAt(i), 10) * (9 - i);
            }
            if ((sum < 100 && sum == controlNumber) || (sum == 100 && controlNumber == 0)) {
                return true;
            } else return controlNumber == ((sum % 101 == 100) ? 0 : sum % 101);
        }
        return false;
    }
}
