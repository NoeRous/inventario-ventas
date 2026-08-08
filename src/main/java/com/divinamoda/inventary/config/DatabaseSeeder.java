package com.divinamoda.inventary.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.divinamoda.inventary.entity.auth.User;
import com.divinamoda.inventary.entity.products.Category;
import com.divinamoda.inventary.enums.Role;
import com.divinamoda.inventary.repository.CategoryRepository;
import com.divinamoda.inventary.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(CategoryRepository categoryRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setFullName("Administrador");
            admin.setEmail("admin@divinamoda.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);

            System.out.println("Seeder: Usuario admin creado (admin@divinamoda.com / admin123).");
        }

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
