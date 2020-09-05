package catalog;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogServiceImp implements CatalogService {
	private ProductCrud productCrud;
	private CategoryCrud categoryCrud;
	private AtomicLong atomicId;

	@Autowired
	public CatalogServiceImp(ProductCrud productCrud, CategoryCrud categoryCrud) {
		super();
		this.productCrud = productCrud;
		this.categoryCrud = categoryCrud;
		this.atomicId = new AtomicLong(1L);
	}

	@Override
	@Transactional
	public Category createCategory(Category category) {
		if (categoryCrud.existsById(category.getName()))
			throw new RuntimeException("Category already exists");
		String parent = category.getParentCategory();
		if (parent != null) {
			if (!categoryCrud.existsById(parent))
				throw new RuntimeException("Category parent " + parent + " not exists");
			else {
				while(parent != null) {
					categoryCrud.findById(parent).get().addChild(category);
					parent = categoryCrud.findById(parent).get().getParentCategory();
				}
			}
		}
		return this.categoryCrud.save(category);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> getAllCategories(int page, int size, String sortBy) {
		return this.categoryCrud.findAll(PageRequest.of(page, size, Direction.DESC, sortBy)).getContent();
	}

	@Override
	@Transactional
	public Product createProduct(Product product) {
		if (!categoryCrud.existsById(product.getCategory()))
			throw new RuntimeException("Category not exists");
		product.setId(this.atomicId.getAndIncrement() + "");
		return this.productCrud.save(product);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> getProductById(String id) {
		return this.productCrud.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProducts(int page, int size, String sortBy) {
		return this.productCrud.findAll(PageRequest.of(page, size, Direction.DESC, sortBy)).getContent();
	}

	@Override
	@Transactional(readOnly = true)

	public List<Product> getAllProductsByName(int page, int size, String sort, String name) {
		return this.productCrud.findAllByName(name, PageRequest.of(page, size, Direction.DESC, sort));
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.productCrud.deleteAll();
		this.categoryCrud.deleteAll();

	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProductsByMinPrice(int page, int size, String sortBy, String value) {
		return this.productCrud.findAllByPriceGreaterThanEqual(Double.parseDouble(value),
				PageRequest.of(page, size, Direction.DESC, sortBy));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProductsByMaxPrice(int page, int size, String sortBy, String value) {
		return this.productCrud.findAllByPriceLessThanEqual(Double.parseDouble(value),
				PageRequest.of(page, size, Direction.DESC, sortBy));

	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProductsByCategoryName(int page, int size, String sortBy, String value) {
		Set<Category> dynasty = categoryCrud.findById(value).get().getDynasty();
		List<Product> productsByCategory = this.productCrud.findAllByCategory(value,
				PageRequest.of(page, size, Direction.DESC, sortBy));
		for (Category category : dynasty) {
			if (productsByCategory.size() >= size)
				break;
			productsByCategory.addAll(this.productCrud.findAllByCategory(category.getName(),
					PageRequest.of(0, size - productsByCategory.size(), Direction.DESC, sortBy)));
		
		}

		return productsByCategory;
	}
	
	

}
