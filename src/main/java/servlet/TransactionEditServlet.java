package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TransactionDAO;
import model.Transaction;

@WebServlet("/TransactionEditServlet")
public class TransactionEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionEditServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);

		TransactionDAO dao = new TransactionDAO();
		Transaction transaction = dao.findById(id);

		request.setAttribute("transaction", transaction);

		request.getRequestDispatcher("/transaction-edit.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		int id = Integer.parseInt(request.getParameter("id"));
		String date = request.getParameter("date");
		String category = request.getParameter("category");
		int amount = Integer.parseInt(request.getParameter("amount"));
		String description = request.getParameter("description");
		String paymentMethod = request.getParameter("paymentMethod");

		Transaction transaction = new Transaction(
				id,
				date,
				category,
				amount,
				description,
				paymentMethod);

		TransactionDAO dao = new TransactionDAO();
		boolean success = dao.update(transaction);

		if (success) {
			response.sendRedirect(request.getContextPath() + "/TransactionDetailServlet?id=" + id);
		} else {
			response.sendError(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"取引の更新に失敗しました");
		}
	}
}