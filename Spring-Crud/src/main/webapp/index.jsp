<html>
<body>
<h2>Hello World!</h2>
<form action="${pageContext.request.contextPath}/create" method="post">
Name: <input type="text" name="itemName"> <br>
Price : <input type="text" name="itemPrice"><br>
status: <input type="text" name="itemStatus"><br>
<input type="submit" value="Submit">
</form>
</body>
</html>
