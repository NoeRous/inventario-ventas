package com.divinamoda.inventary.controller.products;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divinamoda.inventary.entity.products.Category;
import com.divinamoda.inventary.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // READ (todas las categorías)
    @GetMapping
    public List<Category> listAllCategories() {
        return categoryRepo.findAll();
    }
}
