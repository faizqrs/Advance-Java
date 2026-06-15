package com.productcategory.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.productcategory.entity.Category;

/**
 * Repository interface for Category entity database operations.
 *
 * Extends CrudRepository to inherit standard CRUD methods:
 *   save(), findById(), findAll(), deleteById(), count(), etc.
 *
 * Table    : category
 * Entity   : {@link Category}
 * Key Type : Integer (maps to auto-generated category id)
 *
 * Layer Role : Data Access Layer
 * Called By  : ProductCategoryService
 */
@Repository
public interface ProductCategoryRepository extends CrudRepository<Category, Integer> {

    /**
     * Fetches all categories along with their associated products
     * using a JPQL JOIN FETCH query.
     *
     * JOIN FETCH eagerly loads the products collection in a single SQL query,
     * avoiding the N+1 select problem that occurs when products are
     * loaded lazily in a loop.
     *
     * Generated SQL (approximate):
     *   SELECT c.*, p.* FROM category c
     *   INNER JOIN product p ON p.category_id = c.id
     *
     * Alternative approach using @EntityGraph (commented out below):
     *   @EntityGraph(attributePaths = "products")
     *   public Iterable<Category> findAll();
     *
     * Both approaches produce the same result — JOIN FETCH is used here
     * for explicit control over the query.
     *
     * @return Iterable of all Category objects with products pre-loaded
     */
    @Query("select c from Category c join fetch c.products")
    public Iterable<Category> findAll();
}