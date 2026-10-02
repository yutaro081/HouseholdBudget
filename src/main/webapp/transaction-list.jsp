<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>家計簿一覧</title>
</head>
<body>

	<h1>取引一覧</h1>

	<p>
		<a href="${pageContext.request.contextPath}/transaction-form.jsp">
			新しい取引を登録 </a>
	</p>

	<table border="1">
		<tr>
			<th>ID</th>
			<th>日付</th>
			<th>科目</th>
			<th>金額</th>
			<th>摘要</th>
			<th>支払方法</th>
			<th>詳細</th>
		</tr>

		<c:forEach var="transaction" items="${transactionList}">
			<tr>
				<td>${transaction.id}</td>
				<td>${transaction.date}</td>
				<td>${transaction.category}</td>
				<td>${transaction.amount}</td>
				<td>${transaction.description}</td>
				<td>${transaction.paymentMethod}</td>
				<td><a
					href="${pageContext.request.contextPath}/TransactionDetailServlet?id=${transaction.id}">
						詳細 </a></td>
			</tr>
		</c:forEach>


	</table>

</body>
</html>