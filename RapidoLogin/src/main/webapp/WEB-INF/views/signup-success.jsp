<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success Page</title>

<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: linear-gradient(to right, #28a745, #85e085);
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .success-box {
        background: white;
        padding: 40px;
        border-radius: 12px;
        text-align: center;
        box-shadow: 0 5px 20px rgba(0,0,0,0.2);
        width: 320px;
    }

    .success-icon {
        font-size: 50px;
        color: #28a745;
        margin-bottom: 15px;
    }

    h2 {
        color: #333;
        margin-bottom: 10px;
    }

    p {
        color: #666;
        margin-bottom: 20px;
    }

    .btn {
        display: inline-block;
        padding: 10px 20px;
        background: #28a745;
        color: white;
        text-decoration: none;
        border-radius: 6px;
        font-size: 14px;
    }

    .btn:hover {
        background: #218838;
    }
</style>

</head>

<body>

    <div class="success-box">
        <div class="success-icon">✔</div>
        <h2>Sign Up Successful!</h2>
        <p>Your account has been created successfully.</p>
        
        <a href="index.jsp" class="btn">Go to Home</a>
    </div>

</body>
</html>