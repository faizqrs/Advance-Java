<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>

    <style>
        body {
            font-family: Arial;
            background: #121212;
            color: white;
            padding: 20px;
        }

        .order {
            background: #1e1e1e;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
        }

        .item {
            margin-left: 20px;
            padding: 8px 0;
            border-bottom: 1px solid #333;
        }

        .title {
            font-size: 18px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<h2>🧾 Your Orders</h2>

<c:forEach var="order" items="${orders}">

    <div class="order">

        <div class="title">Order ID: ${order.id}</div>
        <div>Status: ${order.status}</div>
        <div>Payment: ${order.paymentMethod}</div>

        <h4>Items:</h4>

        <c:forEach var="item" items="${orderItemsMap[order.id]}">

            <div class="item">
                Product: ${productNameMap[item.productId]} <br/>
                Quantity: ${item.quantity} <br/>
                Price: ₹${item.priceAtPurchase}
            </div>

        </c:forEach>

    </div>

</c:forEach>

</body>
</html>