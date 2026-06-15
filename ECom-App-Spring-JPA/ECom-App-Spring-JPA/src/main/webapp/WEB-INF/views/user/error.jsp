<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta charset="UTF-8">
<title>Error</title>

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
        padding: 30px;
        border-radius: 12px;
        text-align: center;
        width: 350px;
        box-shadow: 0 0 25px rgba(0,0,0,0.6);
    }

    .title {
        color: #ff6b6b;
        font-size: 22px;
        margin-bottom: 10px;
    }

    .message {
        font-size: 14px;
        margin: 15px 0;
        color: #bbb;
    }

    .btn {
        display: inline-block;
        margin-top: 15px;
        padding: 10px 18px;
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

    <div class="title">❌ Oops!</div>

    <div class="message">
        ${error}
    </div>

    <a class="btn" href="${ctx}/user/search">
        Back to Products
    </a>

</div>

</body>
</html>