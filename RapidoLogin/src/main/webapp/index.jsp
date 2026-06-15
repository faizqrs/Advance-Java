<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rapido Sign Up</title>

<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: #0f172a; /* dark navy */
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        background: #1e293b;
        padding: 30px;
        border-radius: 12px;
        width: 320px;
        box-shadow: 0 8px 25px rgba(0,0,0,0.6);
    }

    h2 {
        text-align: center;
        color: #facc15; /* yellow highlight */
        margin-bottom: 20px;
    }

    label {
        display: block;
        color: #cbd5f5;
        margin-bottom: 5px;
        font-size: 14px;
    }

    input[type="text"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 15px;
        border: none;
        border-radius: 6px;
        background: #334155;
        color: white;
        outline: none;
    }

    input[type="text"]:focus {
        border: 1px solid #facc15;
    }

    input::placeholder {
        color: #94a3b8;
    }

    .btn {
        width: 100%;
        padding: 10px;
        background: #facc15;
        border: none;
        border-radius: 6px;
        color: black;
        font-size: 16px;
        cursor: pointer;
        font-weight: bold;
    }

    .btn:hover {
        background: #eab308;
    }

    .footer-text {
        text-align: center;
        margin-top: 10px;
        color: #94a3b8;
        font-size: 13px;
    }
</style>

</head>

<body>

<div class="container">
    <h2>Rapido Sign Up</h2>

    <form action="signup">
        <label>Mobile Number</label>
        <input type="text" name="mobile" placeholder="Enter mobile number">

        <label>Email</label>
        <input type="text" name="email" placeholder="Enter email">

        <label>Location</label>
        <input type="text" name="location" placeholder="Enter your location">

        <label>OTP</label>
        <input type="text" name="otp" placeholder="Enter OTP">

        <input type="submit" value="Sign Up" class="btn">
    </form>

    <div class="footer-text">
        Fast • Safe • Affordable
    </div>
</div>

</body>
</html>