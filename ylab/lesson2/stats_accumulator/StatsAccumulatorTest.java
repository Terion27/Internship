package ylab.lesson2.stats_accumulator;

import java.util.Random;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulatorImpl statsAccumulator = new StatsAccumulatorImpl();
        Random rand = new Random();
        int countValue = rand.nextInt(50);
        for (int i = 0; i < countValue; i++) {
            int value = rand.nextInt(1000);
            statsAccumulator.add(value);
        }
        System.out.println("Минимальное значение: " + statsAccumulator.getMin());
        System.out.println("Максимальное значение: " + statsAccumulator.getMax());
        System.out.println("Количество переданных чисел: " + statsAccumulator.getCount());
        System.out.println("Среднее арифметическое чисел: " + statsAccumulator.getAvg());
    }
}
