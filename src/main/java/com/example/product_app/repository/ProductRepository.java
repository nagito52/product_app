package com.example.product_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
