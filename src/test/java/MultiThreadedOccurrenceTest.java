import collections.MyCustomCollection;
import model.MyCustomModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultiThreadedOccurrenceTest {
    private MyCustomCollection<MyCustomModel> collection;

    @BeforeEach
    public void setUp() {
        collection = new MyCustomCollection<>(10);

        // Добавляем тестовые данные
        collection.add(MyCustomModel.builder()
                .name("Task A")
                .number(5)
                .isTrue(true)
                .build());

        collection.add(MyCustomModel.builder()
                .name("Task B")
                .number(3)
                .isTrue(false)
                .build());

        collection.add(MyCustomModel.builder()
                .name("Task A")  // Дубликат
                .number(5)
                .isTrue(true)
                .build());

        collection.add(MyCustomModel.builder()
                .name("Task C")
                .number(1)
                .isTrue(true)
                .build());
    }

    @Test
    public void testMultiThreadedCount() {
        MyCustomModel target = MyCustomModel.builder()
                .name("Task A")
                .number(5)
                .isTrue(true)
                .build();

        long count = collection.getOccurrenceCounterMultiThreaded(target);
        Assertions.assertEquals(2, count, "Должно быть 2 вхождения Task A");
    }

    @Test
    public void testManualThreadsCount() {
        MyCustomModel target = MyCustomModel.builder()
                .name("Task A")
                .number(5)
                .isTrue(true)
                .build();

        long count = collection.getOccurrenceCounterManualThreads(target, 2);
        Assertions.assertEquals(2, count, "Должно быть 2 вхождения Task A");
    }

    @Test
    public void testPerformance() {
        // Создаем большую коллекцию для теста производительности
        MyCustomCollection<MyCustomModel> bigCollection = new MyCustomCollection<>(10000);
        for (int i = 0; i < 10000; i++) {
            bigCollection.add(MyCustomModel.builder()
                    .name("Task " + (i % 100))
                    .number(i % 10 + 1)
                    .isTrue(i % 2 == 0)
                    .build());
        }

        MyCustomModel target = MyCustomModel.builder()
                .name("Task 42")
                .number(5)
                .isTrue(true)
                .build();

        // Сравниваем производительность
        long start = System.nanoTime();
        long sequential = bigCollection.getOccurrenceCounter(target);
        long seqTime = System.nanoTime() - start;

        start = System.nanoTime();
        long parallel = bigCollection.getOccurrenceCounterMultiThreaded(target);
        long parTime = System.nanoTime() - start;

        System.out.println("Sequential: " + seqTime / 1_000_000.0 + " ms, result: " + sequential);
        System.out.println("Parallel:   " + parTime / 1_000_000.0 + " ms, result: " + parallel);

        Assertions.assertEquals(sequential, parallel);
    }
}
