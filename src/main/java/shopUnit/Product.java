package shopUnit;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.Serializable;
import java.time.LocalTime;

public class Product implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private int price;

    @JsonProperty("LocalTime")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime historyOfAdding;

    public Product() {

    }

    public Product(int id, String name, int price, LocalTime historyOfAdding) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.historyOfAdding = historyOfAdding;

    }

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
