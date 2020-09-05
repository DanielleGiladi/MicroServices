package catalog;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogController {

	private enum Filter {
		BY_NAME, BY_MIN_PRICE, BY_MAX_PRICE, BY_CATEGORY, BY_ALL
	}

	private CatalogService catalogService;

	@Autowired
	public CatalogController(CatalogService catalogService) {
		super();
		this.catalogService = catalogService;
	}

	@RequestMapping(path = "/catalog/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundaryToEntity create(@RequestBody ProductBoundaryToEntity productBoundary) {
		return fromEntityProduct(this.catalogService.createProduct(toEntityProduct(productBoundary)));
	}

	@RequestMapping(path = "/catalog/categories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary createCategory(@RequestBody CategoryBoundary categoryBoundary) {
		return fromEntityCategory(this.catalogService.createCategory(toEntityCategory(categoryBoundary)));
	}

	@RequestMapping(path = "/catalog/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary[] getAllCategories(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy) {
		return this.catalogService.getAllCategories(page, size, sortBy).stream().map(this::fromEntityCategory)
				.collect(Collectors.toList()).toArray(new CategoryBoundary[0]);
	}

	@RequestMapping(path = "/catalog/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ProductBoundaryFromEntity[] genericGetProducts(
			@RequestParam(name = "filterType", required = false, defaultValue = "") Filter filterType,
			@RequestParam(name = "filterValue", required = false) String value,
			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {

		switch (filterType) {
		case BY_NAME: {
			return this.catalogService.getAllProductsByName(page, size, sortBy, value).stream()
					.map(this::fromEntityProduct).collect(Collectors.toList())
					.toArray(new ProductBoundaryFromEntity[0]);
		}

		case BY_MIN_PRICE: {
			return this.catalogService.getAllProductsByMinPrice(page, size, sortBy, value).stream()
					.map(this::fromEntityProduct).collect(Collectors.toList())
					.toArray(new ProductBoundaryFromEntity[0]);
		}

		case BY_MAX_PRICE: {
			return this.catalogService.getAllProductsByMaxPrice(page, size, sortBy, value).stream()
					.map(this::fromEntityProduct).collect(Collectors.toList())
					.toArray(new ProductBoundaryFromEntity[0]);
		}

		case BY_CATEGORY: {
			return this.catalogService.getAllProductsByCategoryName(page, size, sortBy, value).stream()
					.map(this::fromEntityProduct).collect(Collectors.toList())
					.toArray(new ProductBoundaryFromEntity[0]);
		}

		case BY_ALL: {
			return this.catalogService.getAllProducts(page, size, sortBy).stream().map(this::fromEntityProduct)
					.collect(Collectors.toList()).toArray(new ProductBoundaryFromEntity[0]);
		}

		default: {
			throw new RuntimeException("Invalid URL");

		}
		}

	}

	@RequestMapping(path = "/catalog/products/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundaryToEntity getProductById(@PathVariable("productId") String productId) {
		return this.fromEntityProduct(catalogService.getProductById(productId)
				.orElseThrow(() -> new ProductNotFoundException("could not find product by id: " + productId)));
	}

	@RequestMapping(path = "/catalog", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteAll() {
		this.catalogService.deleteAll();
	}

	private ProductBoundaryToEntity fromEntityProduct(Product product) {
		ProductBoundaryToEntity rv = new ProductBoundaryToEntity();
		rv.setProductId(product.getProductId());
		rv.setName(product.getName());
		rv.setPrice(product.getPrice() + "");
		rv.setImage(product.getImage());
		rv.setCategory(fromEntityCategory(product.getCategory()));

		return rv;
	}

	private Product toEntityProduct(ProductBoundaryToEntity productBoundary) {
		try {
			Product rv = new Product();
			rv.setProductId(productBoundary.getProductId());
			rv.setName(productBoundary.getName());
			rv.setPrice(Double.parseDouble(productBoundary.getPrice()));
			rv.setCategory(toEntityCategory(productBoundary.getCategory()));
			rv.setImage(productBoundary.getImage());
			return rv;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private CategoryBoundary fromEntityCategory(Category category) {
		CategoryBoundary rv = new CategoryBoundary();
		rv.setName(category.getName());
		rv.setDescription(category.getDescription());
		rv.setParentCategory(category.getParentCategory());
		return rv;
	}

	private Category toEntityCategory(CategoryBoundary categoryBoundary) {
		try {
			Category rv = new Category();
			rv.setName(categoryBoundary.getName());
			rv.setDescription(categoryBoundary.getDescription());
			rv.setParentCategory(categoryBoundary.getParentCategory());
			return rv;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
