package com.example.javafxpartscatalog.dao;

import com.example.javafxpartscatalog.dao.interfaces.IManufacturerDAO;
import com.example.javafxpartscatalog.db.DataBaseConfig;
import com.example.javafxpartscatalog.models.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDAO implements IManufacturerDAO {

    @Override
    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM manufacturer")){
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
        }
        return manufacturers;
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        try(Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO manufacturer(name) values (?)")) {
            statement.setString(1, manufacturer.getName());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Manufacturer> getManufacturerById(int id) {
        Manufacturer result;
        try(Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM manufacturer WHERE id = ?")) {
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = getManufacturerFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void updateManufacturerById(int id, Manufacturer manufacturer) {
        try(Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE manufacturer SET name = ? where id = ?")) {
            statement.setString(1, manufacturer.getName());
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM manufacturer WHERE id = ?")){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Manufacturer getManufacturerFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        return new Manufacturer(id, name);
    }
}
