package catalog;

public class ProductBoundaryFromEntity {

	private String id;
	private String name;
	private String price;
	private String image;
	private String category;

	public ProductBoundaryFromEntity() {
	}

	public ProductBoundaryFromEntity(String id, String name, String price, String image, String category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "CatalogBoundary [id=" + id + ", name=" + name + ", price=" + price + ", image=" + image + ", category="
				+ category + "]";
	}

	

}
