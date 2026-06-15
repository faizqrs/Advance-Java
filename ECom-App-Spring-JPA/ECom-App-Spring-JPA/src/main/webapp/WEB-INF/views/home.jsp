<%@ page contentType="text/html;charset=UTF-8" %>
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
            color: #f5f5f5;
        }

        .btn {
            display: block;
            margin: 20px auto;
            padding: 15px 40px;
            font-size: 18px;
            text-decoration: none;
            border-radius: 8px;
            color: white;
            width: 200px;
            transition: 0.3s ease;
        }

        .user {
            background: #4CAF50;
        }

        .user:hover {
            background: #45a049;
            transform: scale(1.05);
        }

        .seller {
            background: #4a4a8a;
        }

        .seller:hover {
            background: #5c5ccf;
            transform: scale(1.05);
        }
    </style>
</head>
<body>

<div class="container">

    <h1>🛒 Welcome to E-Commerce App</h1>

    <a class="btn user"
       href="${pageContext.request.contextPath}/user/search">
        User
    </a>

    <a class="btn seller"
       href="${pageContext.request.contextPath}/seller/product/new">
        Seller
    </a>

</div>

</body>
</html>