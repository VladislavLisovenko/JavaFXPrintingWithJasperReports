package ru.vlsoft.models;

public class Product extends BaseObject {

    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return getName();
    }
}
