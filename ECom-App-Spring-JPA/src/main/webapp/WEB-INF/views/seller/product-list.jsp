<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
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
            padding: 40px 20px;
        }

        .container {
            max-width: 1000px;
            margin: auto;
            background: #1e1e1e;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 0 25px rgba(0,0,0,0.6);
        }

        h2 {
            margin-bottom: 20px;
        }

        .btn {
            display: inline-block;
            padding: 10px 16px;
            background: #4a4a8a;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-size: 14px;
            margin-bottom: 15px;
            transition: 0.3s;
        }

        .btn:hover {
            background: #5c5ccf;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            text-align: left;
            padding: 12px;
            background: #2a2a2a;
            font-size: 13px;
            border-bottom: 1px solid #444;
        }

        td {
            padding: 12px;
            font-size: 13px;
            border-bottom: 1px solid #333;
        }

        tr:hover {
            background: #2a2a2a;
        }

        .badge {
            padding: 4px 10px;
            border-radius: 10px;
            font-size: 11px;
        }

        .badge.ACTIVE {
            background: #1e4620;
            color: #4CAF50;
        }

        .badge.INACTIVE {
            background: #4a1e1e;
            color: #ff6b6b;
        }

        .edit {
            color: #4CAF50;
            text-decoration: none;
            margin-right: 10px;
        }

        .edit:hover {
            text-decoration: underline;
        }

        .del {
            color: #ff6b6b;
            text-decoration: none;
        }

        .del:hover {
            text-decoration: underline;
        }

        .empty {
            color: #888;
            text-align: center;
            padding: 24px;
        }
    </style>
</head>

<body>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="container">

    <h2>📦 Products</h2>

    <a class="btn" href="${ctx}/seller/product/new">
        + New Product
    </a>

    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Brand</th>
                <th>Category</th>
                <th>Price</th>
                <th>Qty</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.name}</td>
                    <td>${p.brand}</td>
                    <td>${p.category}</td>
                    <td>${p.currencyCode} ${p.price}</td>
                    <td>${p.availableQty}</td>

                    <td>
                        <span class="badge ${p.status}">
                            ${p.status}
                        </span>
                    </td>

                    <td>
                        <a class="edit"
                           href="${ctx}/seller/product/edit/${p.id}">
                           ✏️ Edit
                        </a>

                        <a class="del"
                           href="${ctx}/seller/product/delete/${p.id}"
                           onclick="return confirm('Delete this product?')">
                           🗑 Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty products}">
                <tr>
                    <td colspan="7" class="empty">
                        No products yet. Add your first one.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>

</div>

</body>
</html>