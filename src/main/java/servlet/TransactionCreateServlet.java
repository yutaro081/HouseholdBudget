package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TransactionDAO;
import model.Transaction;
import validator.TransactionValidator;

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
		String description = request.getParameter("description");
		String paymentMethod = request.getParameter("paymentMethod");

		// チェック担当に5つの値を渡して、エラーの入った箱を受け取る
		List<String> errors = TransactionValidator.validate(
				date, category, amountText, description, paymentMethod);

		/* リダイレクトするとerrorsが消えてしまうのでフォワードする
		    returnを書かないと、処理が下のTransactionオブジェクト作成へと進んでしまう。*/
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/transaction-form.jsp")
					.forward(request, response);
			return;
		}

		// 入力チェックを通ったので、ここでは必ず数字に変換できる
		int amount = Integer.parseInt(amountText);

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