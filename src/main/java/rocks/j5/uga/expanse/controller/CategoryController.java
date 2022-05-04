package rocks.j5.uga.expanse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.model.Category;
import rocks.j5.uga.expanse.service.CatalogService;
import rocks.j5.uga.expanse.service.CategoryService;
import rocks.j5.uga.expanse.service.UserServiceO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The Class CategoryResource.
 *
 * @version 1.0
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/category", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(maxAge = 3600)
public class CategoryController {

	private final CatalogService catalogService;
	private final CategoryService categoryService;
	private final UserServiceO userServiceO;

	public CategoryController(CatalogService catalogService, CategoryService categoryService,
							  UserServiceO userServiceO) {
		this.catalogService = catalogService;
		this.categoryService = categoryService;
		this.userServiceO = userServiceO;
	}

	/**
	 * Gets all categories.
	 *
	 * @return all categories
	 */
	@CrossOrigin("*")
	@GetMapping(value = "/{category}/{all}")
	public Category getAllBooksByCategory(@PathVariable String category,
										  @PathVariable(required = false) String howMany,
										  HttpSession httpSession) {
		return categoryService.findAllBooksByCategory(category);
	}

	/**
	 * Persist category.
	 *
	 * @param category the category
	 * @return the list
	 */
	@PostMapping(value = "/add")
	public List<Category> persist(@RequestBody final Category category,
								  HttpSession httpSession) {
		categoryService.save(category);
		return categoryService.findAll();
	}

	/**
	 * Delete category.
	 *
	 * @param id the id
	 * @return all categories
	 */
	@DeleteMapping(value = "/delete")
	public List<Category> delete(@PathVariable int id, HttpSession session) {
		categoryService.deleteById(id);
		return categoryService.findAll();
	}

	/**
	 * Put category.
	 *
	 * @param id 		the id
	 * @param category  the category
	 * @return all categories
	 */
	@PutMapping(value = "/put/{id}")
	public List<Category> put(@PathVariable int id, @RequestBody Category category, HttpSession session) {
		if (categoryService.existsById(id)) {
			categoryService.deleteById(id);
			categoryService.save(category);
		}
		
		return categoryService.findAll();
	}
}
