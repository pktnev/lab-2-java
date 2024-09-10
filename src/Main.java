public class Main {
    public static void main(String[] args) {
        int number_of_cells = 100000000; // Кількість елементів масиву
        int threadNum = 8; // Кількість потоків

        long startTime = System.nanoTime();
        ArrClass arrClass = new ArrClass(number_of_cells, threadNum);
        long minValue = arrClass.oneThreadMin(0, number_of_cells);
        int minIndex = arrClass.oneThreadMinIndex(0, number_of_cells);
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Single-threaded min value: " + minValue + " at index: " + minIndex + " Time: " + elapsedTime + " ns");

        startTime = System.nanoTime();
        minValue = arrClass.threadMin();
        minIndex = arrClass.getMinIndex();
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("Multi-threaded min value: " + minValue + " at index: " + minIndex + " Time: " + elapsedTime + " ns");
    }
}
