package com.github.murilo;

import com.github.murilo.repository.ProductRepository;
import com.github.murilo.service.ProductService;
import com.github.murilo.ui.InventoryUI;

public class Main {
    public static void main(String[] args) {
        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService(repository);
        InventoryUI ui = new InventoryUI(service);

        ui.start();
    }
}