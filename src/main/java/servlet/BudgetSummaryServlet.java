package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.BudgetSummaryDAO;
import model.BudgetSummary;

// 予実比較画面(季節ごとの、科目別の予算と実績)を表示するServlet
@WebServlet("/BudgetSummaryServlet")
public class BudgetSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ① 初めて開いたときは、今日を含む季節を表示する
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		String season;
		if (month >= 3 && month <= 5) {
			season = "spring";
		} else if (month >= 6 && month <= 8) {
			season = "summer";
		} else if (month >= 9 && month <= 11) {
			season = "autumn";
		} else {
			season = "winter";
			if (month <= 2) {
				year = year - 1;
			}
		}

		// ② 画面で年と季節が選ばれていれば、そちらを使う(おかしな値なら①のまま)
		String yearText = request.getParameter("year");
		String seasonParam = request.getParameter("season");
		if (yearText != null && isSeason(seasonParam)) {
			try {
				int selectedYear = Integer.parseInt(yearText);
				if (selectedYear >= 2000 && selectedYear <= 2100) {
					year = selectedYear;
					season = seasonParam;
				}
			} catch (NumberFormatException e) {
				// 数字でなければ①のまま
			}
		}

		// ③ 季節から期間を決める(開始日以上、終了日未満)
		int startMonth;
		String seasonName;
		switch (season) {
		case "spring":
			startMonth = 3;
			seasonName = "春(3～5月)";
			break;
		case "summer":
			startMonth = 6;
			seasonName = "夏(6～8月)";
			break;
		case "autumn":
			startMonth = 9;
			seasonName = "秋(9～11月)";
			break;
		default:
			startMonth = 12;
			seasonName = "冬(12～2月)";
			break;
		}
		LocalDate start = LocalDate.of(year, startMonth, 1);
		LocalDate end = start.plusMonths(3); // 冬なら翌年の3月1日になる

		//  ④ DAOで、科目ごとの予算と実績を取り出す
		BudgetSummaryDAO dao = new BudgetSummaryDAO();
		List<BudgetSummary> summaryList = dao.findByPeriod(start, end);
		if (summaryList == null) {
			response.sendError(500, "予実比較の取得に失敗しました");
			return;
		}

		// ⑤ 合計行を作る(科目名を「合計」にした BudgetSummary)
		int totalBudget = 0;
		int totalActual = 0;
		for (BudgetSummary summary : summaryList) {
			totalBudget += summary.getBudget();
			totalActual += summary.getActual();
		}
		BudgetSummary totalSummary = new BudgetSummary("合計", totalBudget, totalActual);

		// ⑥ JSPに渡す
		request.setAttribute("summaryList", summaryList);
		request.setAttribute("totalSummary", totalSummary);
		request.setAttribute("year", year);
		request.setAttribute("season", season);
		request.setAttribute("seasonName", seasonName);
		request.getRequestDispatcher("/budget-summary.jsp").forward(request, response);
	}

	// 季節の指定が正しいか(4つのどれかならtrue)
	private boolean isSeason(String s) {
		return "spring".equals(s) || "summer".equals(s)
				|| "autumn".equals(s) || "winter".equals(s);
	}
}