package com.github.murilo.service;

import com.github.murilo.domain.Product;
import com.github.murilo.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void register(Product product) {
        validateProduct(product);
        ensureIdIsUnique(product.getId());
        repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
    }

    public void removeProduct(Long id) {
        Product product = getProductById(id);
        repository.delete(product);
    }

    public void updateProduct(Long id, String newName, double newPrice, int newQuantity) {
        Product product = getProductById(id);

        Product tempValidator = new Product(id, newName, newPrice, newQuantity);
        validateProduct(tempValidator);

        product.setName(newName);
        product.setPrice(newPrice);
        product.setQuantity(newQuantity);
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative.");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative.");
        }
    }

    private void ensureIdIsUnique(Long id) {
        if (repository.findById(id).isPresent()) {
            throw new IllegalArgumentException("A product with this ID already exists.");
        }
    }
}