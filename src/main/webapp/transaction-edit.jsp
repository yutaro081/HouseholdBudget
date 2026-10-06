<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>取引編集</title>
</head>
<body>

	<h1>取引編集</h1>

	<c:if test="${not empty errors}">
		<ul style="color: red;">
			<c:forEach var="error" items="${errors}">
				<li>${error}</li>
			</c:forEach>
		</ul>
	</c:if>

	<form
		action="${pageContext.request.contextPath}/TransactionEditServlet"
		method="post">

		<input type="hidden" name="id"
			value="<c:out value='${empty errors ? transaction.id : param.id}'/>">

		<p>
			日付： <input type="date" name="date"
				value="<c:out value='${empty errors ? transaction.date : param.date}'/>"
				required>
		</p>

		<p>
			科目： <input type="text" name="category"
				value="<c:out value='${empty errors ? transaction.category : param.category}'/>"
				required>
		</p>


		<p>
			金額： <input type="number" name="amount"
				value="<c:out value='${empty errors ? transaction.amount : param.amount}'/>"
				required>
		</p>

		<p>
			摘要： <input type="text" name="description"
				value="<c:out value='${empty errors ? transaction.description : param.description}'/>">
		</p>

		<p>
			支払方法： <input type="text" name="paymentMethod"
				value="<c:out value='${empty errors ? transaction.paymentMethod : param.paymentMethod}'/>"
				required>
		</p>

		<button type="submit">更新</button>

	</form>

</body>
</html>