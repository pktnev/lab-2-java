import java.util.Random;

public class ArrClass {
    private final int number_of_cells;
    private final int threadNum;
    public final int[] arr;
    private long min = Long.MAX_VALUE;
    private int minIndex = -1;
    private int threadCount = 0;

    public ArrClass(int number_of_cells, int threadNum) {
        this.number_of_cells = number_of_cells;
        this.threadNum = threadNum;
        arr = new int[number_of_cells];
        Random random = new Random();

        // Заповнюємо масив значеннями від 0 до number_of_cells - 1
        for (int i = 0; i < number_of_cells; i++) {
            arr[i] = i;
        }

        // Замінюємо випадковий елемент на від'ємне число
        arr[random.nextInt(number_of_cells)] *= -1;
    }

    public long oneThreadMin(int startIndex, int finishIndex) {
        long min = Long.MAX_VALUE;
        for (int i = startIndex; i < finishIndex; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public int oneThreadMinIndex(int startIndex, int finishIndex) {
        long min = Long.MAX_VALUE;
        int index = startIndex;
        for (int i = startIndex; i < finishIndex; i++) {
            if (min > arr[i]) {
                min = arr[i];
                index = i;
            }
        }
        return index;
    }

    synchronized private long getMin() {
        while (threadCount < threadNum) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(long min, int index) {
        if (this.min > min) {
            this.min = min;
            this.minIndex = index;
        }
    }

    synchronized public void incThreadCount() {
        threadCount++;
        notify();
    }

    public long threadMin() {
        ThreadMin[] threadMins = new ThreadMin[threadNum];
        int len = number_of_cells / threadNum;

        // Створюємо та запускаємо потоки
        for (int i = 0; i < threadNum - 1; i++) {
            threadMins[i] = new ThreadMin(len * i, len * (i + 1), this);
            threadMins[i].start();
        }
        threadMins[threadNum - 1] = new ThreadMin(len * (threadNum - 1), number_of_cells, this);
        threadMins[threadNum - 1].start();

        getMin(); // Дочекатися завершення всіх потоків
        return min;
    }

    public int getMinIndex() {
        return minIndex;
    }
}
