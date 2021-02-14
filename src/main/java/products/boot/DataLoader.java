package products.boot;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import products.domain.Category;
import products.domain.Product;
import products.service.CategoryService;
import products.service.ProductService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class DataLoader implements InitializingBean {

    private ProductService productService;
    private CategoryService categoryService;

    final private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");

    @Autowired
    public DataLoader(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Long count = categoryService.count();
        if (count == 0) {
            loadCategoryDataFromCSV();
        }
        count = productService.count();
        if (count == 0) {
            loadProductDataFromCSV();
        }
    }

    private void loadCategoryDataFromCSV() throws IOException {
        File file = ResourceUtils.getFile("classpath:categories.csv");
        var in = new FileReader(file);
        String[] HEADERS = {"ID", "CATEGORY_NAME"};
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withHeader(HEADERS)
            .withFirstRecordAsHeader()
            .parse(in);
        var categories = new ArrayList<Category>();
        for (CSVRecord record : records) {
            String id = record.get("ID");
            String category_name = record.get("CATEGORY_NAME");
            categories.add(new Category(Long.valueOf(id), category_name));
        }
        categoryService.saveAll(categories);
    }

    private void loadProductDataFromCSV() throws IOException, ParseException {
        File file = ResourceUtils.getFile("classpath:products.csv");
        var in = new FileReader(file);
        String[] HEADERS = {"ID", "NAME", "DESCRIPTION", "CATEGORY_ID", "CREATION_DATE", "UPDATE_DATE",
            "LAST_PURCHASED_DATE"};
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withHeader(HEADERS)
            .withFirstRecordAsHeader()
            .parse(in);

        var products = new ArrayList<Product>();
        for (CSVRecord r : records) {
            Optional<Category> category = categoryService.findById(Long.valueOf(r.get("CATEGORY_ID")));
            Product p = new Product(
                Long.valueOf(r.get("ID")),
                r.get("NAME"),
                r.get("DESCRIPTION"),
                dateFormat.parse(r.get("UPDATE_DATE")),
                dateFormat.parse(r.get("CREATION_DATE")),
                dateFormat.parse(r.get("LAST_PURCHASED_DATE")),
                category.get()
            );

            products.add(p);
        }
        productService.saveAll(products);
    }
}
