package com.example.unistore.repositories;


import com.example.unistore.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByCategoryName(String name);
}
