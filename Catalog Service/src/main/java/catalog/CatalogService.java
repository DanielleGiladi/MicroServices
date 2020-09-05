package catalog;

import java.util.List;
import java.util.Optional;

public interface CatalogService {

	public Product createProduct(Product product);

	public Category createCategory(Category category);

	public List<Category> getAllCategories(int page, int size, String sort);

	public Optional<Product> getProductById(String id);

	public List<Product> getAllProducts(int page, int size, String sortBy);

	public List<Product> getAllProductsByName(int page, int size, String sort, String name);

	public List<Product> getAllProductsByMinPrice(int page, int size, String sortBy, String value);

	public List<Product> getAllProductsByMaxPrice(int page, int size, String sortBy, String value);

	public List<Product> getAllProductsByCategoryName(int page, int size, String sortBy, String value);

	public void deleteAll();
}
