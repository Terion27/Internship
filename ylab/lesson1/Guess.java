package ylab.lesson1;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(99) + 1; // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
        int x;
        Scanner scan = new Scanner(System.in);
        for (int i = 1; i <= maxAttempts; i++) {
            System.out.print("Введите целое число: ");
            x = scan.nextInt();
            if (x < number) {
                System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - i) + " попыток");
            } else if (x > number) {
                System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - i) + " попыток");
            } else {
                System.out.println("Ты угадал с " + i + " попытки");
                break;
            }
            if (i == maxAttempts) System.out.println("Ты не угадал");
        }
    }
}
