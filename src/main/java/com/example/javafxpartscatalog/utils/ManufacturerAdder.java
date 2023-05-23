package com.example.javafxpartscatalog.utils;

import com.example.javafxpartscatalog.dao.ManufacturerDAO;
import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.models.Manufacturer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerAdder {
    private static IManufacturerDAO service = new ManufacturerDAO();

    public static void main(String[] args) {
        String marks = "Hyundai, Volkswagen, BMW,  Kia, Toyota, Subaru";
        List<String> markList = Arrays.asList("Hyundai", "Volkswagen", "BMW",  "Kia", "Toyota", "Subaru");
        List<Manufacturer> res = markList.stream().map(Manufacturer::new).toList();
        res.forEach(manufacturer -> service.addManufacturer(manufacturer));
    }

}
