package catalog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface CategoryCrud extends PagingAndSortingRepository<Category, Long> {
	public Optional<Category> findOneByName(
			@Param("name") String name);
	
	public List<Category> findAllByName (
			@Param("name") String name, Pageable pageable);
	
}
