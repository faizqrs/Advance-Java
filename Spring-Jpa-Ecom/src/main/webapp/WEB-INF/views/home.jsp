<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #121212;
            color: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            text-align: center;
            background: #1e1e1e;
            padding: 50px;
            border-radius: 12px;
            box-shadow: 0 0 25px rgba(0,0,0,0.6);
        }

        h1 {
            margin-bottom: 40px;
            font-size: 28px;
        }

        .btn {
            display: block;
            margin: 12px auto;
            padding: 14px 40px;
            font-size: 16px;
            text-decoration: none;
            border-radius: 8px;
            color: white;
            width: 220px;
            transition: 0.3s ease;
        }

        .user { background: #4CAF50; }
        .login { background: #2196F3; }
        .register { background: #FF9800; }
        .admin { background: #9C27B0; }
        .logout { background: #e53935; }

        .btn:hover {
            transform: scale(1.05);
            opacity: 0.9;
        }
    </style>
</head>
<body>

<div class="container">

    <h1>🛒 Welcome to E-Commerce App</h1>

    <!-- Browse -->
    <a class="btn user"
       href="${pageContext.request.contextPath}/user/search">
        Browse Products
    </a>

    <!-- 🔐 NOT LOGGED IN -->
    <c:if test="${empty sessionScope.userId}">

        <a class="btn login"
           href="${pageContext.request.contextPath}/login">
            Login
        </a>

        <a class="btn register"
           href="${pageContext.request.contextPath}/register">
            Register
        </a>

    </c:if>

    <!-- 👤 LOGGED IN -->
    <c:if test="${not empty sessionScope.userId}">

        <!-- User Orders -->
        <a class="btn user"
           href="${pageContext.request.contextPath}/user/orders">
            My Orders
        </a>

        <!-- 🧑‍💼 ADMIN ONLY -->
        <c:if test="${sessionScope.role == 'ADMIN'}">

            <a class="btn admin"
               href="${pageContext.request.contextPath}/admin/orders">
                Admin Orders
            </a>

        </c:if>

        <!-- Logout -->
        <a class="btn logout"
           href="${pageContext.request.contextPath}/logout">
            Logout
        </a>

    </c:if>

</div>

</body>
</html>