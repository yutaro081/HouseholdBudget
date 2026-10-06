<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>取引登録</title>
</head>
<body>

	<h1>取引登録</h1>

	<%-- TransactionCreateServletに送る。
		${pageContext.request.contextPath} は /HouseholdBudget に置き換わる。
   		データ登録のため、postを使う --%>
	<form
		action="${pageContext.request.contextPath}/TransactionCreateServlet"
		method="post">

   		<%-- name=""の部分でServletは日付や金額を見分ける。
   			requiredがあると、空欄の入力をブラウザがはじいてくれる。
   			摘要にrequiredがないのは、空欄でも記録として成り立つので、必須にしていない。 --%>
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