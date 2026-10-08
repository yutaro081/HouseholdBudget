<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>取引登録</title>
</head>
<body>

	<h1>取引登録</h1>

	<%-- 登録画面を初めて開いたときやエラーがないときは、errorsはフォワードされて来ていない
		 エラーがあるときは、エラーを表示する --%>
	<c:if test="${not empty errors}">
		<ul style="color: red;">
			<c:forEach var="error" items="${errors}">
				<li>${error}</li>
			</c:forEach>
		</ul>
	</c:if>

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
			日付： <input type="date" name="date"
				value="<c:out value='${param.date}'/>" required>
		</p>

		<p>
			科目： <select name="categoryId" required>
				<option value="">選択してください</option>
				<c:forEach var="category" items="${categoryList}">
					<option value="${category.id}"
						${param.categoryId == category.id ? 'selected' : ''}>
						<c:out value="${category.name}" />
					</option>
				</c:forEach>
			</select>
		</p>

		<p>
			金額： <input type="number" name="amount"
				value="<c:out value='${param.amount}'/>" required>
		</p>

		<p>
			摘要： <input type="text" name="description"
				value="<c:out value='${param.description}'/>">
		</p>

		<p>
			支払方法： <input type="text" name="paymentMethod"
				value="<c:out value='${param.paymentMethod}'/>" required>
		</p>

		<button type="submit">登録</button>

	</form>

	<p>
		<a href="${pageContext.request.contextPath}/TransactionListServlet">
			一覧へ戻る </a>
	</p>

</body>
</html>