package com.example.javafxpartscatalog.dao;

import com.example.javafxpartscatalog.dao.interfaces.IPartDAO;
import com.example.javafxpartscatalog.db.DataBaseConfig;
import com.example.javafxpartscatalog.enums.PartCondition;
import com.example.javafxpartscatalog.models.Part;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartDAO implements IPartDAO {
    @Override
    public void addPart(Part part) {

    }

    @Override
    public List<Part> getAllParts() {
        return null;
    }

    @Override
    public Optional<Part> getPartById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Part> getPartsByManufacturerName(String name) {
        Connection connection = DataBaseConfig.getConnection();
        List<Part> parts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT part.id, part.manufacturer_id, part.original_part_code, part.name, part.part_condition, part.price FROM part inner JOIN manufacturer on part.manufacturer_id = manufacturer.id where manufacturer.name = ?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                parts.add(getPartFromResultSet(rs));
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
        return parts;
    }

    @Override
    public List<Part> getPartByOriginalPartCode(String originalPartCode) {
        return null;
    }

    @Override
    public void updateById(int id, Part part) {

    }

    @Override
    public void deleteById(int id) {

    }

    private Part getPartFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int manufacturerId = rs.getInt(2);
        String originalPartCode = rs.getString(3);
        String name = rs.getString(4);
        PartCondition condition = PartCondition.valueOf(rs.getString(5));
        int price = rs.getInt(6);
        return new Part(id, manufacturerId, originalPartCode, name, condition, price);
    }
}
