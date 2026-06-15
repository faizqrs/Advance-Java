<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Orders</title>

    <style>
        body {
            background: #121212;
            color: white;
            font-family: Arial;
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
            padding: 5px 0;
        }

        select, button {
            padding: 5px;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<h2>📦 Admin - Orders</h2>

<c:forEach var="order" items="${orders}">

    <div class="order">

        <b>Order ID:</b> ${order.id} <br/>
        <b>Status:</b> ${order.status} <br/>
        <b>Payment:</b> ${order.paymentMethod}

        <h4>Items:</h4>

        <c:forEach var="item" items="${orderItemsMap[order.id]}">
            <div class="item">
                Product ID: ${item.productId} |
                Qty: ${item.quantity} |
                Price: ₹${item.priceAtPurchase}
            </div>
        </c:forEach>

        <!-- 🔥 Update Status -->
        <form action="${pageContext.request.contextPath}/admin/orders/update" method="post">

            <input type="hidden" name="orderId" value="${order.id}" />

            <select name="status">
                <option value="PLACED">PLACED</option>
                <option value="SHIPPED">SHIPPED</option>
                <option value="DELIVERED">DELIVERED</option>
            </select>

            <button type="submit">Update</button>
        </form>

    </div>

</c:forEach>

</body>
</html>