package ylab.lesson2.complex_numbers;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber complexNumberOne = new ComplexNumber(-2.5);
        ComplexNumber complexNumberTwo = new ComplexNumber(7, -3);
        System.out.println(complexNumberOne.addition(complexNumberTwo));
        System.out.println(complexNumberOne.subtraction(complexNumberTwo));
        System.out.println(complexNumberOne.multiplication(complexNumberTwo));
        System.out.println(complexNumberOne.modulus());
        System.out.println(complexNumberTwo.modulus());
    }
}
