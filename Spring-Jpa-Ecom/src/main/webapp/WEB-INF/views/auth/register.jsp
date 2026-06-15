<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>

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
            width: 340px;
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
            background: #FF9800;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #F57C00;
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

    <h2>Create Account</h2>

    <!-- Optional Error -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">

        <input type="text" name="name" placeholder="Full Name" required />

        <input type="email" name="email" placeholder="Email" required />

        <input type="password" name="password" placeholder="Password" required />

        <button type="submit">Register</button>

    </form>

    <div class="link">
        Already have an account?
        <a href="${pageContext.request.contextPath}/login">Login</a>
    </div>

</div>

</body>
</html>