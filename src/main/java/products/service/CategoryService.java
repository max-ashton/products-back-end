package products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import products.domain.Category;
import products.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Long count(){
        return categoryRepository.count();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Iterable<Category> saveAll(List<Category> Categorys){
        return categoryRepository.saveAll(Categorys);
    }
}
