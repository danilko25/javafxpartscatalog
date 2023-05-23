package com.example.javafxpartscatalog.models;


import com.example.javafxpartscatalog.enums.PartCondition;

public class Part {
    private int id;
    private int manufacturerId;
    private String originalPartCode;
    private String name;
    private PartCondition condition;
    private int price;

    public Part(int id, int manufacturerId, String originalPartCode, String name, PartCondition condition, int price) {
        this.id = id;
        this.manufacturerId = manufacturerId;
        this.originalPartCode = originalPartCode;
        this.name = name;
        this.condition = condition;
        this.price = price;
    }

    public Part(int manufacturerId, String originalPartCode, String name, PartCondition condition, int price) {
        this.manufacturerId = manufacturerId;
        this.originalPartCode = originalPartCode;
        this.name = name;
        this.condition = condition;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getOriginalPartCode() {
        return originalPartCode;
    }

    public void setOriginalPartCode(String originalPartCode) {
        this.originalPartCode = originalPartCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartCondition getCondition() {
        return condition;
    }

    public void setCondition(PartCondition condition) {
        this.condition = condition;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", manufacturerId=" + manufacturerId +
                ", originalPartCode='" + originalPartCode + '\'' +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                ", price=" + price +
                '}';
    }
}
