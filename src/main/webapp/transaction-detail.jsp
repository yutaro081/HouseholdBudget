<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix='c' uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>取引詳細</title>
</head>
<body>

	<h1>取引詳細</h1>

	<p>ID：<c:out value="${transaction.id}"/></p>
	<p>日付：<c:out value="${transaction.date}"/></p>
	<p>科目：<c:out value="${transaction.category}"/></p>
	<p>金額：<c:out value="${transaction.amount}"/></p>
	<p>摘要：<c:out value="${transaction.description}"/></p>
	<p>支払方法：<c:out value="${transaction.paymentMethod}"/></p>

	<p>
		<a
			href="${pageContext.request.contextPath}/TransactionEditServlet?id=${transaction.id}">
			編集 </a>
	</p>

	<form
		action="${pageContext.request.contextPath}/TransactionDeleteServlet"
		method="post" onsubmit="return confirm('この取引を削除しますか？');">
		<input type="hidden" name="id" value="${transaction.id}">
		<button type="submit">削除</button>
	</form>


	<p>
		<a href="${pageContext.request.contextPath}/TransactionListServlet">
			一覧へ戻る </a>
	</p>

</body>
</html>