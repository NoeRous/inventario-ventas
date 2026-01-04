package com.divinamoda.inventary.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divinamoda.inventary.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
