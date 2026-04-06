package com.nnson128.myapplication;

public class Product {
    private String id;
    private String name;
    private long price;
    private String category;

    public Product(String id, String name, long price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Mã: " + id + ", Tên: " + name + ", Giá: " + price + ", Loại: " + category;
    }
}
