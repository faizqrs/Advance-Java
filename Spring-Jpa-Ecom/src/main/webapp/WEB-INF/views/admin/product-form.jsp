<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Edit Product' : 'New Product'}</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #121212;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            padding: 40px 20px;
        }

        .container {
            width: 100%;
            max-width: 600px;
            background: #1e1e1e;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 25px rgba(0,0,0,0.6);
        }

        h2 {
            margin-bottom: 20px;
            text-align: center;
            color: #ffffff;
        }

        label {
            display: block;
            margin-top: 14px;
            font-size: 13px;
            color: #aaa;
        }

        input, select, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border: 1px solid #333;
            border-radius: 6px;
            font-size: 14px;
            background: #2a2a2a;
            color: #fff;
            box-sizing: border-box;
        }

        input:focus, select:focus, textarea:focus {
            outline: none;
            border-color: #4a4a8a;
        }

        button {
            margin-top: 20px;
            width: 100%;
            padding: 12px;
            background: #4a4a8a;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
            transition: 0.3s;
        }

        button:hover {
            background: #5c5ccf;
        }

        .back {
            display: block;
            margin-top: 15px;
            text-align: center;
            font-size: 13px;
            color: #bbb;
            text-decoration: none;
        }

        .back:hover {
            color: #fff;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>${isEdit ? '✏️ Edit Product' : '➕ New Product'}</h2>

    <form:form method="post"
        action="${pageContext.request.contextPath}${isEdit ? '/seller/product/update' : '/seller/product/save'}"
        modelAttribute="productRequest">

        <c:if test="${isEdit}">
            <form:hidden path="id" />
        </c:if>

        <label>Product Name</label>
        <form:input path="name" placeholder="Wireless Mouse" />

        <label>Description</label>
        <form:textarea path="description" rows="3" placeholder="Short description" />

        <label>Brand</label>
        <form:input path="brand" placeholder="Logitech" />

        <label>Category</label>
        <form:input path="category" placeholder="Electronics" />

        <label>Price</label>
        <form:input path="price" type="number" step="0.01" />

        <label>Currency</label>
        <form:select path="currencyCode">
            <form:option value="INR">INR</form:option>
            <form:option value="USD">USD</form:option>
            <form:option value="EUR">EUR</form:option>
        </form:select>

        <label>Available Quantity</label>
        <form:input path="availableQty" type="number" />

        <label>Status</label>
        <form:select path="status">
            <form:option value="ACTIVE">Active</form:option>
            <form:option value="INACTIVE">Inactive</form:option>
        </form:select>

        <button type="submit">
            ${isEdit ? 'Update Product' : 'Save Product'}
        </button>

    </form:form>

    <a class="back" href="${pageContext.request.contextPath}/seller/product/list">
        ← Back to Product List
    </a>

</div>

</body>
</html>