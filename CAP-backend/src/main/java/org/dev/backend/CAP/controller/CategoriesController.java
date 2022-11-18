package org.dev.backend.CAP.controller;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.CategoriesDTO;
import org.dev.backend.CAP.service.CategoriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @PutMapping
    void save(CategoriesDTO categoriesDTO){
        categoriesService.save(categoriesDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategoriesDTO>> findAll() {
        return new ResponseEntity<>(categoriesService.getCategoriesList(), HttpStatus.OK);
    }
}
