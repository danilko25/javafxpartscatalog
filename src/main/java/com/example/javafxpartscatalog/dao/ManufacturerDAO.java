package com.example.javafxpartscatalog.dao;

import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.db.DataBaseConfig;
import com.example.javafxpartscatalog.models.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDAO implements IManufacturerDAO {

    Connection connection;

    @Override
    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
         connection = DataBaseConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM manufacturer");

            Manufacturer manufacturer;
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                manufacturer = new Manufacturer(id, name);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return manufacturers;
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        connection = DataBaseConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO manufacturer(name) values (?)");
            statement.setString(1, manufacturer.getName());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<Manufacturer> getManufacturerById(int id) {
        return Optional.empty();
    }

    @Override
    public void updateManufacturerById(int id, Manufacturer manufacturer) {

    }

    @Override
    public void deleteById(int id) {

    }
}
