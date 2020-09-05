package catalog;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface CategoryCrud extends PagingAndSortingRepository<Category, String> {
	
}
