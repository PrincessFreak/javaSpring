package com.example.unistore.models;

import jakarta.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imageName;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Image() {
    }

    public Image(int id, String imageName, Product product) {
        this.id = id;
        this.imageName = imageName;
        this.product = product;
    }
}
