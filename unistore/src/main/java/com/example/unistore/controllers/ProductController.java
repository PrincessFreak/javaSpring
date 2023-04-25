package com.example.unistore.controllers;

import com.example.unistore.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/productsList")
    public String getAllProducts(Model model) {
        model.addAttribute("product", productService.getAllProducts());
        return "/products/productsList";
    } // get all products on page

    @GetMapping("/productView/{id}")
    public String productView(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "/products/productView";
    }


}
