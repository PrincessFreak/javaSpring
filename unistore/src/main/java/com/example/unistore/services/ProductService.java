package com.example.unistore.services;

import com.example.unistore.models.Product;
import com.example.unistore.models.ProductCategory;
import com.example.unistore.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    } // get all products

    public Product getProductById(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    } // get product by id

    @Transactional
    public void addProduct(Product product, ProductCategory productCategory) {
        product.setCategory(productCategory);
        productRepository.save(product);
    } // add product

    @Transactional
    public void productEdit(int id, Product product) {
        product.setId(id);
        productRepository.save(product);
    } // edit product

    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    } // delete product

    public List<Product> getProductTitleContainingIgnoreCase(String title) {
        return productRepository.findByProductTitleContainingIgnoreCase(title);
    }

    public List<Product> getProductTitleAndProductPriceGreaterThanEqualAndProductPriceLessThanEqual(String title, float from, float to) {
        return productRepository.findByProductTitleAndProductPriceGreaterThanEqualAndProductPriceLessThanEqual(title, from, to);
    }

    public List<Product> getProductTitleOrderByProductPriceAsc(String title, float from, float to) {
        return productRepository.findByProductTitleOrderByProductPriceAsc(title, from, to);
    }

    public List<Product> getProductTitleOrderByProductPriceDesc(String title, float from, float to) {
        return productRepository.findByProductTitleOrderByProductPriceDesc(title, from, to);
    }

    public List<Product> getProductTitleAAndCategoryOrderByProductPriceAsc(String title, float from, float to, int category) {
        return productRepository.findByProductTitleAAndCategoryOrderByProductPriceAsc(title, from, to, category);
    }

    public List<Product> getProductTitleAAndCategoryOrderByProductPriceDesc(String title, float from, float to, int category) {
        return productRepository.findByProductTitleAAndCategoryOrderByProductPriceDesc(title, from, to, category);
    }
}
