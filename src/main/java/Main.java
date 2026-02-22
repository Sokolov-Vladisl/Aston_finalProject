import collections.MyCustomCollection;

import inputStrategy.DataFillStrategy;
import inputStrategy.fileFill;
import inputStrategy.manualFill;
import inputStrategy.MyCustomModelFiller;
import inputStrategy.randomFill;
import model.MyCustomModel;

//import search.MyBinarySearch;

//import sorting.MultiThreadSorting;
//import sorting.QuickSort;
//import sorting.SortService;
//import sorting.SortStrategy;

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
   // private static final SortService<MyCustomModel> personSortService = new SortService<>();


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
        System.out.println("3. Выйти");
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
                    break;
                case 3: // Найти MyCustomModel
                    break;
                case 4: // Показать MyCustomModel
                    printCollection(modelCollection);
                    break;
                case 5: // Подсчет вхождений MyCustomModel
                    occurrenceCounter(modelCollection);
                    break;
                case 6: // Вернуться к главному меню
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
        System.out.println("6. Назад");
        System.out.print("Выберите действие: ");
    }

    private static void fillCollection(MyCustomCollection<?> collection, Object filler) {
        System.out.println("Выберите способ заполнения для MyCustomModel:");
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
                        //strategy = new manualFill();
                        //break;
            case 2:
                strategy = new randomFill();
                break;
                case 3:
                    System.out.print("Введите имя файла: ");
                        //String fileName = scanner.nextLine();
                        //strategy = new fileFill(fileName);
                        //break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }
        modelFiller.setStrategy(strategy);
        modelCollection = modelFiller.fill(size);
        System.out.println("Коллекция MyCustomModel заполнена. Размер: " + modelCollection.size());
        
        
    }

   // private static void sortCollection(){}

    private static <T extends Comparable<T>> void searchData(MyCustomCollection<T> collection) {
        if (collection == null || collection.size() == 0) {
            System.out.println("Сначала заполните и отсортируйте коллекцию MyCustomModel.");
            return;
        }

        System.out.println("Введите данные для поиска MyCustomModel:");
        T target = null;


        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Number: ");
        String number = scanner.nextLine();
        System.out.print("Is it true: ");
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

//        MyBinarySearch<T> binarySearch = new MyBinarySearch<>(collection);
//        int index = binarySearch.getIndexedBinarySearch(target);

//        if (index >= 0) {
//            System.out.println("MyCustomModel найден: " + collection.get(index) + " на позиции " + index);
//        } else {
//            System.out.println("MyCustomModel не найден. Индекс для вставки: " + (-index - 1));
//        }
    }

    private static <T> void occurrenceCounter(MyCustomCollection<T> collection) {
        if (collection == null || collection.size() == 0) {
            System.out.println("Сначала заполните коллекцию MyCustomModel.");
            return;
        }

        System.out.println("Введите данные для подсчёта вхождений MyCustomModel:");
        T target = null;

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Number: ");
        String number = scanner.nextLine();
        System.out.print("Is it true: ");
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
            System.out.println("Коллекция MyCustomModel пуста (не инициализирована).");
        } else {
            System.out.println("Текущая коллекция MyCustomModel:");
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