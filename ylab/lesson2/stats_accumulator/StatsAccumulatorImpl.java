package ylab.lesson2.stats_accumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {

    int min = 0;
    int max = 0;
    int count = 0;
    double sum = 0;

    @Override
    public void add(int value) {
        if (count == 0) {
            min = value;
            max = value;
        } else {
            if (value < min) min = value;
            if (value > max) max = value;
        }
        sum += value;
        count++;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getAvg() {
        if (count != 0) {
            return (sum / count);
        } else return 0.0;
    }
}
