<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta charset="UTF-8">
<title>Cart</title>

<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: #121212;
        color: #e0e0e0;
        padding: 40px 20px;
    }

    .container {
        max-width: 700px;
        margin: auto;
        background: #1e1e1e;
        padding: 25px;
        border-radius: 12px;
        box-shadow: 0 0 25px rgba(0,0,0,0.6);
    }

    h2 {
        margin-bottom: 20px;
    }

    .item {
        border-bottom: 1px solid #333;
        padding: 15px 0;
    }

    .item:last-child {
        border-bottom: none;
    }

    .title {
        font-size: 16px;
        font-weight: bold;
        margin-bottom: 5px;
    }

    .info {
        font-size: 14px;
        color: #bbb;
    }

    .btn {
        display: inline-block;
        margin-top: 20px;
        padding: 10px 18px;
        border-radius: 6px;
        text-decoration: none;
        color: white;
        transition: 0.3s;
    }

    .pay {
        background: #4CAF50;
    }

    .pay:hover {
        background: #45a049;
    }

    .back {
        background: #4a4a8a;
        margin-left: 10px;
    }

    .back:hover {
        background: #5c5ccf;
    }

    .empty {
        text-align: center;
        color: #888;
        padding: 20px;
    }
</style>

</head>

<body>

<div class="container">

<h2>🛒 Your Cart</h2>

<c:forEach var="item" items="${items}" varStatus="status">

    <div class="item">

        <div class="title">
            ${products[status.index].name}
        </div>

        <div class="info">
            Price:
            ${products[status.index].currencyCode}
            ${products[status.index].price}
        </div>

        <div class="info">
            Quantity: ${item.quantity}
        </div>

    </div>

</c:forEach>

<c:if test="${empty items}">
    <div class="empty">
        Your cart is empty
    </div>
</c:if>

<a class="btn pay" href="${ctx}/payment/checkout">
    Proceed to Payment
</a>

<a class="btn back" href="${ctx}/user/search">
    Continue Shopping
</a>

</div>

</body>
</html>