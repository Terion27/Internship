package ylab.lesson3.passwordvalidator;

import ylab.lesson3.passwordvalidator.exception.WrongLoginException;
import ylab.lesson3.passwordvalidator.exception.WrongPasswordException;

public class PasswordValidatorImpl {

    private PasswordValidatorImpl() {
        // для отключения публичного дефолтового конструктора
    }

    public static boolean getPasswordValidator(String login, String password, String confirmPassword) {
        try {
            validationLogin(login);
            validationPassword(password, confirmPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static void validationLogin(String str) throws WrongLoginException {
        if (!str.matches("\\w*")) {  // суда по разбяснениям в чате администрацией, пустые значения не являются невалидными.
            throw new WrongLoginException("Логин содержит недопустимые символы");
        } else if (str.length() >= 20) {
            throw new WrongLoginException("Логин сдишком длинный");
        }
    }

    private static void validationPassword(String password, String confirmPassword) throws WrongPasswordException {
        if (!password.matches("\\w*")) {  // суда по разбяснениям в чате администрацией, пустые значения не являются невалидными.
            throw new WrongPasswordException("Пароль содержит недопустимые символы");
        } else if (password.length() >= 20) {
            throw new WrongPasswordException("Пароль сдишком длинный");
        } else if (!password.equals(confirmPassword)) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        }
    }
}
