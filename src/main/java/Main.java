package main.java;

import main.java.collections.MyCustomCollection;
import main.java.inputStrategy.DataFillStrategy;
import main.java.inputStrategy.fileFill;
import main.java.inputStrategy.manualFill;
import main.java.inputStrategy.MyCustomModelFiller;
import main.java.inputStrategy.randomFill;
import main.java.model.MyCustomModel;

import java.util.Comparator;
import java.util.Scanner;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static MyCustomCollection<MyCustomModel> modelCollection = null;
    private static final MyCustomModelFiller modelFiller = new MyCustomModelFiller();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            int mainChoice = getChoice();
            switch (mainChoice) {
                case 1:
                    handlePersonMenu();
                    break;
                case 2:
                    running = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Главное меню ---");
        System.out.println("1. Работа с MyCustomModel");
        System.out.println("2. Выйти");
        System.out.print("Выберите действие: ");
    }

    private static void handlePersonMenu() {
        boolean modelMenuRunning = true;
        while (modelMenuRunning) {
            printEntityMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    fillCollection();
                    break;
                case 2:
                    sortCollection();
                    break;
                case 3:
                    searchData();
                    break;
                case 4:
                    printCollection(modelCollection);
                    break;
                case 5:
                    occurrenceCounter();
                    break;
                case 6:
                    saveToFile();
                    break;
                case 7:
                    modelMenuRunning = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void printEntityMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Заполнить данные");
        System.out.println("2. Сортировать данные");
        System.out.println("3. Найти элемент (поиск)");
        System.out.println("4. Показать текущий список");
        System.out.println("5. Подсчет вхождений");
        System.out.println("6. Сохранить в файл");
        System.out.println("7. Назад");
        System.out.print("Выберите действие: ");
    }

    private static void fillCollection() {
        System.out.println("Выберите способ заполнения:");
        System.out.println("1. Вручную");
        System.out.println("2. Случайно");
        System.out.println("3. Из файла");
        System.out.print("Выбор: ");
        int fillChoice = getChoice();

        System.out.print("Введите размер коллекции: ");
        int size = Integer.parseInt(scanner.nextLine());

        DataFillStrategy strategy;

        switch (fillChoice) {
            case 1:
                strategy = new manualFill();
                break;
            case 2:
                strategy = new randomFill();
                break;
            case 3:
                strategy = new fileFill();
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }
        modelFiller.setStrategy(strategy);
        modelCollection = modelFiller.fill(size);
        System.out.println("Коллекция заполнена. Размер: " + modelCollection.size());
    }

    private static void sortCollection() {
        if (modelCollection == null || modelCollection.size() == 0) {
            System.out.println("Сначала заполните коллекцию!");
            return;
        }

        System.out.println("\nВыберите поле для сортировки:");
        System.out.println("1. По имени");
        System.out.println("2. По приоритету");
        System.out.println("3. По статусу");
        System.out.print("Ваш выбор: ");

        int fieldChoice = getChoice();
        Comparator<MyCustomModel> comparator = null;
        String sortType = "";

        switch (fieldChoice) {
            case 1:
                comparator = Comparator.comparing(MyCustomModel::getName);
                sortType = "name";
                break;
            case 2:
                comparator = Comparator.comparing(MyCustomModel::getNumber);
                sortType = "priority";
                break;
            case 3:
                comparator = Comparator.comparing(MyCustomModel::isTrue);
                sortType = "status";
                break;
            default:
                System.out.println("Неверный выбор");
                return;
        }

        for (int i = 0; i < modelCollection.size() - 1; i++) {
            for (int j = 0; j < modelCollection.size() - i - 1; j++) {
                if (comparator.compare(modelCollection.get(j), modelCollection.get(j + 1)) > 0) {
                    MyCustomModel temp = modelCollection.get(j);
                }
            }
        }

        System.out.println("Коллекция отсортирована по полю: " + sortType);
        saveSortedToFile(sortType);
    }

    private static void saveSortedToFile(String sortType) {
        if (modelCollection == null || modelCollection.size() == 0) {
            System.out.println("Нет данных для сохранения!");
            return;
        }

        try {
            Files.createDirectories(Paths.get("output"));

            String filename = "output/sorted_by_" + sortType + ".txt";
            Path filePath = Paths.get(filename);

            List<String> lines = new ArrayList<>();
            lines.add("=== РЕЗУЛЬТАТ СОРТИРОВКИ ===");
            lines.add("Поле сортировки: " + sortType);
            lines.add("Всего элементов: " + modelCollection.size());
            lines.add("Дата: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            lines.add("");
            lines.add("Содержимое:");
            lines.add("");

            for (int i = 0; i < modelCollection.size(); i++) {
                MyCustomModel item = modelCollection.get(i);
                lines.add((i + 1) + ". " + item.toString());
            }

            Files.write(filePath, lines, StandardOpenOption.CREATE,
                       StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("✅ Отсортированный список сохранен в файл: " + filename);

        } catch (IOException e) {
            System.out.println("❌ Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    private static void saveToFile() {
        if (modelCollection == null || modelCollection.size() == 0) {
            System.out.println("Нет данных для сохранения!");
            return;
        }
        saveSortedToFile("current");
    }

    private static void searchData() {
        if (modelCollection == null || modelCollection.size() == 0) {
            System.out.println("Сначала заполните коллекцию.");
            return;
        }

        System.out.println("Введите данные для поиска:");
        MyCustomModel target = null;

        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Приоритет: ");
        String number = scanner.nextLine();
        System.out.print("Статус (true/false): ");
        String isTrue = scanner.nextLine();

        try {
            target = MyCustomModel.builder()
                    .name(name)
                    .number(Integer.parseInt(number))
                    .isTrue(Boolean.parseBoolean(isTrue))
                    .build();
        } catch (RuntimeException e) {
            System.out.println("Не получилось собрать объект.");
            return;
        }

        boolean found = false;
        for (int i = 0; i < modelCollection.size(); i++) {
            if (modelCollection.get(i).equals(target)) {
                System.out.println("Элемент найден на позиции: " + i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Элемент не найден");
        }
    }

    private static void occurrenceCounter() {
        if (modelCollection == null || modelCollection.size() == 0) {
            System.out.println("Сначала заполните коллекцию.");
            return;
        }

        System.out.println("Введите данные для подсчёта вхождений:");
        MyCustomModel target = null;

        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Приоритет: ");
        String number = scanner.nextLine();
        System.out.print("Статус (true/false): ");
        String isTrue = scanner.nextLine();

        try {
            target = MyCustomModel.builder()
                    .name(name)
                    .number(Integer.parseInt(number))
                    .isTrue(Boolean.parseBoolean(isTrue))
                    .build();
        } catch (RuntimeException e) {
            System.out.println("Не получилось собрать объект.");
            return;
        }

        long occurrences = modelCollection.getOccurrenceCounter(target);
        System.out.println("Элемент встречается " + occurrences + " раз(а).");
    }

    private static void printCollection(MyCustomCollection<?> collection) {
        if (collection == null) {
            System.out.println("Коллекция пуста.");
        } else {
            System.out.println("Текущая коллекция:");
            for (int i = 0; i < collection.size(); i++) {
                System.out.println(collection.get(i));
            }
        }
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}