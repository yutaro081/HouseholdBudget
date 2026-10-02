package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TransactionDAO;
import model.Transaction;

@WebServlet("/TransactionDetailServlet")
public class TransactionDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionDetailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);

		TransactionDAO dao = new TransactionDAO();
		Transaction transaction = dao.findById(id);

		request.setAttribute("transaction", transaction);

		request.getRequestDispatcher("/transaction-detail.jsp")
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}