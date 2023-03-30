package org.dev.backend.CAP.service;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.CategoriesDTO;
import org.dev.backend.CAP.repository.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public void save(CategoriesDTO categoriesDTO) {
        log.info("Saving category.");
        categoriesRepository.save(categoriesDTO);
    }

    public List<CategoriesDTO> getCategoriesList() {
        log.info("Finding categories...");
        return categoriesRepository.getCategories();
    }

    public CategoriesDTO getCategoryByName(String categoryName) {
        return categoriesRepository.getCategory(categoryName);
    }
}
