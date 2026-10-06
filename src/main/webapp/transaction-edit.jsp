<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>取引編集</title>
</head>
<body>

	<h1>取引編集</h1>

	<form action="${pageContext.request.contextPath}/TransactionEditServlet"
		method="post">

		<input type="hidden" name="id" value="${transaction.id}">

		<p>
			日付： <input type="date" name="date" value="${transaction.date}"
				required>
		</p>

		<p>
			科目： <input type="text" name="category"
				value="${transaction.category}" required>
		</p>


		<p>
			金額： <input type="number" name="amount" value="${transaction.amount}"
				required>
		</p>

		<p>
			摘要： <input type="text" name="description"
				value="${transaction.description}">
		</p>

		<p>
			支払方法： <input type="text" name="paymentMethod"
				value="${transaction.paymentMethod}" required>
		</p>

		<button type="submit">更新</button>

	</form>

</body>
</html>