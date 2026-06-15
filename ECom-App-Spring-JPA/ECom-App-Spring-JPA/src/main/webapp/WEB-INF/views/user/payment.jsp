<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>

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

    .container {
        width: 400px;
        background: #1e1e1e;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 0 25px rgba(0,0,0,0.6);
    }

    h2 {
        text-align: center;
        margin-bottom: 10px;
    }

    .amount {
        text-align: center;
        font-size: 18px;
        margin-bottom: 20px;
        color: #4CAF50;
    }

    .option {
        margin: 10px 0;
        padding: 10px;
        border-radius: 6px;
        background: #2a2a2a;
        cursor: pointer;
    }

    .option:hover {
        background: #333;
    }

    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        border: 1px solid #333;
        border-radius: 6px;
        background: #2a2a2a;
        color: #fff;
    }

    label {
        font-size: 13px;
        color: #bbb;
    }

    .section {
        margin-top: 10px;
        display: none;
    }

    button {
        width: 100%;
        margin-top: 20px;
        padding: 12px;
        background: #4CAF50;
        border: none;
        border-radius: 6px;
        color: white;
        font-size: 15px;
        cursor: pointer;
        transition: 0.3s;
    }

    button:hover {
        background: #45a049;
    }

    .back {
        display: block;
        margin-top: 15px;
        text-align: center;
        color: #bbb;
        text-decoration: none;
    }

    .back:hover {
        color: #fff;
    }
</style>

</head>

<body>

<div class="container">

<h2>💳 Payment</h2>

<div class="amount">
    Total: ₹ ${total}
</div>

<form action="${ctx}/payment/pay" method="post">

    <div class="option">
        <input type="radio" name="method" value="UPI" required> UPI
    </div>

    <div class="option">
        <input type="radio" name="method" value="CARD"> Credit / Debit Card
    </div>

    <div class="option">
        <input type="radio" name="method" value="COD"> Cash on Delivery
    </div>

    <!-- UPI -->
    <div id="upiSection" class="section">
        <label>UPI ID</label>
        <input type="text" name="upiId" placeholder="example@upi"/>
    </div>

    <!-- CARD -->
    <div id="cardSection" class="section">
        <label>Card Number</label>
        <input type="text" name="cardNumber" placeholder="1234 5678 9012 3456"/>

        <label>Expiry</label>
        <input type="text" name="expiry" placeholder="MM/YY"/>

        <label>CVV</label>
        <input type="password" name="cvv" placeholder="123"/>
    </div>

    <button type="submit">Pay Now</button>

</form>

<a class="back" href="${ctx}/cart/view">
    ← Back to Cart
</a>

</div>

<script>
    const upi = document.getElementById("upiSection");
    const card = document.getElementById("cardSection");

    document.querySelectorAll('input[name="method"]').forEach(radio => {
        radio.addEventListener("change", function() {

            upi.style.display = "none";
            card.style.display = "none";

            if (this.value === "UPI") {
                upi.style.display = "block";
            } else if (this.value === "CARD") {
                card.style.display = "block";
            }
        });
    });
</script>

</body>
</html>