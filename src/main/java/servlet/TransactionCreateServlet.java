package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TransactionDAO;
import model.Transaction;

/* transaction-form.jspの、/TransactionCreateServletと同じ文字なので、登録ボタンを押すと
	このServletが呼ばれる。*/
@WebServlet("/TransactionCreateServlet")
public class TransactionCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionCreateServlet() {
		super();
	}

	// フォームがPOSTで送ってくるので、doPostが呼ばれる。
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setCharacterEncoding("UTF-8")を入れないと、受け取る「食費」などの日本語が文字化けしてしまう。
		request.setCharacterEncoding("UTF-8");

		// getParameter("○○")の"○○"は、フォームのnameと対応する。
		String date = request.getParameter("date");
		String category = request.getParameter("category");
		// 金額は、チェックしてから数字に変換するため、いったん文字列のまま受け取る。
		String amountText = request.getParameter("amount");
		int amount = 0;
		String description = request.getParameter("description");
		String paymentMethod = request.getParameter("paymentMethod");
		
		// 入力チェックで見つかったエラーメッセージを入れる箱
		List<String> errors = new ArrayList<>();
		// 日付：必須
		if (date == null || date.isBlank()) {
			errors.add("日付を入力してください");
		}
		
		//科目：必須、10文字以内
		if (category == null || category.isBlank()) {
			errors.add("科目を入力してください");
		} else if (category.length() > 10) {
			errors.add("科目は10文字以内で入力してください");
		}
		
		// 金額：必須、数字、1円以上100万円未満
		if (amountText == null || amountText.isBlank()) {
			errors.add("金額を入力してください");
		} else {
			try {
				// ここで初めて、文字列を数字(int)に変換する
				amount = Integer.parseInt(amountText);
				
				if(amount < 1 || amount >= 1000000) {
					errors.add("金額は1円以上100万円未満で入力してください");
				}
			} catch (NumberFormatException e) {
				// 数字に変換できなかったとき(「abc」や、大きすぎる数など)
				errors.add("金額は数字で入力してください");
			}
		}
		
		//摘要：任意、30文字以内
		if (description != null && description.length() > 30) {
			errors.add("摘要は30文字以内で入力してください");
		}
		
		//支払方法：必須、10文字以内
		if (paymentMethod == null || paymentMethod.isBlank()) {
			errors.add("支払方法を入力してください");
		} else if (paymentMethod.length() > 10) {
			errors.add("支払方法は10文字以内で入力してください");
		}

		/* リダイレクトするとerrorsが消えてしまうのでフォワードする
		    returnを書かないと、処理が下のTransactionオブジェクト作成へと進んでしまう。*/ 
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/transaction-form.jsp")
					.forward(request, response);
			return;
		}
		
		/* Transactionという1つのオブジェクトにまとめることで、
		   5つの値を「1件の取引」というひとまとまりとして扱えるようにする。*/
		Transaction transaction = new Transaction(date, category, amount, description, paymentMethod);

		/* データベース処理を分離する。分離によって、SQLを直すときにDAOだけ見ればよくなる。
		   successにはtrueとfalseが入る。登録に成功したらtrue。失敗したらfalse。*/
		TransactionDAO dao = new TransactionDAO();
		boolean success = dao.insert(transaction);

		/* ①request.getContextPath()は、/HouseholdBudgetという文字になる。
		 * ②登録に成功したとき、sendRedirect で TransactionListServlet に移動するのは、
		 * JSPではなくこの一覧のServletがDAOからデータを取り出すから。
		 * ③リダイレクトにする理由は、登録直後の画面で再読み込みしたときに、
		   直前に送ったものを再度登録してしまうのを防ぐため、PRGパターンにしている。*/
		if (success) {
			response.sendRedirect(
					request.getContextPath() + "/TransactionListServlet");
		} else {
			response.sendError(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"伝票の登録に失敗しました");
		}

	}
}