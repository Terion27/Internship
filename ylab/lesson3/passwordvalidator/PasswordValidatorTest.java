package ylab.lesson3.passwordvalidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {

        System.out.println(PasswordValidatorImpl.getPasswordValidator("", "", ""));
        System.out.println(PasswordValidatorImpl.getPasswordValidator("login123_", "password123_", "password123_"));
        System.out.println(PasswordValidatorImpl.getPasswordValidator("login7h87_^&", "passwd7987_^&", "passwd^&"));
        System.out.println(PasswordValidatorImpl.getPasswordValidator(
                "logdfsafasdfsadfafdsfasfasdczxcvzin7987_", "passwd7987_^&", "login7987_^&"));
        System.out.println(PasswordValidatorImpl.getPasswordValidator(
                "logdn7987_", "pasfyudfsdfhsdgfhsdfhsdcvsswd7987_", "login7987_^&"));
        System.out.println(PasswordValidatorImpl.getPasswordValidator("login7987_", "passwd7997_^&", "login7987_^"));
        System.out.println(PasswordValidatorImpl.getPasswordValidator("login7987_", "passwd7997_", "login79v87_"));
    }
}
