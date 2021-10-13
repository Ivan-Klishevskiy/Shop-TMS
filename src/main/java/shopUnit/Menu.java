package shopUnit;

import java.time.LocalTime;
import java.util.stream.Collectors;

public class Menu {
    IOService ioService = new IOService();

    public void start() {
        Shop shop = ioService.jsonToObject("src/main/java/shopUnit/shopFile.json");

        if (shop == null) {
            shop = new Shop();
        }

        do {
            System.out.print(
                    """
                            |***************MENU****************|
                            |_1)_Добавить товар_________________|
                            |_2)_Вывод всех товаров_____________|
                            |_3)_Редактировать товар____________|
                            |_4)_Удалить товар__________________|
                            |-5)_Формирование отчета____________|
                            |_0)_Выход из программы_____________|
                            *************************************
                            """
            );
            System.out.print("|->");

            switch (ioService.getInt(0, 6)) {
                case 1 -> addingFunction(shop);
                case 2 -> printFunction(shop);
                case 3 -> editFunction(shop);
                case 4 -> removeFunction(shop);
                case 5 -> {
                    Shop finalShop = shop;
                    Runnable runnable = () -> {
                        ioService.writeInFile("src/main/java/shopUnit/report.txt", finalShop.getList().toString());
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
                case 0 -> {
                    ioService.objectToJson(shop, "src/main/java/shopUnit/shopFile.json");
                    System.out.println("Завершение программы...");
                    return;
                }
            }

            System.out.println("Нажмите Enter для продолжения");
            try {
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n");

        } while (true);
    }

    private void removeFunction(Shop shop) {
        System.out.print("Id всех товаров: " + "\n" + shop.getAllId() + "\n" + "Введите Id товара: ");
        shop.removeProduct(ioService.getInt(0, Integer.MAX_VALUE));
    }

    private void editFunction(Shop shop) {
        System.out.print("Id всех товаров: " + "\n" + shop.getAllId() + "\n" + "Введите Id товара: ");
        int id = ioService.getInt(0, Integer.MAX_VALUE);

        System.out.println("Введите название товара: ");
        String name = ioService.getString();

        System.out.print("Введите цену продукта: ");
        int price = ioService.getInt(0, Integer.MAX_VALUE);
        shop.editProduct(new Product(id, name, price));
    }

    private void addingFunction(Shop shop) {
        System.out.print("Введите название нового продукта: ");
        String name = ioService.getString();

        System.out.print("Введите цену нового продукта: ");
        int price = ioService.getInt(0, Integer.MAX_VALUE);

        System.out.print("Введите id нового продукта: ");
        int id = ioService.getInt(0, Integer.MAX_VALUE);

        shop.addProduct(new Product(id, name, price, LocalTime.now()));
    }

    private void sortByRange(Shop shop, int outputLocation) {
        System.out.print("Введите нижнюю границу: ");
        int lowerBound = ioService.getInt(0, Integer.MAX_VALUE);

        System.out.print("Введите верхнюю границу: ");
        int upperBound = ioService.getInt(0, Integer.MAX_VALUE);

        if (outputLocation == 1) {
            shop.getList().stream()
                    .filter(x -> x.getPrice() >= lowerBound)
                    .filter(i -> i.getPrice() <= upperBound)
                    .forEach(System.out::print);
        } else {
            ioService.writeInFile("src/ShopUnit12/ListProduct.txt", shop
                    .getList()
                    .stream()
                    .filter(x -> x.getPrice() >= lowerBound)
                    .filter(i -> i.getPrice() <= upperBound)
                    .map(Product::toString)
                    .collect(Collectors.toList())
                    .toString());
        }

    }

    private void printFunction(Shop shop) {
        System.out.print(
                """
                        **************************************
                        |_1)_Вывод в консоль_________________|
                        |_2)_Вывод в файл____________________|
                        **************************************
                        |->
                        """

        );

        int outputLocation = ioService.getInt(1, 2);

        System.out.println("""
                ******************Сортировка вывода********************
                |_1)_По цене (возрастание)____________________________|
                |_2)_По цене (убывание)_______________________________|
                |_3)_По добавлению(сначала новые, потом более старые)_|
                |_4)_Диапазон цены____________________________________|
                *******************************************************
                |->
                """
        );

        switch (ioService.getInt(1, 4)) {
            case 1 -> shop.sortByPriceAscending();
            case 2 -> shop.sortByPriceDecreasing();
            case 3 -> shop.sortByHistory();
            case 4 -> {
                sortByRange(shop, outputLocation);
                return;
            }
        }

        if (outputLocation == 1) {
            shop.printList();
        } else {
            new Thread(()->ioService.writeInFile("src/main/java/shopUnit/ListProduct.txt", shop.toString())).start();
        }
    }
}
