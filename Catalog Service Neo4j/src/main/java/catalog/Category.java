package catalog;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Category {

	@Index(unique = true)
	private String name;

	private String description;

	private String parentCategory;

	@Relationship(type = "dynasty", direction = Relationship.OUTGOING)
	private Set<Category> dynasty;

	@Relationship(type = "dynasty", direction = Relationship.INCOMING)
	private Set<Category> ancesters;
	
	@Relationship(type = "Category")
	private Set<Product> products;
	
	@Relationship(type = "parent", direction = Relationship.OUTGOING)
	private Category parent;
	
	@Relationship(type = "parent", direction = Relationship.INCOMING)
	private Set<Category> children;

	@Id
	@GeneratedValue
	private Long id;

	public Category() {
		super();
		this.dynasty = new HashSet<Category>();
		this.ancesters = new HashSet<Category>();
		this.children = new HashSet<Category>();

	}

	public Category(String name, String description, String parentCategory) {
		this();
		this.name = name;
		this.description = description;
		this.parentCategory = parentCategory;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	@JsonIgnore
	public Set<Category> getDynasty() {
		return dynasty;
	}

	public void setDynasty(Set<Category> dynasty) {
		this.dynasty = dynasty;
	}

	@JsonIgnore
	public Set<Category> getAncesters() {
		return ancesters;
	}

	public void setAncesters(Set<Category> ancesters) {
		this.ancesters = ancesters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Category getParent() {
		return parent;
	}

	
	public void setParent(Category parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	

}
