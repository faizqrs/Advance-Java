<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.productId}</td>
            <td>${p.name}</td>
            <td>${p.status}</td>

            <td>
                <form action="updateStatus" method="post">

                    <input type="hidden" name="id" value="${p.productId}" />

                    <select name="status">
                        <option value="APPROVED">Approve</option>
                        <option value="REJECTED">Reject</option>
                    </select>

                    <input type="text" name="note" placeholder="Enter note"/>

                    <button type="submit">Update</button>

                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>