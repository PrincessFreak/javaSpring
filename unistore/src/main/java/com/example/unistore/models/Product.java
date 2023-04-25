package com.example.unistore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Введите наименование товара")
    @Column(name = "product_title", nullable = false, columnDefinition = "text", unique = true)
    private String productTitle;

    @Column(name = "product_description", columnDefinition = "text")
    private String productDescription;

    @Min(value = 1, message = "Стоимость не может быть меньше 1")
    @Column(name = "product_price", nullable = false)
    private float productPrice;

    @NotEmpty(message = "Добавьте склад")
    @Column(name = "warehouse", nullable = false)
    private String warehouse;

    @NotEmpty(message = "Добавьте продавца")
    @Column(name = "seller", nullable = false)
    private String seller;

    @ManyToOne(optional = false)
    private ProductCategory category;

    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> imageList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Product() {
    }

    public Product(String productTitle, String productDescription, float productPrice, String warehouse, String seller, ProductCategory category, LocalDateTime dateTime, List<Image> imageList) {
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.warehouse = warehouse;
        this.seller = seller;
        this.category = category;
        this.dateTime = dateTime;
        this.imageList = imageList;
    }

    @PrePersist
    private void initialDate() {
        dateTime = LocalDateTime.now();
    } // add date when was product added

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        imageList.add(image);
    } // add image to current product

    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "product_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Order> orderList;
}
