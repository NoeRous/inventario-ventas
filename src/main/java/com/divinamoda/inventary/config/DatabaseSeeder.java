package com.divinamoda.inventary.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.divinamoda.inventary.entity.Category;
import com.divinamoda.inventary.repository.CategoryRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository categoriaRepository;

    public DatabaseSeeder(CategoryRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() == 0) {
            Category c1 = new Category();
            c1.setName("BODY MANGA CORTA");

            Category c2 = new Category();
            c2.setName("BODY MANGA LARGA");

            Category c3 = new Category();
            c3.setName("CHALECOS");

            Category c4 = new Category();
            c4.setName("BODY CURVY");

            categoriaRepository.save(c1);
            categoriaRepository.save(c2);
            categoriaRepository.save(c3);
            categoriaRepository.save(c4);

            System.out.println("Seeder: Categorías iniciales creadas.");
        }
    }
}
