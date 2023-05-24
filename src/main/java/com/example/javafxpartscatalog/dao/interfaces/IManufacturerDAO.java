package com.example.javafxpartscatalog.dao.interfaces;

import com.example.javafxpartscatalog.models.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface IManufacturerDAO {
    List<Manufacturer> getAllManufacturers();

    void addManufacturer(Manufacturer manufacturer);

    Optional<Manufacturer> getManufacturerById(int id);

    void updateManufacturerById(int id, Manufacturer manufacturer);

    void deleteById(int id);


}
