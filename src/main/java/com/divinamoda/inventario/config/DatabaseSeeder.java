package com.divinamoda.inventario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.divinamoda.inventario.entity.Categoria;
import com.divinamoda.inventario.repository.CategoriaRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;

    public DatabaseSeeder(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() == 0) {
            Categoria c1 = new Categoria();
            c1.setNombre("BODY MANGA CORTA");

            Categoria c2 = new Categoria();
            c2.setNombre("BODY MANGA LARGA");

            Categoria c3 = new Categoria();
            c3.setNombre("CHALECOS");

            Categoria c4 = new Categoria();
            c4.setNombre("BODY CURVY");

            categoriaRepository.save(c1);
            categoriaRepository.save(c2);
            categoriaRepository.save(c3);
            categoriaRepository.save(c4);

            System.out.println("Seeder: Categorías iniciales creadas.");
        }
    }
}
