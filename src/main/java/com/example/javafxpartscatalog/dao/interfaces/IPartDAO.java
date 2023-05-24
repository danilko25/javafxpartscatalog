package com.example.javafxpartscatalog.dao.interfaces;

import com.example.javafxpartscatalog.models.Part;

import java.util.List;
import java.util.Optional;

public interface IPartDAO {

    void addPart(Part part);
    List<Part> getAllParts();

    Optional<Part> getPartById(int id);

    List<Part> getPartByOriginalPartCode(String originalPartCode);

    void updateById(int id, Part part);

    void deleteById(int id);

}
