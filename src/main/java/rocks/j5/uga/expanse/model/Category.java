package rocks.j5.uga.expanse.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * The Class Category.
 */
@Entity
@Data
public class 	Category {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id", unique = true, nullable = false)
	private Integer category_id;

	/** The name. */
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/** The picture. */
	@Column(name = "picture")
	private String picture;

	/** The products. */
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category", fetch = FetchType.EAGER)
	private Collection<CategoryItem> categoryItems;

	/**
	 * Empty Constructor. Instantiates a new category.
	 */
	public Category() {
	}

	/**
	 * Instantiates a new category.
	 *
	 * @param category_id       the id
	 * @param name     the name
	 * @param picture  the picture
	 * @param children the children
	 */
	public Category(Integer category_id, String name, String picture, List<Category> children) {
		this.category_id = category_id;
		this.name = name;
		this.picture = picture;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the picture.
	 *
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * Sets the picture.
	 *
	 * @param picture the new picture
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
