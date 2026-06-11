package com.github.murilo.repository;

import com.github.murilo.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public void save(Product product) {
        products.add(product);
    }

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public void delete(Product product) {
        products.remove(product);
    }
}