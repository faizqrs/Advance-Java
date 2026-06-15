<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Uploaded</title>

<style>
    * { box-sizing: border-box; }

    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: #0f172a;
        color: white;
        padding: 30px;
    }

    /* ── Upload Confirmation Card ── */
    .card-wrapper {
        display: flex;
        justify-content: center;
        margin-bottom: 40px;
    }

    .card {
        background: white;
        padding: 30px;
        border-radius: 12px;
        text-align: center;
        width: 340px;
        box-shadow: 0 8px 25px rgba(0,0,0,0.4);
        color: #333;
    }

    .icon {
        font-size: 50px;
        color: #16a34a;
        margin-bottom: 10px;
    }

    .card h2 {
        margin-bottom: 15px;
        color: #333;
    }

    .card p {
        font-size: 15px;
        color: #555;
        margin: 8px 0;
    }

    .card input[type="text"] {
        padding: 8px;
        border-radius: 6px;
        border: 1px solid #ccc;
        width: 80%;
        margin-bottom: 10px;
        outline: none;
    }

    .card input[type="submit"] {
        padding: 8px 18px;
        background: #16a34a;
        color: white;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-size: 14px;
    }

    .card input[type="submit"]:hover {
        background: #15803d;
    }

    /* ── Admin Panel ── */
    .admin-wrapper {
        max-width: 1100px;
        margin: 0 auto;
    }

    .admin-wrapper h2 {
        color: #22c55e;
        text-align: center;
        margin-bottom: 20px;
        font-size: 22px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th {
        background: #22c55e;
        color: black;
        padding: 12px 10px;
        font-size: 14px;
    }

    td {
        background: #1e293b;
        padding: 10px;
        text-align: center;
        border-bottom: 1px solid #334155;
        font-size: 14px;
        vertical-align: middle;
    }

    td select {
        padding: 6px;
        border-radius: 4px;
        background: #334155;
        color: white;
        border: none;
        margin-right: 6px;
    }

    td input[type="text"] {
        padding: 6px;
        border-radius: 4px;
        border: none;
        background: #334155;
        color: white;
        width: 150px;
        margin-right: 6px;
    }

    td input[type="submit"] {
        padding: 6px 14px;
        background: #22c55e;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-weight: bold;
        color: black;
    }

    td input[type="submit"]:hover {
        background: #16a34a;
    }

    /* Status badge colors */
    .badge {
        padding: 4px 10px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: bold;
        display: inline-block;
    }

    .badge-approved  { background: #16a34a; color: white; }
    .badge-rejected  { background: #dc2626; color: white; }
    .badge-created   { background: #f59e0b; color: black; }

    .divider {
        border: none;
        border-top: 2px solid #334155;
        margin: 30px auto;
        max-width: 1100px;
    }
</style>

</head>
<body>

    <!-- ═══════════════════════════════════════ -->
    <!--        UPLOAD CONFIRMATION CARD         -->
    <!-- ═══════════════════════════════════════ -->
    <div class="card-wrapper">
        <div class="card">
            <div class="icon">✔</div>
            <h2>Product Uploaded!</h2>

            <p><b>Product ID:</b> ${response.productId}</p>
            <p><b>Status:</b> ${response.confirmationMsg}</p>

            <form action="checkProductStatus">
                <p><b>Check Product Status</b></p>
                Product ID : <input type="text" name="productId" placeholder="Enter Product ID" /> <br/><br/>
                <input type="submit" value="Check Status" />
            </form>
        </div>
    </div>

    <hr class="divider" />

    <!-- ═══════════════════════════════════════ -->
    <!--         ADMIN PANEL — ALL PRODUCTS      -->
    <!-- ═══════════════════════════════════════ -->
    <div class="admin-wrapper">
        <h2>🛡️ Admin Panel — Product Approvals</h2>

        <table>
            <tr>
                <th>#</th>
                <th>Product ID</th>
                <th>Name</th>
                <th>Current Status</th>
                <th>Approval Note</th>
                <th>Update Status</th>
            </tr>

            <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.productId}</td>
                <td>${product.name}</td>

                <!-- Status Badge -->
                <td>
                    <c:choose>
                        <c:when test="${product.status == 'APPROVED'}">
                            <span class="badge badge-approved">APPROVED</span>
                        </c:when>
                        <c:when test="${product.status == 'REJECTED'}">
                            <span class="badge badge-rejected">REJECTED</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-created">CREATED</span>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>${product.approvalNote}</td>

                <!-- Update Form per row -->
                <td>
                    <form action="updateStatus" method="post">
                        <input type="hidden" name="id" value="${product.id}" />

                        <select name="status">
                            <option value="APPROVED">APPROVED</option>
                            <option value="REJECTED">REJECTED</option>
                            <option value="CREATED">CREATED</option>
                        </select>

                        <input type="text" name="note" placeholder="Add a note..." />
                        <input type="submit" value="Update" />
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>