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
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO part(manufacturer_id, original_part_code, name,  part_condition, price) VALUES (?,?,?,?,?)")) {
            PreparedStatement result = insertPartIntoPreparedStatement(statement, part);
            result.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Part> getAllParts() {
        List<Part> allParts = new ArrayList<>();
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM part")){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                allParts.add(getPartFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allParts;
    }

    @Override
    public Optional<Part> getPartById(int id) {
        Part result;
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM part WHERE id = ?")){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = getPartFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Part> getPartsByManufacturerName(String name) {
        List<Part> parts = new ArrayList<>();
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT part.id, part.manufacturer_id, part.original_part_code, part.name, part.part_condition, part.price FROM part inner JOIN manufacturer on part.manufacturer_id = manufacturer.id where manufacturer.name = ?")){
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                parts.add(getPartFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return parts;
    }

    @Override
    public List<Part> getPartByOriginalPartCode(String originalPartCode) {
        List<Part> parts = new ArrayList<>();
        try(Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM part WHERE original_part_code = ?")){
            statement.setString(1, originalPartCode);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                parts.add(getPartFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return parts;
    }

    @Override
    public void updateById(int id, Part part) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE part SET manufacturer_id = ?, original_part_code = ?, name = ?,  part_condition = ?, price = ? WHERE id = ?")){
            statement.setInt(6, id);
            PreparedStatement result = insertPartIntoPreparedStatement(statement, part);
            result.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM part WHERE id = ?")){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement insertPartIntoPreparedStatement(PreparedStatement statement, Part part) throws SQLException {
        statement.setInt(1, part.getManufacturerId());
        statement.setString(2, part.getOriginalPartCode());
        statement.setString(3, part.getName());
        statement.setString(4, part.getCondition().toString());
        statement.setInt(5, part.getPrice());
        return statement;
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
