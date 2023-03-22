package ylab.lesson2.sequences;

public class SequencesImpl implements Sequences {
    @Override
    public void a(int n) {
        System.out.print("A.");
        for (int i = 1; i <= n; i++) {
            System.out.print(" " + i * 2);
        }
        System.out.println();
    }

    @Override
    public void b(int n) {
        System.out.print("B.");
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result = (i == 1) ? 1 : result + 2;
            System.out.print(" " + result);
        }
        System.out.println();
    }

    @Override
    public void c(int n) {
        System.out.print("C.");
        for (int i = 1; i <= n; i++) {
            System.out.print(" " + (int) Math.pow(i, 2));
        }
        System.out.println();
    }

    @Override
    public void d(int n) {
        System.out.print("D.");
        for (int i = 1; i <= n; i++) {
            System.out.print(" " + (int) Math.pow(i, 3));
        }
        System.out.println();
    }

    @Override
    public void e(int n) {
        System.out.print("E.");
        for (int i = 1; i <= n; i++) {
            int result = (i % 2 == 0) ? -1 : 1;
            System.out.print(" " + result);
        }
        System.out.println();
    }

    @Override
    public void f(int n) {
        System.out.print("F.");
        for (int i = 1; i <= n; i++) {
            int result = (i % 2 != 0) ? i : i * -1;
            System.out.print(" " + result);
        }
        System.out.println();
    }

    @Override
    public void g(int n) {
        System.out.print("G.");
        for (int i = 1; i <= n; i++) {
            int result = (i % 2 != 0) ? (int) Math.pow(i, 2) : (int) Math.pow(i, 2) * -1;
            System.out.print(" " + result);
        }
        System.out.println();
    }

    @Override
    public void h(int n) {
        System.out.print("H.");
        int index = 0;
        for (int i = 1; i <= n; i++) {
            int result = (i % 2 == 0) ? 0 : ++index;
            System.out.print(" " + result);
        }
        System.out.println();
    }

    @Override
    public void i(int n) {
        System.out.print("I.");
        long result = 0;  // Использую long, т.к уже на n=13 получаю переполнение int.
        for (int i = 1; i <= n; i++) {
            result = (i == 1) ? 1 : result * i;
            System.out.print(" " + result);
        }
        System.out.println();
    }

    @Override
    public void j(int n) {
        System.out.print("J.");
        int lastNumber = 1;
        int prevNumber = 1;
        for (int i = 1; i <= n; i++) {
            int result = (i < 3) ? 1 : prevNumber + lastNumber;
            System.out.print(" " + result);
            prevNumber = lastNumber;
            lastNumber = result;
        }
        System.out.println();
    }
}
