package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TransactionDAO;
import model.Transaction;

@WebServlet("/TransactionCreateServlet")
public class TransactionCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionCreateServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String date = request.getParameter("date");
		String category = request.getParameter("category");
		int amount = Integer.parseInt(request. getParameter("amount"));
		String description = request.getParameter("description");
		String paymentMethod = request.getParameter("paymentMethod");

		Transaction transaction = new Transaction(date, category, amount, description, paymentMethod);

		TransactionDAO dao = new TransactionDAO();
		boolean success = dao.insert(transaction);

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