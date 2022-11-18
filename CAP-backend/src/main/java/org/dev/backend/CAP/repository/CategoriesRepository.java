package org.dev.backend.CAP.repository;

import org.dev.api.CAP.model.CategoriesDTO;

import java.util.List;

public interface CategoriesRepository {
    void save (CategoriesDTO categoriesDTO);

    List<CategoriesDTO> getCategories();

    void delete (String username);
}
