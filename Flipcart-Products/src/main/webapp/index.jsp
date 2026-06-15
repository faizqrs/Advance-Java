<!DOCTYPE html>
<html>
<head>
<title>Flipcart Reseller</title>

<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: #0f172a;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        background: #1e293b;
        padding: 30px;
        border-radius: 12px;
        width: 350px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.5);
    }

    h2 {
        text-align: center;
        color: #22c55e;
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
        border-radius: 6px;
        border: none;
        background: #334155;
        color: white;
        outline: none;
    }

    input[type="text"]:focus {
        border: 1px solid #22c55e;
    }

    input[type="submit"] {
        width: 100%;
        padding: 12px;
        background: #22c55e;
        border: none;
        border-radius: 6px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
        color: black;
    }

    input[type="submit"]:hover {
        background: #16a34a;
    }

    .footer {
        text-align: center;
        margin-top: 10px;
        font-size: 12px;
        color: #94a3b8;
    }
</style>

</head>

<body>

<div class="container">
    <h2>Flipcart Products - Reseller</h2>

    <form action="uploadProduct" method="post">

        <label>Product Name</label>
        <input type="text" name="productName" placeholder="Enter product name">

        <label>Product Quantity</label>
        <input type="text" name="productQty" placeholder="Enter quantity">

        <label>Product Description</label>
        <input type="text" name="productDescription" placeholder="Enter description">

        <label>Product Price</label>
        <input type="text" name="productPrice" placeholder="Enter price">

        <label>Sold By</label>
        <input type="text" name="soldBy" placeholder="Seller name">

        <input type="submit" value="Upload Product">

    </form>

    <div class="footer">
        Fast Upload • Smart Selling 🚀
    </div>
</div>

</body>
</html>