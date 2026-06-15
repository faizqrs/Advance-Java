package com.productcategory.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.productcategory.entity.Category;
import com.productcategory.entity.Product;
import com.productcategory.repository.ProductCategoryRepository;
import com.productcategory.request.CategoryRequest;

/**
 * Service class for Product Category business logic.
 *
 * Layer Role : Business Logic Layer
 * Called By  : {@link com.productcategory.controller.ProductCategoryController}
 * Depends On : {@link ProductCategoryRepository}
 *
 * Flow: Controller → ProductCategoryService → ProductCategoryRepository → DB
 */
@Service
public class ProductCategoryService {

    /**
     * Repository injected by Spring's IoC container.
     * Handles all direct database interactions for Category and Product entities.
     */
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    /**
     * Fetches a single category by its ID and prints its details to the console.
     *
     * @Transactional ensures a Hibernate session is active for the duration
     * of this method, required for any lazy-loaded associations.
     *
     * @param categoryID The primary key of the category to fetch
     */
    @Transactional
    public void getCategory(int categoryID) {
        System.out.println("ProductCategoryService.getCategory() START");

        Category category = productCategoryRepository.findById(categoryID).orElse(null);

        System.out.println("category details "
                + category.getId() + " "
                + category.getCategoryName()
                + " and product Name : "
                /* category.getProducts().get(0).getName() */);

        System.out.println("ProductCategoryService.getCategory() END");
    }

    /**
     * Creates a new Category along with one associated Product and saves both to the DB.
     *
     * Steps:
     *   1. Builds a Category entity from the CategoryRequest DTO.
     *   2. Builds a Product entity from the CategoryRequest DTO.
     *   3. Links the Product to the Category (both sides of the relationship).
     *   4. Saves the Category — Product is saved automatically via CascadeType.ALL.
     *
     * @param categoryRequest DTO carrying category and product details from the form
     */
    public void createCategory(CategoryRequest categoryRequest) {
        System.out.println("ProductCategoryService.createCategory()... START");

        // Build Category entity
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getCategoryDescription());
        category.setStatus(categoryRequest.getCategoryStatus());

        // Build Product entity
        Product product = new Product();
        product.setName(categoryRequest.getProductName());
        product.setDescription(categoryRequest.getProductDescription());

        // Link product to category (both sides of the bidirectional relationship)
        List<Product> products = new ArrayList<>();
        products.add(product);
        category.setProducts(products);
        product.setCategory(category);

        // Save category — cascades to product via CascadeType.ALL
        productCategoryRepository.save(category);

        System.out.println("ProductCategoryService.createCategory()... END");
    }

    /**
     * Fetches all categories with their associated products and prints them to the console.
     *
     * Uses the custom repository query with JOIN FETCH to load
     * all categories and their products in a single SQL query,
     * avoiding the N+1 select problem.
     */
    public void findCategory() {
        Iterable<Category> categories = productCategoryRepository.findAll();
        Iterator<Category> categoriesItr = categories.iterator();

        while (categoriesItr.hasNext()) {
            Category category = categoriesItr.next();
            List<Product> products = category.getProducts();

            for (Product prod : products) {
                System.out.println("Name: " + prod.getName());
                System.out.println("ID: "   + prod.getId());
            }
        }
    }
}