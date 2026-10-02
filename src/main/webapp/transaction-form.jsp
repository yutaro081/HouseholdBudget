<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>取引登録</title>
</head>
<body>

	<h1>取引登録</h1>

	<form
		action="${pageContext.request.contextPath}/TransactionCreateServlet"
		method="post">

		<p>
			日付： <input type="date" name="date" required>
		</p>

		<p>
			科目： <input type="text" name="category" required>
		</p>

		<p>
			金額： <input type="number" name="amount" required>
		</p>

		<p>
			摘要： <input type="text" name="description">
		</p>

		<p>
			支払方法： <input type="text" name="paymentMethod" required>
		</p>

		<button type="submit">登録</button>

	</form>

	<p>
		<a href="${pageContext.request.contextPath}/TransactionListServlet">
			一覧へ戻る </a>
	</p>

</body>
</html>