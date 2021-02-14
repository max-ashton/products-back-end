package products.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import products.domain.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

}
