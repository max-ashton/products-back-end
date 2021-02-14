package products.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import products.domain.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
