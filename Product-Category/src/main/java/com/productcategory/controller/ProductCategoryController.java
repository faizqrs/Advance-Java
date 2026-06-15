package com.productcategory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.productcategory.request.CategoryRequest;
import com.productcategory.service.ProductCategoryService;

/**
 * Controller for handling Product Category web requests.
 *
 * Layer Role : Presentation Layer (MVC Controller)
 * Depends On : ProductCategoryService
 *
 * Flow: HTTP Request → ProductCategoryController → ProductCategoryService → Repository → DB
 */
@Controller
public class ProductCategoryController {

    /**
     * Service layer injected by Spring's IoC container.
     * Handles all business logic for category operations.
     */
    @Autowired
    ProductCategoryService productCategoryService;

    /**
     * Handles POST requests to create a new product category.
     *
     * URL     : POST /createCategory
     * Binding : @ModelAttribute binds form fields to CategoryRequest DTO
     *
     * @param categoryRequest DTO carrying category details from the HTML form
     * @return Logical view name resolved to /WEB-INF/views/find-category.jsp
     */
    @PostMapping("createCategory")
    public String createCategory(@ModelAttribute CategoryRequest categoryRequest) {
        productCategoryService.createCategory(categoryRequest);
        return "find-category";
    }

    /**
     * Handles GET requests to find a single category by ID.
     *
     * URL        : GET /findCategory
     * @ResponseBody : Returns plain text directly to the browser,
     *                 bypassing the view resolver — no JSP is rendered.
     *
     * @return Confirmation string sent directly in the HTTP response body
     */
    @GetMapping("findCategory")
    @ResponseBody
    public String getCategory() {
        productCategoryService.getCategory(1);
        return "found category";
    }

    /**
     * Handles GET requests to retrieve all product categories.
     *
     * URL        : GET /findAllProducts
     * @ResponseBody : Returns plain text directly to the browser,
     *                 bypassing the view resolver — no JSP is rendered.
     *
     * @return Confirmation string sent directly in the HTTP response body
     */
    @GetMapping("findAllProducts")
    @ResponseBody
    public String findAllProducts() {
        productCategoryService.findCategory();
        return "Found Category";
    }
}