package com.example.javafxpartscatalog.dao;

import com.example.javafxpartscatalog.db.DataBaseConfig;
import com.example.javafxpartscatalog.enums.PartCondition;
import com.example.javafxpartscatalog.models.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PartDAOTest {

    PartDAO partDAO;

    @BeforeEach
    void setPartDAO(){
        partDAO = new PartDAO();
    }
    @Test
    void addPart() {
        Part partToAdding = new Part(3, "addingTestCode", "TestName", PartCondition.DAMAGED, 1000);
        int maxIdBeforeAdd = getMaxIdFromTablePart();
        partDAO.addPart(partToAdding);
        int maxIdAfterAdd = getMaxIdFromTablePart();
        assertEquals(1, maxIdAfterAdd-maxIdBeforeAdd);
    }

    @Test
    void getAllParts() {
        partDAO.getAllParts().forEach(System.out::println);
    }

    @Test
    void getPartById() {
        Part partToAdding = new Part(1, "getByPartIdTestCode", "TestNameGetById", PartCondition.NEW, 1111);
        partDAO.addPart(partToAdding);
        Optional<Part> optionalPart = partDAO.getPartById(getMaxIdFromTablePart());
        if (optionalPart.isPresent()){
            Part partFromOptional = optionalPart.get();
            assertEquals(partToAdding.getManufacturerId(), partFromOptional.getManufacturerId());
            assertEquals(partToAdding.getOriginalPartCode(), partFromOptional.getOriginalPartCode());
            assertEquals(partToAdding.getName(), partFromOptional.getName());
            assertEquals(partToAdding.getCondition(), partFromOptional.getCondition());
            assertEquals(partToAdding.getPrice(), partFromOptional.getPrice());
        }
    }

    @Test
    void getPartsByManufacturerName() {
        String manufacturerName = "BMW";
        List<Part> parts = partDAO.getPartsByManufacturerName(manufacturerName);
        for (int i = 0; i<parts.size(); i++){
            assertEquals(manufacturerName, getManufacturerNameById(parts.get(i).getManufacturerId()));
        }
    }

    @Test
    void getPartByOriginalPartCode() {
        String partCode = "Test part code 2";
        Part part = new Part(1, "Test part code 3", "testName", PartCondition.USED, 500);
        partDAO.addPart(part);
        List<Part> foundedParts = partDAO.getPartByOriginalPartCode(partCode);
        assertTrue(foundedParts.size()>0);
        for (int i = 0; i <foundedParts.size(); i++){
            assertEquals(partCode, foundedParts.get(i).getOriginalPartCode());
        }
    }

    @Test
    void updateById() {
        Part part = new Part(1, "1", "1", PartCondition.DAMAGED, 1);
        Part partForUpdate = new Part(2, "2", "2", PartCondition.NEW, 2);
        Part falseResultForUpdate = new Part(3, "3", "3", PartCondition.USED, 3);
        partDAO.addPart(part);
        partDAO.updateById(getMaxIdFromTablePart(), partForUpdate);
        Part updatedPart = partDAO.getPartById(getMaxIdFromTablePart()).get();
        assertEquals(partForUpdate.getManufacturerId(), updatedPart.getManufacturerId());
        assertEquals(partForUpdate.getOriginalPartCode(), updatedPart.getOriginalPartCode());
        assertEquals(partForUpdate.getName(), updatedPart.getName());
        assertEquals(partForUpdate.getCondition(), updatedPart.getCondition());
        assertEquals(partForUpdate.getPrice(), updatedPart.getPrice());
    }

    @Test
    void deleteById() {
        int sizeBeforeDelete = partDAO.getAllParts().size();
        partDAO.deleteById(getMaxIdFromTablePart());
        int sizeAfterDelete = partDAO.getAllParts().size();
        assertEquals(1, sizeBeforeDelete-sizeAfterDelete);
    }

    int getMaxIdFromTablePart(){
        int maxId = -1;
        try (Statement statement = DataBaseConfig.getConnection().createStatement();){
            ResultSet rs = statement.executeQuery("SELECT MAX(id) FROM part");
            rs.next();
            maxId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                DataBaseConfig.getConnection().close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return maxId;
    }

    private String getManufacturerNameById(int id){
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT name FROM manufacturer WHERE id = ?")){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}