<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <meta charset="UTF-8">
    <title>Product Detail</title>

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
            width: 500px;
            background: #1e1e1e;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 0 25px rgba(0,0,0,0.6);
        }

        h2 { margin-bottom: 10px; }

        .desc {
            font-size: 14px;
            color: #bbb;
            margin-bottom: 10px;
        }

        .meta {
            font-size: 13px;
            color: #888;
            margin-bottom: 15px;
        }

        .price {
            font-size: 20px;
            color: #4CAF50;
            margin-bottom: 10px;
        }

        .stock {
            font-size: 13px;
            color: #aaa;
            margin-bottom: 20px;
        }

        input[type="number"] {
            width: 80px;
            padding: 8px;
            border-radius: 6px;
            border: 1px solid #333;
            background: #2a2a2a;
            color: #fff;
        }

        button {
            margin-left: 10px;
            padding: 10px 18px;
            background: #4a4a8a;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: 0.3s;
        }

        button:hover { background: #5c5ccf; }

        .back {
            display: block;
            margin-top: 20px;
            color: #bbb;
            text-decoration: none;
        }

        .back:hover { color: #fff; }

        .error {
            color: #ff6b6b;
            margin-bottom: 10px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>${product.name}</h2>

    <div class="desc">${product.description}</div>

    <div class="meta">
        ${product.brand} | ${product.category}
    </div>

    <div class="price">
        ${product.currencyCode} ${product.price}
    </div>

    <div class="stock">
        Available: ${product.availableQty}
    </div>

    <!-- Error Message -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <!-- Add to Cart -->
    <form action="${ctx}/cart/add" method="post">
        <input type="hidden" name="productId" value="${product.id}" />

        Quantity:
        <input type="number" name="qty" value="1" min="1"/>

        <button type="submit">Add to Cart</button>
    </form>

    <a class="back" href="${ctx}/user/search">
        ← Back to Products
    </a>

</div>

</body>
</html>