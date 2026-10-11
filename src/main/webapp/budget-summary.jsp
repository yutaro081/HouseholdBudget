<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>予実比較</title>
<style>
	table { border-collapse: collapse; }
	th, td { border: 1px solid #999; padding: 4px 12px; }
	.num { text-align: right; }  /* 金額は右寄せ */
	.over { color: red; } /* 予算超過 */
	.under { color: blue; } /* 予算内 */
	.total { font-weight: bold; } /* 合計行 */
</style>
</head>
<body>
<h1>予実比較(科目別)</h1>

<%-- 年と季節を選ぶフォーム --%>
<form action="${pageContext.request.contextPath}/BudgetSummaryServlet" method="get">
	<input type="number" name="year" value="${year}" min="2000" max="2100" required>年
	<select name="season">
		<option value="spring" ${season == 'spring' ? 'selected' : ''}>春(3～5月) </option>
		<option value="summer" ${season == 'summer' ? 'selected' : ''}>夏(6～8月) </option>
		<option value="autumn" ${season == 'autumn' ? 'selected' : ''}>秋(9～11月) </option>
		<option value="winter" ${season == 'winter' ? 'selected' : ''}>冬(12～2月) </option>
	</select>
	<button type="submit">表示</button>
</form>

<h2><c:out value="${year}" />年 <c:out value="${seasonName}" /></h2>

<table>
	<tr>
		<th>科目</th>
		<th>予算</th>
		<th>実績</th>
		<th>差額(実績-予算)</th>
	</tr>
	
	<%-- 科目ごとの行。超過なら赤、予算内なら青、同じなら色なし --%>
	<c:forEach var="summary" items="${summaryList}">
		<c:set var="colorClass" value="${summary.over ? 'over' : (summary.under ? 'under' : '')}" />
		<tr>
			<td><c:out value="${summary.categoryName}" /></td>
			<td class="num"><fmt:formatNumber value="${summary.budget}" pattern="#,##0" />円</td>
			<td class="num ${colorClass}"><fmt:formatNumber value="${summary.actual}" pattern="#,##0" />円</td>
			<td class="num ${colorClass}"><fmt:formatNumber value="${summary.difference}" pattern="#,##0" />円</td>
		</tr>
	</c:forEach>
			
	<%-- 合計行(色分けのルールは科目の行と同じ) --%>
	<c:set var="totalClass" value="${totalSummary.over ? 'over' : (totalSummary.under ? 'under' : '')}" />
	<tr class="total">
		<td>合計</td>
		<td class="num"><fmt:formatNumber value="${totalSummary.budget}" pattern="#,##0" />円</td>
		<td class="num ${totalClass}"><fmt:formatNumber value="${totalSummary.actual}" pattern="#,##0" />円</td>
		<td class="num ${totalClass}"><fmt:formatNumber value="${totalSummary.difference}" pattern="#,##0" />円</td>
	</tr>
</table>

<p>赤：予算超過  青：予算内</p>

<a href="${pageContext.request.contextPath}/TransactionListServlet">一覧に戻る</a>
</body>
</html>