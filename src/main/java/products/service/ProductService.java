package products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import products.domain.Product;
import products.repo.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> getPaginatedProducts(
        int start, int size, String productName,
        String sortBy, String direction) {

        if (sortBy != null) {
            var sortDirection = Sort.Direction.valueOf(direction.toUpperCase());
            return productRepository
                .findAllByNameContainingIgnoreCase(productName, PageRequest.of(start, size,
                Sort.by(sortDirection,sortBy)));
        }
        return productRepository.findAllByNameContainingIgnoreCase(productName, PageRequest.of(start, size));
    }

    public Long count() {
        return productRepository.count();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public String deleteById(Long id) {
        Optional<Product> optionalTeam = this.findById(id);
        if (optionalTeam.isPresent()) {
            productRepository.deleteById(id);
            return String.format("Product %s deleted", optionalTeam.get().getName());
        } else {
            return "Product not found";
        }
    }

    public Iterable<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }
}
