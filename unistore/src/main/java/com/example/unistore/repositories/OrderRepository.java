package com.example.unistore.repositories;

import com.example.unistore.models.Order;
import com.example.unistore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
