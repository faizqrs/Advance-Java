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

    h2 { text-align: center; }

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

    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        border-radius: 6px;
        background: #2a2a2a;
        color: #fff;
        border: 1px solid #333;
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
    }

    .error-box {
        background: #ff4d4d;
        padding: 10px;
        border-radius: 6px;
        margin-bottom: 10px;
        text-align: center;
    }

    .error {
        color: red;
        font-size: 12px;
    }

    .back {
        display: block;
        margin-top: 15px;
        text-align: center;
        color: #bbb;
        text-decoration: none;
    }
</style>

</head>

<body>

<div class="container">

<h2>💳 Payment</h2>

<div class="amount">
    Total: ₹ ${total}
</div>

<!-- ✅ BACKEND ERROR -->
<c:if test="${not empty error}">
    <div class="error-box">
        ${error}
    </div>
</c:if>

<form action="${ctx}/payment/pay" method="post" onsubmit="return validateForm()">

    <!-- Payment Options -->
    <div class="option">
        <input type="radio" name="method" value="UPI" required> UPI
    </div>

    <div class="option">
        <input type="radio" name="method" value="CARD"> Credit / Debit Card
    </div>

    <div class="option">
        <input type="radio" name="method" value="COD"> Cash on Delivery
    </div>

    <!-- UPI Section -->
    <div id="upiSection" class="section">
        <label>UPI ID</label>
        <input type="text" id="upiId" name="upiId" placeholder="example@upi" />
        <div id="upiError" class="error"></div>
    </div>

    <!-- CARD Section -->
    <div id="cardSection" class="section">
        <label>Card Number</label>
        <input type="text" id="cardNumber" name="cardNumber" />
        <div id="cardError" class="error"></div>

        <label>Expiry</label>
        <input type="text" id="expiry" name="expiry" />
        <div id="expiryError" class="error"></div>

        <label>CVV</label>
        <input type="password" id="cvv" name="cvv" />
        <div id="cvvError" class="error"></div>
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

            if (this.value === "UPI") upi.style.display = "block";
            if (this.value === "CARD") card.style.display = "block";
        });
    });

    function validateForm() {
        let method = document.querySelector('input[name="method"]:checked');

        // clear errors
        document.querySelectorAll(".error").forEach(e => e.innerText = "");

        if (!method) return false;

        if (method.value === "UPI") {
            let upiId = document.getElementById("upiId").value;
            if (upiId.trim() === "") {
                document.getElementById("upiError").innerText = "UPI ID is required";
                return false;
            }
        }

        if (method.value === "CARD") {
            let cardNumber = document.getElementById("cardNumber").value;
            let expiry = document.getElementById("expiry").value;
            let cvv = document.getElementById("cvv").value;

            if (cardNumber.trim() === "") {
                document.getElementById("cardError").innerText = "Card number required";
                return false;
            }

            if (expiry.trim() === "") {
                document.getElementById("expiryError").innerText = "Expiry required";
                return false;
            }

            if (cvv.trim() === "") {
                document.getElementById("cvvError").innerText = "CVV required";
                return false;
            }
        }

        return true;
    }
</script>

</body>
</html>