<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta charset="UTF-8">
<title>Products</title>

<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: #121212;
        color: #e0e0e0;
        padding: 30px;
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .search-box {
        text-align: center;
        margin-bottom: 25px;
    }

    input[type="text"] {
        padding: 10px;
        width: 250px;
        border-radius: 6px;
        border: 1px solid #333;
        background: #2a2a2a;
        color: #fff;
    }

    button {
        padding: 10px 15px;
        margin-left: 5px;
        border: none;
        border-radius: 6px;
        background: #4a4a8a;
        color: white;
        cursor: pointer;
        transition: 0.3s;
    }

    button:hover {
        background: #5c5ccf;
    }

    .grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: 20px;
    }

    .card {
        background: #1e1e1e;
        padding: 15px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0,0,0,0.5);
        transition: 0.3s;
    }

    .card:hover {
        transform: translateY(-5px);
    }

    .title {
        font-size: 16px;
        margin-bottom: 5px;
    }

    .meta {
        font-size: 13px;
        color: #aaa;
        margin-bottom: 10px;
    }

    .price {
        font-size: 16px;
        color: #4CAF50;
        margin-bottom: 8px;
    }

    .stock {
        font-size: 12px;
        color: #888;
        margin-bottom: 10px;
    }

    .btn {
        display: inline-block;
        padding: 8px 12px;
        background: #4a4a8a;
        color: white;
        text-decoration: none;
        border-radius: 6px;
        font-size: 13px;
        transition: 0.3s;
    }

    .btn:hover {
        background: #5c5ccf;
    }

    .empty {
        text-align: center;
        color: #888;
        margin-top: 20px;
    }
</style>

</head>

<body>

<h2>🛍️ Products</h2>

<div class="search-box">
    <form action="${ctx}/user/search/filter" method="get">
        <input type="text" name="keyword" placeholder="Search products..." />
        <button type="submit">Search</button>
    </form>
</div>

<div class="grid">

<c:forEach var="p" items="${products}">
    <div class="card">

        <div class="title">${p.name}</div>

        <div class="meta">
            ${p.brand} | ${p.category}
        </div>

        <div class="price">
            ${p.currencyCode} ${p.price}
        </div>

        <div class="stock">
            Stock: ${p.availableQty}
        </div>

        <a class="btn" href="${ctx}/user/product/${p.id}">
            View Details
        </a>

    </div>
</c:forEach>

</div>

<c:if test="${empty products}">
    <div class="empty">
        No products found
    </div>
</c:if>

</body>
</html>