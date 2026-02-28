package inputStrategy;

import collections.MyCustomCollection;
import inputStrategy.DataFillStrategy;
import model.MyCustomModel;

import java.util.Scanner;
import java.util.stream.IntStream;

public class manualFill implements DataFillStrategy<MyCustomModel> {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public MyCustomCollection<MyCustomModel> fill(int size) {
        MyCustomCollection<MyCustomModel> list = new MyCustomCollection<>(size);

        IntStream.range(0, size)
                .mapToObj(i -> {
                    String name = readNonEmptyName("Введите название Задачи: ");
                    int number = readNumber("Введите приоритет (1 - наивысший , 10 - наименьший): ");
                    boolean isTrue = readStatus("Статус задачи (решена? y/n): ");

                    return MyCustomModel.builder()
                            .name(name)
                            .number(number)
                            .isTrue(isTrue)
                            .build();
                })
                .forEach(list::add);

        return list;
    }

    private String readNonEmptyName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Ошибка: строка не может быть пустой. Попробуйте снова.");
        }
    }

    private boolean readStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equals("y")) return true;
            else if (input.equals("n")) return false;
            else System.out.println("Ошибка: значение должно быть \"y\" или \"n\". Попробуйте снова.");
        }
    }

    private int readNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                if (number <= 0 || number > 10) {
                    System.out.println("Ошибка: приоритет должен быть в диапазоне 1–10. Попробуйте снова.");
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректный приоритет.");
            }
        }
    }
}