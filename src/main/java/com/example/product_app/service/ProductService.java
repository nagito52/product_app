package com.example.product_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product_app.entity.Product;
import com.example.product_app.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Integer id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("指定IDの商品が存在しません:" + id));
	}

	public void save(Product product) {
		productRepository.save(product);
	}

	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}
}
