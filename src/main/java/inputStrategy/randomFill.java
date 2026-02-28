package inputStrategy;

import collections.MyCustomCollection;
import inputStrategy.DataFillStrategy;
import model.MyCustomModel;

import java.util.Random;
import java.util.stream.IntStream;

public class randomFill implements DataFillStrategy<MyCustomModel> {
    private Random random = new Random();

    @Override
    public MyCustomCollection<MyCustomModel> fill(int size) {
        if (size < 1) throw new RuntimeException("Размер коллекции для заполнения должен быть больше нуля");
        MyCustomCollection<MyCustomModel> list = new MyCustomCollection<>(size);
        IntStream.range(0, size)
                .mapToObj(i -> MyCustomModel.builder()
                        .number(random.nextInt(10)+1)
                        .isTrue(random.nextBoolean())
                        .name("Task " + random.nextInt(Math.abs(size)))
                        .build())
                .forEach(list::add);

        return list;
    }
}