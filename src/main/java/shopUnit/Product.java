package shopUnit;

import java.io.Serializable;
import java.time.LocalTime;

public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private LocalTime historyOfAdding;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public LocalTime getHistoryOfAdding() {
        return historyOfAdding;
    }

    public void setHistoryOfAdding(LocalTime historyOfAdding) {
        this.historyOfAdding = historyOfAdding;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Название: " + name +
                " Цена: " + price +
                " ID: " + id + "\n";
    }
}
