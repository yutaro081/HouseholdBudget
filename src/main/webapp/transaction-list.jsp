<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
// このJSPではJSTL(JSPで繰り返しや条件分岐を書くための部品)を、c:という名前で使います、という宣言。
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

		<%-- ①transactionListはTransactionListServletのsetAttributeで付けた名札の名前。
			 ②c:forEachはTransactionを1件ずつ取り出し、そのたびに表の1行を作っている。
			 ③var="transaction" は、取り出した1枚に付ける呼び名。 					--%>
		<c:forEach var="transaction" items="${transactionList}">
			<tr>
				<%-- ①Transactionから取り出すとき、裏ではgetメソッドが使われている。
					 ②?id=${transaction.id}は、取引を指定するためにあり、このリンクはGETで送られる。
					 TransactionDetailServletがgetParameter("id")でIDを受け取り、DAOのfindByIdでその1件だけを
					 取り出している。--%>
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