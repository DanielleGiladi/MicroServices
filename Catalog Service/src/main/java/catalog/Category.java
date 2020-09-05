package catalog;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "CATEGORY")
@Entity
public class Category {

	private String name;
	private String description;
	private String parentCategory;
	private Set<Category> dynasty;

	public Category() {
		super();
		dynasty = new HashSet<Category>();
	}

	public Category(String name, String description, String parentCategory) {
		super();
		this.name = name;
		this.description = description;
		this.parentCategory = parentCategory;
	}

	@Id
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

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Category> getDynasty() {
		return dynasty;
	}

	public void setDynasty(Set<Category> dynasty) {
		this.dynasty = dynasty;
	}

	public void addChild(Category category) {
		dynasty.add(category);
	}

}
