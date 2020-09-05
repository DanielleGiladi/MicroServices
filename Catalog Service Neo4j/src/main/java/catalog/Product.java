package catalog;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;

public class Product implements Comparable<Product> {

	@Index(unique = true)
	private String productId;

	private String name;

	private double price;

	private String image;
	
	@Relationship(type = "Category", direction = Relationship.INCOMING)
	private Category category;

	@Id
	@GeneratedValue
	private Long id;

	public Product() {
	}

	public Product(String productId, String name, double price, String image, Category category) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.image = image;
		this.category = category;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	@Override
	public int compareTo(Product other) {
		return this.productId.compareTo(other.productId);
	}
	

}
