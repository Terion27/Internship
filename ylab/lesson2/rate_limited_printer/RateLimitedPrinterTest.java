package ylab.lesson2.rate_limited_printer;

public class RateLimitedPrinterTest {
    public static void main(String[] args) {
        RateLimitedPrinterImpl rateLimitedPrinter = new RateLimitedPrinterImpl(2000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }
    }
}
