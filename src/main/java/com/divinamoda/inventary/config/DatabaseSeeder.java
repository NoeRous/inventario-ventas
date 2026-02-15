package com.divinamoda.inventary.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.divinamoda.inventary.entity.products.Category;
import com.divinamoda.inventary.repository.CategoryRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DatabaseSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category c1 = new Category();
            c1.setName("BODY MANGA CORTA");

            Category c2 = new Category();
            c2.setName("BODY MANGA LARGA");

            Category c3 = new Category();
            c3.setName("CHALECOS");

            Category c4 = new Category();
            c4.setName("BODY CURVY");

            categoryRepository.save(c1);
            categoryRepository.save(c2);
            categoryRepository.save(c3);
            categoryRepository.save(c4);

            System.out.println("Seeder: Categorías iniciales creadas.");
        }
    }
}
