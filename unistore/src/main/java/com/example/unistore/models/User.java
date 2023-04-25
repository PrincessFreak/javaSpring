package com.example.unistore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Введите фамилию")
    @Size(min = 1, max = 30, message = "Длина фамилии не может превышать 30 символов")
    @Column(name = "second_name")
    private String secondName;

    @NotEmpty(message = "Введите имя")
    @Size(min = 1, max = 20, message = "Длина имени не может превышать 20 символов")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Введите логин")
    @Size(min = 5, max = 30, message = "Логин должен быть в диапазоне от 5 до 30 символов")
    @Column(name = "user_login")
    private String userLogin;

    @NotEmpty(message = "Введите пароль")
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(secondName, user.secondName) && Objects.equals(firstName, user.firstName) && Objects.equals(userLogin, user.userLogin) && Objects.equals(userPassword, user.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secondName, firstName, userLogin, userPassword);
    }

    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orderList;
}
