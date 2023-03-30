package ylab.lesson2.rate_limited_printer;

public class RateLimitedPrinterImpl implements RateLimitedPrinter {

    private final int interval;
    private long lastTime;

    public RateLimitedPrinterImpl(int interval) {
        this.interval = interval;
    }

    @Override
    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (lastTime + interval < currentTime) {
            System.out.println(message);
            lastTime = currentTime;
        }
    }
}
