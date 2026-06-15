<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Success</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #121212;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .box {
            background: #1e1e1e;
            padding: 35px;
            border-radius: 12px;
            text-align: center;
            width: 350px;
            box-shadow: 0 0 25px rgba(0,0,0,0.6);
        }

        h2 {
            color: #4CAF50;
            margin-bottom: 15px;
        }

        p {
            font-size: 14px;
            margin: 8px 0;
        }

        .btn {
            display: block;
            margin-top: 15px;
            padding: 10px;
            text-decoration: none;
            border-radius: 6px;
            color: white;
            font-size: 14px;
            transition: 0.3s;
        }

        .primary {
            background: #4a4a8a;
        }

        .primary:hover {
            background: #5c5ccf;
        }

        .secondary {
            background: #4CAF50;
        }

        .secondary:hover {
            background: #45a049;
        }
    </style>
</head>

<body>

<div class="box">

    <h2>✅ Product Saved Successfully</h2>

    <p><b>Product:</b> ${productRequest.name}</p>
    <p><b>Price:</b> ₹ ${productRequest.price}</p>

    <a class="btn primary"
       href="${pageContext.request.contextPath}/seller/product/list">
        View All Products
    </a>

    <a class="btn secondary"
       href="${pageContext.request.contextPath}/seller/product/new">
        Add Another Product
    </a>

</div>

</body>
</html>