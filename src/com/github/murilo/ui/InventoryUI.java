package com.github.murilo.ui;

import com.github.murilo.domain.Product;
import com.github.murilo.service.ProductService;

import java.util.Scanner;

public class InventoryUI {

    private final ProductService service;
    private final Scanner scanner;

    public InventoryUI(ProductService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getUserChoice();
            running = processChoice(choice);
        }
    }

    private void showMenu() {
        System.out.println("\n=== Inventory Management System ===");
        System.out.println("1. Register Product");
        System.out.println("2. List Products");
        System.out.println("3. Find Product by ID");
        System.out.println("4. Remove Product");
        System.out.println("5. Update Product");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean processChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> registerProduct();
                case 2 -> listProducts();
                case 3 -> findProduct();
                case 4 -> removeProduct();
                case 5 -> updateProduct();
                case 6 -> {
                    System.out.println("Shutting down system...");
                    return false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }

    private void registerProduct() {
        System.out.print("Enter ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        Product product = new Product(id, name, price, quantity);
        service.register(product);
        System.out.println("Product registered successfully!");
    }

    private void listProducts() {
        var products = service.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products registered.");
            return;
        }
        products.forEach(p -> System.out.printf("ID: %d | Name: %s | Price: $%.2f | Qty: %d%n",
                p.getId(), p.getName(), p.getPrice(), p.getQuantity()));
    }

    private void findProduct() {
        System.out.print("Enter ID to search: ");
        Long id = Long.parseLong(scanner.nextLine());
        Product p = service.getProductById(id);
        System.out.printf("Found -> ID: %d | Name: %s | Price: $%.2f | Qty: %d%n",
                p.getId(), p.getName(), p.getPrice(), p.getQuantity());
    }

    private void removeProduct() {
        System.out.print("Enter ID to remove: ");
        Long id = Long.parseLong(scanner.nextLine());
        service.removeProduct(id);
        System.out.println("Product removed successfully!");
    }

    private void updateProduct() {
        System.out.print("Enter ID of the product to update: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter new Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        service.updateProduct(id, name, price, quantity);
        System.out.println("Product updated successfully!");
    }
}