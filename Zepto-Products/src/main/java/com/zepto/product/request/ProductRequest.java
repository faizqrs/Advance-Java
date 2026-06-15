package com.zepto.product.request;

/**
 * Request model class representing the product details submitted by a seller
 * when uploading a new product to the Zepto platform.
 *
 * This class acts as a Data Transfer Object (DTO) — it carries input data
 * from the API client (seller) through the Controller layer to the Service layer.
 *
 * Follows the standard JavaBean convention:
 * private fields with public getters and setters,
 * making it compatible with Spring's JSON deserialization (Jackson library).
 *
 * Layer Role  : Request/DTO Layer
 * Received By : Controller Layer (deserialized from HTTP request body)
 * Consumed By : {@link com.zepto.product.service.impl.ProductServiceImpl}
 *
 * Expected HTTP Method : POST
 * Expected Format      : JSON request body
 *
 * Example JSON Payload:
 * {
 *   "productName"        : "Laptop",
 *   "productQty"         : "50",
 *   "productDescription" : "15-inch gaming laptop",
 *   "productPrice"       : "75000",
 *   "soldBy"             : "TechMart"
 * }
 */
public class ProductRequest {

    /**
     * The name of the product being uploaded.
     * Used along with {@link #productQty} to generate a unique product ID.
     *
     * Example: "Laptop"
     */
    private String productName;

    /**
     * The available quantity of the product being uploaded.
     * Combined with {@link #productName} to generate a deterministic product ID.
     *
     * Example: "50"
     *
     * Note: Stored as String — consider using Integer or Long
     *       for numeric validation and calculations.
     */
    private String productQty;

    /**
     * A brief description of the product providing additional details to buyers.
     *
     * Example: "15-inch gaming laptop with 16GB RAM"
     */
    private String productDescription;

    /**
     * The price of the product in the platform's default currency (INR).
     *
     * Example: "75000"
     *
     * Note: Stored as String — consider using BigDecimal
     *       for precise monetary calculations and validations.
     */
    private String productPrice;

    /**
     * The name or ID of the seller uploading the product.
     *
     * Example: "TechMart"
     */
    private String soldBy;
    
    private String currencyType;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the name of the product.
     *
     * @return productName — The product's name as provided by the seller.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     *
     * @param productName The product name to be set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return productQty — The available stock quantity as a String.
     */
    public String getProductQty() {
        return productQty;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param productQty The product quantity to be set.
     */
    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    /**
     * Returns the description of the product.
     *
     * @return productDescription — A brief description of the product.
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the description of the product.
     *
     * @param productDescription The product description to be set.
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * Returns the price of the product.
     *
     * @return productPrice — The product price as a String.
     */
    public String getProductPrice() {
        return productPrice;
    }

    /**
     * Sets the price of the product.
     *
     * @param productPrice The product price to be set.
     */
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Returns the seller's name or ID who is uploading the product.
     *
     * @return soldBy — The seller identifier.
     */
    public String getSoldBy() {
        return soldBy;
    }

    /**
     * Sets the seller's name or ID.
     *
     * @param soldBy The seller identifier to be set.
     */
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	
}