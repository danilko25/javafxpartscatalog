package com.example.javafxpartscatalog.models.forJavaFxTableView;

import com.example.javafxpartscatalog.enums.PartCondition;
import com.example.javafxpartscatalog.models.Part;

public class FXPart {
    private int id;
    private int manufacturerId;
    private String originalPartCode;
    private String name;
    private String condition;
    private int price;

    public FXPart() {
    }

    public FXPart(Part part){
        this.id = part.getId();
        this.manufacturerId = part.getManufacturerId();
        this.originalPartCode = part.getOriginalPartCode();
        this.name = part.getName();
        this.condition = part.getCondition().toString();
        this.price = part.getPrice();
    }

    public FXPart(int id, int manufacturerId, String originalPartCode, String name, String condition, int price) {
        this.id = id;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
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
        return "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price;
    }
}


