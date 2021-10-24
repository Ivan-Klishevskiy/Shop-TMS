package by.teachmeskills.model;

import by.teachmeskills.jdbc_connection.JdbcConnection;
import by.teachmeskills.repository.BaseRepository;
import by.teachmeskills.repository.impl.ProductsRepositoryImpl;
import by.teachmeskills.service.BaseService;
import by.teachmeskills.service.impl.ProductsBaseServiceImpl;
import by.teachmeskills.service.impl.IOServiceImpl;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    IOServiceImpl ioService = new IOServiceImpl();

    public void start() {
        Shop shop = ioService.jsonToObject("src/main/resources/outputFiles/shopFile.json");

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
                            |_5)_Формирование отчета____________|
                            |_6)_Функции БД_____________________|
                            |_0)_Выход из программы_____________|
                            |***********************************|
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
                    new Thread(()->ioService.writeInFile("src/main/resources/outputFiles/report.txt", finalShop.getList().toString())).start();
                }
                case 6 -> sqlFunction(shop);
                case 0 -> {
                    ioService.objectToJson(shop, "src/main/resources/outputFiles/shopFile.json");
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

    private void sqlFunction(Shop shop){
        JdbcConnection jdbcCon=new JdbcConnection();
        BaseRepository repository = new ProductsRepositoryImpl(jdbcCon);
        BaseService<Product>service=new ProductsBaseServiceImpl(repository);
        System.out.print(
                """
                        **************************************
                        |_1)_Сохранить все обьекты в БД______|
                        |_2)_Удалить продукт из БД___________|
                        |_3)_Найти продукт по id_____________|
                        |_4)_Обновить продукт в БД___________|
                        |_5)_Вывести все продукты из БД______|
                        |_6)_Добавить продукт в БД___________|
                        **************************************
                        |->
                        """

        );
        switch (ioService.getInt(0, 6)) {
            case 1->{
                for (Product product : shop.getList()) {
                    service.save(product);
                }
            }
            case 2->{
                System.out.println("Введите Id удаляемого товара:");
                System.out.println(service.deleteById(ioService.getInt(Integer.MIN_VALUE, Integer.MAX_VALUE)));
            }
            case 3->{
                System.out.println("Введите Id искомого товара:");
                System.out.println(service.find(ioService.getInt(Integer.MIN_VALUE, Integer.MAX_VALUE)));
            }
            case 4->{
                System.out.print("Введите название нового продукта: ");
                String name = ioService.getString();

                System.out.print("Введите цену нового продукта: ");
                int price = ioService.getInt(0, Integer.MAX_VALUE);

                System.out.print("Введите id нового продукта: ");
                int id = ioService.getInt(0, Integer.MAX_VALUE);

                Product temp=new Product(id, name, price, LocalTime.now());

                System.out.println(service.update(temp));
            }
            case 5->{
                List<Product> allProducts = service.findAll();
                for (Product product : allProducts) {
                    System.out.println(product);
                }
            }
            case 6->{
                System.out.print("Введите название нового продукта: ");
                String name = ioService.getString();

                System.out.print("Введите цену нового продукта: ");
                int price = ioService.getInt(0, Integer.MAX_VALUE);

                System.out.print("Введите id нового продукта: ");
                int id = ioService.getInt(0, Integer.MAX_VALUE);

                Product temp=new Product(id, name, price, LocalTime.now());

                System.out.println(service.save(temp));
            }
        }
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
            new Thread(
                    ()->ioService.writeInFile("src/main/resources/outputFiles/ListProduct.txt", shop
                    .getList()
                    .stream()
                    .filter(x -> x.getPrice() >= lowerBound)
                    .filter(i -> i.getPrice() <= upperBound)
                    .map(Product::toString)
                    .collect(Collectors.toList())
                    .toString()))
                    .start();
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
            new Thread(()->ioService.writeInFile("src/main/resources/outputFiles/ListProduct.txt", shop.toString())).start();
        }
    }
}
