package com.zepto.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zepto.product.entity.ProductEntity;
import com.zepto.product.service.IProduct;

/**
 * Spring MVC Controller responsible for handling product status-related
 * HTTP requests on the Zepto platform.
 *
 * This controller serves two purposes:
 *   1. Seller-facing : Allows sellers to check the approval status of
 *                      their uploaded product using the product ID.
 *   2. Admin-facing  : Displays all products in the admin panel and allows
 *                      the admin to approve or reject products with a note.
 *
 * Annotated with @Controller (not @RestController) because this controller
 * returns both plain text (@ResponseBody) and View names (JSP rendering).
 *
 * Layer Role  : Controller Layer (Web / Presentation Layer)
 * Depends On  : {@link IProduct} (Service Interface)
 *
 * Endpoints:
 *   GET  /checkProductStatus — Check status of a single product by ID
 *   GET  /getAllProducts     — Load admin panel with all products
 *   POST /updateStatus       — Update product status and approval note
 */
@Controller
public class ProductStatusController {

    /**
     * Injects the IProduct service interface via Spring's dependency injection.
     *
     * Programming to the interface (IProduct) rather than the concrete class
     * (ProductServiceImpl) ensures loose coupling between layers,
     * making the controller easier to test and maintain.
     */
    @Autowired
    private IProduct iProduct;

    // -------------------------------------------------------------------------
    // ENDPOINT 1 — Check Product Status (Seller-Facing)
    // -------------------------------------------------------------------------

    /**
     * Handles GET requests to check the approval status of a specific product.
     *
     * Triggered when a seller submits the "Check Status" form on the
     * product-confirm page. Converts the productId from String to int,
     * delegates to the service layer, and returns a plain text response.
     *
     * Annotated with @ResponseBody to return the result directly as a
     * plain text HTTP response — not as a View name.
     *
     * HTTP Method  : GET
     * Endpoint URL : /checkProductStatus?productId=5
     *
     * @param productId The product's database ID submitted from the form (as String).
     * @return A plain text message showing the current status of the product.
     *         Example: "Status of your product is : APPROVED"
     */
    @GetMapping("checkProductStatus")
    @ResponseBody
    public String checkStatus(@RequestParam("productId") String productId) {

        // Convert productId from String to int for the service layer
        int productIdint = Integer.parseInt(productId);

        // Delegate to service layer to fetch the product's current status from DB
        String status = iProduct.checkProductStatus(productIdint);

        // Return plain text response directly to the browser (not a JSP view)
        return "Status of your product is : " + status;
    }

    // -------------------------------------------------------------------------
    // ENDPOINT 2 — Load Admin Panel (Admin-Facing)
    // -------------------------------------------------------------------------

    /**
     * Handles GET requests to load the admin panel showing all products.
     *
     * Fetches all product records from the database via the service layer,
     * adds them to the Spring Model, and renders the product-confirm JSP
     * which displays the full product table with status update controls.
     *
     * HTTP Method  : GET
     * Endpoint URL : /getAllProducts
     *
     * @param model The Spring MVC Model used to pass data to the View (JSP).
     * @return The logical View name "product-confirm" which resolves to
     *         product-confirm.jsp via Spring's ViewResolver.
     */
    @GetMapping("getAllProducts")
    public String getAllProducts(Model model) {

        // Fetch all ProductEntity records from the database
        List<ProductEntity> allProducts = iProduct.getAllProducts();

        // Add the list to the model under the key "products"
        // Accessible in JSP via: ${products} and <c:forEach var="product" items="${products}">
        model.addAttribute("products", allProducts);

        // Render the product-confirm.jsp page with the populated products list
        return "product-confirm";
    }

    // -------------------------------------------------------------------------
    // ENDPOINT 3 — Update Product Status (Admin-Facing)
    // -------------------------------------------------------------------------

    /**
     * Handles POST requests to update the approval status and note of a product.
     *
     * Triggered when the admin clicks the "Update" button on any product row
     * in the admin panel table. Receives the product's database ID, the new
     * status (APPROVED / REJECTED / CREATED), and an optional approval note.
     *
     * After updating, redirects back to the admin panel (GET /getAllProducts)
     * using the POST-Redirect-GET pattern to prevent form resubmission on refresh.
     *
     * HTTP Method  : POST
     * Endpoint URL : /updateStatus
     *
     * Note: Explicit names are provided in each @RequestParam annotation
     *       (e.g., @RequestParam("id")) because Java 21 does not expose
     *       parameter names via reflection by default unless compiled with
     *       the '-parameters' flag. Explicit names avoid this issue entirely.
     *
     * @param id     The database primary key (int) of the product to update.
     * @param status The new status string: "APPROVED", "REJECTED", or "CREATED".
     * @param note   An optional admin note explaining the approval decision.
     * @return A redirect instruction to reload the admin panel with fresh data.
     */
    @PostMapping("updateStatus")
    public String updateStatus(
            @RequestParam("id") int id,
            @RequestParam("status") String status,
            @RequestParam("note") String note) {

        // Delegate status update to the service layer
        // Service → Repository → Hibernate UPDATE query → MySQL
        iProduct.updateProductStatus(id, status, note);

        // POST-Redirect-GET pattern:
        // Redirect to GET /getAllProducts so the admin sees the refreshed table.
        // This also prevents duplicate form submission if the admin refreshes the page.
        return "redirect:/getAllProducts";
    }
}