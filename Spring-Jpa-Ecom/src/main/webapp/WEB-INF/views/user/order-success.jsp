<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Order Success</title>

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

        .card {
            background: #1e1e1e;
            padding: 35px;
            border-radius: 12px;
            text-align: center;
            width: 350px;
            box-shadow: 0 0 25px rgba(0,0,0,0.6);
        }

        .title {
            color: #4CAF50;
            font-size: 22px;
            margin-bottom: 15px;
        }

        .info {
            margin: 10px 0;
            font-size: 14px;
            color: #bbb;
        }

        .amount {
            font-size: 18px;
            color: #ffffff;
            margin-top: 10px;
        }

        .btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #4a4a8a;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: 0.3s;
        }

        .btn:hover {
            background: #5c5ccf;
        }
    </style>
</head>

<body>

<div class="card">

    <div class="title"> Order Placed Successfully</div>

    <div class="info">
      <h3>
    Payment Method:
    <span style="color:#4CAF50;">
        ${paymentMethod}
    </span>
</h3>
    </div>

    

    <a class="btn" href="${pageContext.request.contextPath}/user/search">
        Continue Shopping
    </a>

</div>

</body>
</html>