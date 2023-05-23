package com.example.javafxpartscatalog.dao.interfaces;

import com.example.javafxpartscatalog.models.Manufacturer;

import java.util.List;

public interface IManufacturerDAO {
    List<Manufacturer> getAllManufacturers();

    void addManufacturer(Manufacturer manufacturer);


}
