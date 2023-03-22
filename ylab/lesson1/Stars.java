package ylab.lesson1;

import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            int m = scan.nextInt();
            String symbol = scan.next();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) System.out.print(symbol);
                System.out.println();
            }
        }
    }
}
