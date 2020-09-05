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
		if (categoryCrud.findOneByName(category.getName()).isPresent())
			throw new RuntimeException("Category already exists");
		String parentName = category.getParentCategory();
		if (parentName != null) {
			if (!categoryCrud.findOneByName(parentName).isPresent())
				throw new RuntimeException("Category parent " + parentName + " not exists");
			else {
				//Category parent = categoryCrud.findOneByName(parentName).get();
				while (parentName != null) {
					Category parent = categoryCrud.findOneByName(parentName).get();
					category.getAncesters().add(parent);
					addChild(parent, category);
					parentName = categoryCrud.findOneByName(parentName).get().getParentCategory();

				}
			}
		}
		return this.categoryCrud.save(category);
	}

	public void addChild(Category parent, Category child) {
		this.categoryCrud.findOneByName(parent.getName()).get().getDynasty().add(child);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> getAllCategories(int page, int size, String sortBy) {
		return this.categoryCrud.findAll(PageRequest.of(page, size, Direction.ASC, sortBy)).getContent();
	}

	@Override
	@Transactional
	public Product createProduct(Product product) {
		if (!categoryCrud.findOneByName(product.getCategory().getName()).isPresent())
			throw new RuntimeException("Category not exists");
		product.setProductId(this.atomicId.getAndIncrement() + "");
		return this.productCrud.save(product);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> getProductById(String id) {
		return this.productCrud.findOneByProductId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProducts(int page, int size, String sortBy) {
		return this.productCrud.findAll(PageRequest.of(page, size, Direction.ASC, sortBy)).getContent();
	}

	@Override
	@Transactional(readOnly = true)

	public List<Product> getAllProductsByName(int page, int size, String sort, String name) {
		return this.productCrud.findAllByName(name, PageRequest.of(page, size, Direction.ASC, sort));
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
				PageRequest.of(page, size, Direction.ASC, sortBy));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProductsByMaxPrice(int page, int size, String sortBy, String value) {
		return this.productCrud.findAllByPriceLessThanEqual(Double.parseDouble(value),
				PageRequest.of(page, size, Direction.ASC, sortBy));

	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProductsByCategoryName(int page, int size, String sortBy, String value) {
		Set<Category> dynasty = categoryCrud.findOneByName(value).get().getDynasty();
		List<Product> productsByCategory = this.productCrud.findAllByCategory(value,
				PageRequest.of(page, size, Direction.ASC, sortBy));
		for (Category category : dynasty) {
			if (productsByCategory.size() >= size)
				break;
			productsByCategory.addAll(this.productCrud.findAllByCategory(category.getName(),
					PageRequest.of(page, size - productsByCategory.size(), Direction.ASC, sortBy)));

		}

		return productsByCategory;
	}

}
