package rocks.j5.uga.expanse.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {

	/** The category repository. */
	private final CategoryRepository categoryRepository;

	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/**
	 * Gets all categories.
	 *
	 * @return all categories
	 */
	@GetMapping(value = "/all")
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}
	/**
	 * Gets category.
	 *
	 * @return category if exists
	 */
	@GetMapping(value = "/get")
	public Category get(@RequestParam("id") int id) {
		return categoryRepository.findById(id).get();
	}

	/**
	 * Persist category.
	 *
	 * @param category the category
	 * @return the list
	 */
	@PostMapping(value = "/add")
	public List<Category> persist(@RequestBody final Category category) {
		categoryRepository.save(category);
		return categoryRepository.findAll();
	}
	/**
	 * Delete category.
	 *
	 * @param id the id
	 * @return all categories
	 */
	@DeleteMapping(value = "/delete")
	public List<Category> delete(@PathVariable int id) {
		categoryRepository.deleteById(id);
		return categoryRepository.findAll();
	}
	/**
	 * Put category.
	 *
	 * @param id 		the id
	 * @param category  the category
	 * @return all categories
	 */
	@PutMapping(value = "/put/{id}")
	public List<Category> put(@PathVariable int id, @RequestBody Category category) {
		if (categoryRepository.existsById(id)) {
			categoryRepository.deleteById(id);
			categoryRepository.save(category);
		}
		
		return categoryRepository.findAll();
	}
}
