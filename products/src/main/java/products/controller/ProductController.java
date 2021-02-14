package products.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import products.domain.Product;
import products.service.ProductService;

@RestController
@Log
@SuppressWarnings("unused")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/products")
    public ResponseEntity<Iterable<Product>> getPaginatedProducts(
        @RequestParam("page") Integer page,
        @RequestParam(value = "productName", defaultValue = "") String productName,
        @RequestParam(value = "sortBy", required = false) String sortBy,
        @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction,
        @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        var products = productService.getPaginatedProducts(page, size, productName, sortBy, direction);
        return ResponseEntity.ok(products);
    }

}
