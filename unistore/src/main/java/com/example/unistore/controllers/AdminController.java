package com.example.unistore.controllers;

import com.example.unistore.models.Image;
import com.example.unistore.models.Product;
import com.example.unistore.models.ProductCategory;
import com.example.unistore.repositories.CategoryRepository;
import com.example.unistore.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    public AdminController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/users/adminIndex")
    public String adminLogin(Model model) {
        model.addAttribute("product", productService.getAllProducts());
        return "/users/adminIndex";
    } //

    @GetMapping("/productAdd")
    public String productAdd(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "/products/productAdd";
    } // admins add product

    @PostMapping("/products/productAdd")
    public String productAdd(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("image_one")MultipartFile image_one, @RequestParam("image_two")MultipartFile image_two, @RequestParam("image_three")MultipartFile image_three, @RequestParam("image_four")MultipartFile image_four, @RequestParam("image_five")MultipartFile image_five, @RequestParam("category") int category, Model model) throws IOException {
        ProductCategory category_db = (ProductCategory) categoryRepository.findById(category).orElseThrow();
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "/products/productAdd";
        }
        if (image_one != null) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            String uuidFileName = UUID.randomUUID().toString();
            String fileName = uuidFileName + "." + image_one.getOriginalFilename();
            image_one.transferTo(new File(uploadPath + "/" + fileName));
            Image image = new Image();
            image.setProduct(product);
            image.setImageName(fileName);
            product.addImageToProduct(image);
        }
        if (image_two != null) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            String uuidFileName = UUID.randomUUID().toString();
            String fileName = uuidFileName + "." + image_two.getOriginalFilename();
            image_two.transferTo(new File(uploadPath + "/" + fileName));
            Image image = new Image();
            image.setProduct(product);
            image.setImageName(fileName);
            product.addImageToProduct(image);
        }
        if (image_three != null) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            String uuidFileName = UUID.randomUUID().toString();
            String fileName = uuidFileName + "." + image_three.getOriginalFilename();
            image_three.transferTo(new File(uploadPath + "/" + fileName));
            Image image = new Image();
            image.setProduct(product);
            image.setImageName(fileName);
            product.addImageToProduct(image);
        }
        if (image_four != null) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            String uuidFileName = UUID.randomUUID().toString();
            String fileName = uuidFileName + "." + image_four.getOriginalFilename();
            image_four.transferTo(new File(uploadPath + "/" + fileName));
            Image image = new Image();
            image.setProduct(product);
            image.setImageName(fileName);
            product.addImageToProduct(image);
        }
        if (image_five != null) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            String uuidFileName = UUID.randomUUID().toString();
            String fileName = uuidFileName + "." + image_five.getOriginalFilename();
            image_five.transferTo(new File(uploadPath + "/" + fileName));
            Image image = new Image();
            image.setProduct(product);
            image.setImageName(fileName);
            product.addImageToProduct(image);
        }
        productService.addProduct(product, category_db);
        return "redirect:/users/adminIndex";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/users/adminIndex";
    }

    @GetMapping("/products/productEdit/{id}")
    public String productEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "/products/productEdit";
    }

    @PostMapping("/products/productEdit/{id}")
    public String productEdit(@PathVariable("id") int id, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/products/productEdit";
        }
        model.addAttribute("category", categoryRepository.findAll());
        productService.productEdit(id, product);
        return "redirect:/users/adminIndex";
    }
}
