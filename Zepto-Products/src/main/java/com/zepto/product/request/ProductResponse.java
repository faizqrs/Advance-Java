package com.zepto.product.request;

/**
 * Response model class representing the outcome of a product upload operation.
 *
 * This class is used to send structured data back from the Service layer
 * to the Controller layer, and ultimately as the API response to the client (seller).
 *
 * Contains:
 * - A generated unique product ID (on success).
 * - A human-readable confirmation or failure message.
 *
 * Layer Role : Response/Model Layer
 * Populated By : {@link com.zepto.product.service.impl.ProductServiceImpl}
 * Returned To  : Controller Layer → API Client
 *
 * Note: This class follows the standard JavaBean convention —
 *       private fields with public getters and setters.
 */
public class ProductResponse {

    /**
     * The unique product ID generated after a successful product upload.
     * This is an 8-character uppercase alphanumeric string
     * derived from the product name and quantity.
     *
     * Example: "A1B2C3D4"
     * Value is null if the product upload failed.
     */
    private String productId;

    /**
     * A human-readable message indicating the result of the product upload.
     *
     * Success : "Your Product has Uploaded Successfully. It will be live soon."
     * Failure : "Unable to upload product!"
     */
    private String confirmationMsg;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the generated product ID.
     *
     * @return productId — 8-character uppercase product ID, or null on failure.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the generated product ID on the response object.
     *
     * @param productId The unique product ID to be set.
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Returns the confirmation or failure message.
     *
     * @return confirmationMsg — A message describing the upload result.
     */
    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    /**
     * Sets the confirmation or failure message on the response object.
     *
     * @param confirmationMsg The message to be set.
     */
    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }
}