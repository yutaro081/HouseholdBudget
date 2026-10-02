package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TransactionDAO;

@WebServlet("/TransactionDeleteServlet")
public class TransactionDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionDeleteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);

		TransactionDAO dao = new TransactionDAO();
		boolean success = dao.delete(id);

		if (success) {
			response.sendRedirect(
					request.getContextPath() + "/TransactionListServlet");
		} else {
			response.sendError(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"取引の削除に失敗しました");
		}
	}
}