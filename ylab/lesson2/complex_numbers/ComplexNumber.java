package ylab.lesson2.complex_numbers;

public class ComplexNumber {

    private final double real;
    private final double imaginary;

    public ComplexNumber(double real) {
        this.real = real;
        this.imaginary = 0;
    }

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber addition(ComplexNumber complexNumberTwo) {
        double tReal = this.real + complexNumberTwo.real;
        double tImaginary = this.imaginary + complexNumberTwo.imaginary;
        return new ComplexNumber(tReal, tImaginary);
    }

    public ComplexNumber subtraction(ComplexNumber complexNumberTwo) {
        double tReal = this.real - complexNumberTwo.real;
        double tImaginary = this.imaginary - complexNumberTwo.imaginary;
        return new ComplexNumber(tReal, tImaginary);
    }

    public ComplexNumber multiplication(ComplexNumber complexNumberTwo) {
        double tReal = this.real * complexNumberTwo.real - this.imaginary * complexNumberTwo.imaginary;
        double tImaginary = this.real * complexNumberTwo.imaginary + this.imaginary * complexNumberTwo.real;
        return new ComplexNumber(tReal, tImaginary);
    }

    public double modulus() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
    }

    @Override
    public String toString() {
        return (real + ((imaginary < 0) ? " - " : " + ") + Math.abs(imaginary) + "i");
    }
}
