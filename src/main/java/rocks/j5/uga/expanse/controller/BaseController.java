package rocks.j5.uga.expanse.controller;

import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.model.Category;
import rocks.j5.uga.expanse.repository.CategoryRepository;

import java.util.List;

/**
 * The Class CategoryResource.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BaseController {

	/** The category repository. */
	private final CategoryRepository categoryRepository;

	public BaseController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/**
	 * Gets all categories.
	 *
	 * @return all categories
	 */
	@GetMapping(value = "/")
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}
}
