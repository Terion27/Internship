package ylab.lesson2.sequences;

import java.util.Scanner;

public class SequencesTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите целое число: ");
        int n = scan.nextInt();
        scan.close();

        SequencesImpl sequences = new SequencesImpl();
        sequences.a(n);
        sequences.b(n);
        sequences.c(n);
        sequences.d(n);
        sequences.e(n);
        sequences.f(n);
        sequences.g(n);
        sequences.h(n);
        sequences.i(n);
        sequences.j(n);
    }
}
