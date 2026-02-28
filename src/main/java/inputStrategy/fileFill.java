package inputStrategy;

import collections.MyCustomCollection;
import inputStrategy.DataFillStrategy;
import model.MyCustomModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

public class fileFill implements DataFillStrategy<MyCustomModel> {
    private final String filePath;

    public fileFill() {
        String filePath;
        while (true) {
            try {
                System.out.print("название файла (стандарт \"tasks.csv\"): ");
                Scanner scanner = new Scanner(System.in);
                filePath = scanner.nextLine().trim();
                FileInputStream fileInputStream = new FileInputStream(filePath);
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка: файл не найден");
            }

        }
        this.filePath = filePath;
    }

    public fileFill(String filePath){this.filePath = filePath;}

    @Override
    public MyCustomCollection<MyCustomModel> fill(int size) {
        MyCustomCollection<MyCustomModel> list = new MyCustomCollection<>(size);

        try {
            Files.lines(Paths.get(filePath))
                    .limit(size)
                    .map(this::parseModel)
                    .flatMap(Optional::stream)
                    .forEach(list::add);
            return list;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
        return list;
    }

    private Optional<MyCustomModel> parseModel(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) {
            System.out.println("Пропущена строка (не хватает данных): " + line);
            return Optional.empty();
        }

        String name = parts[0].trim();
        int number = Integer.parseInt(parts[1].trim());
        boolean isTrue = Boolean.parseBoolean(parts[2].trim());
        
        if (name.isEmpty() || number <= 0 || number > 10) {
            System.out.println("Пропущена строка (некорректные данные): " + line);
            return Optional.empty();
        }

        return Optional.of(MyCustomModel.builder()
                .name(name)
                .number(number)
                .isTrue(isTrue)
                .build());

    }
}
