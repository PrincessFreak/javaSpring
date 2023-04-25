package com.example.unistore.repositories;

import com.example.unistore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductTitleContainingIgnoreCase(String title);

    @Query(value = "select * from product where (lower(product_title) LIKE %?1%) or (lower(product_title) LIKE '?1%') OR (lower(product_title) LIKE '%?1') and (product_price >= ?2 and product_price <= ?3)", nativeQuery = true)
    List<Product> findByProductTitleAndProductPriceGreaterThanEqualAndProductPriceLessThanEqual(String title, float from, float to);

    @Query(value = "select * from product where (lower(product_title) LIKE %?1%) or (lower(product_title) LIKE '?1%') OR (lower(product_title) LIKE '%?1') and (product_price >= ?2 and product_price <= ?3) order by product_price", nativeQuery = true)
    List<Product> findByProductTitleOrderByProductPriceAsc(String title, float from, float to);

    @Query(value = "select * from product where (lower(product_title) LIKE %?1%) or (lower(product_title) LIKE '?1%') OR (lower(product_title) LIKE '%?1') and (product_price >= ?2 and product_price <= ?3) order by product_price desc", nativeQuery = true)
    List<Product> findByProductTitleOrderByProductPriceDesc(String title, float from, float to);

    @Query(value = "select * from product where category_id ?4 (lower(product_title) LIKE %?1%) or (lower(product_title) LIKE '?1%') OR (lower(product_title) LIKE '%?1') and (product_price >= ?2 and product_price <= ?3) order by product_price", nativeQuery = true)
    List<Product> findByProductTitleAAndCategoryOrderByProductPriceAsc(String title, float from, float to, int category);

    @Query(value = "select * from product where category_id ?4 (lower(product_title) LIKE %?1%) or (lower(product_title) LIKE '?1%') OR (lower(product_title) LIKE '%?1') and (product_price >= ?2 and product_price <= ?3) order by product_price desc", nativeQuery = true)
    List<Product> findByProductTitleAAndCategoryOrderByProductPriceDesc(String title, float from, float to, int category);
}
