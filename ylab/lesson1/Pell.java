package ylab.lesson1;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите целое положительное число: ");
            int n = scanner.nextInt();
            long result = n;
            long n1 = 1;
            long n2 = 0;
            if (n > 1) {
                for (int i = 2; i <= n; i++) {  // Pell numbers: a(0) = 0, a(1) = 1; for n > 1, a(n) = 2*a(n-1) + a(n-2).
                    result = 2 * n1 + n2;
                    n2 = n1;
                    n1 = result;
                }
            }
            System.out.println("Pell numbers: " + result);
        }
    }
}
