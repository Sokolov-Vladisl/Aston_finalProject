import collections.MyCustomCollection;

import inputStrategy.DataFillStrategy;
import inputStrategy.fileFill;
import inputStrategy.manualFill;
import inputStrategy.MyCustomModelFiller;
import inputStrategy.randomFill;
import model.MyCustomModel;
import sorting.QuickSort;
import sorting.SortService;

import search.MyBinarySearch;

//import sorting.MultiThreadSorting;
//import sorting.QuickSort;


import java.util.Comparator;
import java.util.Scanner;

//import static output.WriteDown.MyOutput;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Поля для хранения коллекций
    private static MyCustomCollection<MyCustomModel> modelCollection = null;

    // Поля для фасадов заполнения
    private static final MyCustomModelFiller modelFiller = new MyCustomModelFiller();

    // Поля для сервисов сортировки
   private static final SortService personSortService = new SortService();


    public static int M=0;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            int mainChoice = getChoice();
            switch (mainChoice) {
                case 1: // Работа с MyCustomModel
                    handlePersonMenu();
                    break;
                case 2: // Выход
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
                case 1: // Заполнить MyCustomModel
                    fillCollection(modelCollection, modelFiller);
                    break;
                case 2: // Сортировать MyCustomModel


                    // Здесь будет сортировка
                    sortCollection(modelCollection);

                    break;
                case 3: // Найти MyCustomModel (бинарный поиск)
                    if (modelCollection != null && modelCollection.size() > 0) {
                        // Проверяем, отсортирована ли коллекция
                        MyBinarySearch<MyCustomModel> binarySearch = new MyBinarySearch<>(modelCollection);
                        if (!binarySearch.isSorted()) {
                            System.out.println("Внимание: для бинарного поиска коллекция должна быть отсортирована!");
                            System.out.print("Хотите продолжить? (y/n): ");
                            String answer = scanner.nextLine();
                            if (!answer.equalsIgnoreCase("y")) {
                                break;
                            }
                        }
                        searchData(modelCollection);
                    } else {
                        System.out.println("Сначала заполните коллекцию.");
                    }
                    break;
                case 4: // Показать MyCustomModel
                    printCollection(modelCollection);
                    break;
                case 5: // Обычный подсчет вхождений
                    occurrenceCounter(modelCollection);
                    break;
                case 6: // Многопоточный подсчет вхождений (НОВЫЙ ПУНКТ)
                    occurrenceCounterMultiThreaded(modelCollection);
                    break;
                case 7: // Вернуться к главному меню
                    modelMenuRunning = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void occurrenceCounterMultiThreaded(MyCustomCollection<MyCustomModel> collection) {
        if (collection == null || collection.size() == 0) {
            System.out.println("Сначала заполните коллекцию Задач.");
            return;
        }

        System.out.println("--- Многопоточный подсчёт вхождений ---");
        System.out.println("Введите данные для подсчёта вхождений Задачи:");

        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Приоритет: ");
        String number = scanner.nextLine();
        System.out.print("Статус (выполнена? true/false): ");
        String isTrue = scanner.nextLine();

        try {
            MyCustomModel target = MyCustomModel.builder()
                    .name(name)
                    .number(Integer.parseInt(number))
                    .isTrue(Boolean.parseBoolean(isTrue))
                    .build();

            System.out.println("Выберите метод:");
            System.out.println("1. MultiThreaded (parallelStream)");
            System.out.println("2. Manual Threads");
            System.out.print("Выбор: ");

            int choice = getChoice();
            long occurrences;
            long startTime = System.currentTimeMillis();

            if (choice == 2) {
                System.out.print("Количество потоков (0 - авто): ");
                int threads = getChoice();
                occurrences = collection.getOccurrenceCounterManualThreads(target, threads);
            } else {
                occurrences = collection.getOccurrenceCounterMultiThreaded(target);
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Элемент встречается: " + occurrences + " раз(а)");
            System.out.println("Время выполнения: " + (endTime - startTime) + " мс");

        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void printEntityMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Заполнить данные");
        System.out.println("2. Сортировать данные");
        System.out.println("3. Найти элемент (бинарный поиск)");
        System.out.println("4. Показать текущий список");
        System.out.println("5. Подсчет вхождений (обычный)");
        System.out.println("6. Подсчет вхождений (многопоточный)");
        System.out.println("7. Назад");
        System.out.print("Выберите действие: ");
    }
    



    private static void fillCollection(MyCustomCollection<?> collection, Object filler) {
        System.out.println("Выберите способ заполнения для Задач:");
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
        System.out.println("Коллекция Задач заполнена. Размер: " + modelCollection.size());
        
        
    }


//   private static void sortCollection(){}
    private static void sortCollection(MyCustomCollection<MyCustomModel> collection){
        if (collection == null || collection.size() == 0) {
            System.out.println("Сначала заполните коллекцию Задач.");
            return;
        }
        System.out.println("Выберите способ сортировки для Задач:");
        System.out.println("1. Быстрая сортировка");
        System.out.println("2. Сортировка по приоритету задач только четных значений");
        System.out.print("Выбор: ");
        int sortChoice = getChoice();

        SortService service = new SortService();

        switch (sortChoice) {
            case 1:
                service.setStrategy(new QuickSort<>());
                Comparator<MyCustomModel> comparator = choiceComparator();
                service.sort(collection, comparator);
                System.out.println("Коллекция отсортирована!");
                break;
            case 2:
//                service.setStrategy(new MultiThreadSorting<>());
               break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

    }

    private static Comparator<MyCustomModel> choiceComparator(){

        Comparator<MyCustomModel> comparator = null;
        int fieldsSelected = 0;
        
        while (fieldsSelected <= 3) {
            System.out.println("Выберите по какому полю сортировать Задачи:");
            System.out.println("0. выбор закончен");
            System.out.println("1. по приоритету");
            System.out.println("2. по названию");
            System.out.println("3. по статусу");
            System.out.print("Выбор(0-3): ");

            int choice = getChoice();
            if (choice == 0 && fieldsSelected != 0) break;
            if (choice == 0){
                System.out.println("С начало выберите поля для сортировки");
                continue;
            }
            if (choice < 1 || choice > 3) {
                System.out.println("Неверный выбор! Попробуйте еще раз");
                continue;
            }
            System.out.println("Направление сортировки:");
            System.out.println("1. По возрастанию");
            System.out.println("2. По убыванию");
            System.out.print("Выбор (1-2): ");
            int answer = getChoice();
            boolean revers = (answer == 1);

            Comparator<MyCustomModel> fieldComparator = switch (choice) {
                case 1 -> Comparator.comparing(MyCustomModel::getNumber);
                case 2 -> Comparator.comparing(MyCustomModel::getName);
                case 3 -> Comparator.comparing(MyCustomModel::getStatus);
                default -> null;
            };
            if (!revers) {
                fieldComparator = fieldComparator.reversed();
            }

            if (comparator == null) {
                comparator = fieldComparator;
            } else {
                comparator = comparator.thenComparing(fieldComparator);
            }

            System.out.println("Поле добавлено к сортировке");
            fieldsSelected++;
        }

        return comparator;
    }


    private static <T extends Comparable<T>> void searchData(MyCustomCollection<T> collection) {
        if (collection == null || collection.size() == 0) {
            System.out.println("Сначала заполните и отсортируйте коллекцию Задач.");
            return;
        }

        System.out.println("Введите данные для поиска Задачи:");
        T target = null;


        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Приоритет: ");
        String number = scanner.nextLine();
        System.out.print("Статус (выполнена? true/false): ");
        String isTrue = scanner.nextLine();

        try {
            target = (T) MyCustomModel.builder()
                    .name(name)
                    .number(Integer.parseInt(number))
                    .isTrue(Boolean.parseBoolean(isTrue))
                    .build();
        } catch (RuntimeException e) {
            System.out.println("Не получилось собрать объект. Попробуйте еще раз.");
            return;
        }

        MyBinarySearch<T> binarySearch = new MyBinarySearch<>(collection);
        int index = binarySearch.getIndexedBinarySearch(target);

        if (index >= 0) {
            System.out.println("MyCustomModel найден: " + collection.get(index) + " на позиции " + index);
        } else {
            System.out.println("MyCustomModel не найден. Индекс для вставки: " + (-index - 1));
        }
    }

    private static <T> void occurrenceCounter(MyCustomCollection<T> collection) {
        if (collection == null || collection.size() == 0) {
            System.out.println("Сначала заполните коллекцию Задач.");
            return;
        }

        System.out.println("Введите данные для подсчёта вхождений Задачи:");
        T target = null;

        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Приоритет: ");
        String number = scanner.nextLine();
        System.out.print("Статус (выполнена? true/false): ");
        String isTrue = scanner.nextLine();

        try {
            target = (T) MyCustomModel.builder()
                    .name(name)
                    .number(Integer.parseInt(number))
                    .isTrue(Boolean.parseBoolean(isTrue))
                    .build();
        } catch (RuntimeException e) {
            System.out.println("Не получилось собрать объект. Попробуйте еще раз.");
            return;
        }


        long occurrences = collection.getOccurrenceCounter(target);
        System.out.println("Элемент встречается в коллекции " + occurrences + " раз(а).");
    }


    // Пример метода для печати коллекции
    private static void printCollection(MyCustomCollection<?> collection) {
        if (collection == null) {
            System.out.println("Коллекция Задач пуста (не инициализирована).");
        } else {
            System.out.println("Текущая коллекция Задач:");
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