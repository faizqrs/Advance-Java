<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>

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
            background: #1e1e1e;
            padding: 40px;
            border-radius: 10px;
            width: 320px;
            box-shadow: 0 0 20px rgba(0,0,0,0.6);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        input {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border-radius: 6px;
            border: none;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            border: none;
            border-radius: 6px;
            background: #4CAF50;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #45a049;
        }

        .link {
            text-align: center;
            margin-top: 15px;
        }

        .link a {
            color: #2196F3;
            text-decoration: none;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<div class="container">

    <h2>Login</h2>

    <!-- Error Message -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">

        <input type="text" name="email" placeholder="Enter Email" required />

        <input type="password" name="password" placeholder="Enter Password" required />

        <button type="submit">Login</button>

    </form>

    <div class="link">
        Don't have an account?
        <a href="${pageContext.request.contextPath}/register">Register</a>
    </div>

</div>

</body>
</html>