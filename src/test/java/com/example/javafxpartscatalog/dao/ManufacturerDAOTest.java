package com.example.javafxpartscatalog.dao;

import com.example.javafxpartscatalog.db.DataBaseConfig;
import com.example.javafxpartscatalog.models.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class ManufacturerDAOTest {
    ManufacturerDAO dao;

    @BeforeEach
    void daoCreating(){
        dao = new ManufacturerDAO();
    }

    @Test
    void getAllManufacturers() {
        List<Manufacturer> manufacturers = dao.getAllManufacturers();
        for (Manufacturer manufacturer:manufacturers) System.out.println(manufacturer.getId() + " " + manufacturer.getName());
    }

    @Test
    void addManufacturer() {
        Manufacturer manufacturerForTest = new Manufacturer("Unique");
        ManufacturerDAO dao = new ManufacturerDAO();
        List<Manufacturer> manufacturersBeforeAdd = dao.getAllManufacturers();
        dao.addManufacturer(manufacturerForTest);
        List<Manufacturer> manufacturersAfterAdd = dao.getAllManufacturers();
        assertEquals(1, (manufacturersAfterAdd.size()) - (manufacturersBeforeAdd.size()));
    }

    @Test
    void getManufacturerById() {
        ManufacturerDAO dao = new ManufacturerDAO();
        Manufacturer manufacturer = new Manufacturer("NissanUpdated");
        dao.addManufacturer(manufacturer);
        int maxId = getMaxIdFromTableManufacturer();
        Optional<Manufacturer> result = dao.getManufacturerById(maxId);
        if (result.isPresent()){
            assertEquals(manufacturer.getName(), result.get().getName());
            System.out.println("Success");
        }
    }

    @Test
    void updateManufacturerById() {
        ManufacturerDAO dao = new ManufacturerDAO();
        Manufacturer manufacturerForUpdate = new Manufacturer("Update");
        int maxId = getMaxIdFromTableManufacturer();
        dao.updateManufacturerById(maxId, manufacturerForUpdate);
        Optional<Manufacturer> result = dao.getManufacturerById(maxId);
        if (result.isPresent()){
            assertEquals(manufacturerForUpdate.getName(), result.get().getName());
            System.out.println("Success");
        }
    }

    @Test
    void deleteById() {
        int maxIdBeforeDelete = getMaxIdFromTableManufacturer();
        dao.deleteById(maxIdBeforeDelete);
        int maxIdAfterDelete = getMaxIdFromTableManufacturer();
        assertEquals(1, maxIdBeforeDelete-maxIdAfterDelete);
    }

    int getMaxIdFromTableManufacturer(){
        int maxId = -1;
        try (Statement statement = DataBaseConfig.getConnection().createStatement();){
            ResultSet rs = statement.executeQuery("SELECT MAX(id) FROM manufacturer");
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
}