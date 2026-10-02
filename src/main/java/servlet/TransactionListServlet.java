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

@WebServlet("/TransactionListServlet")
public class TransactionListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TransactionDAO dao = new TransactionDAO();

		List<Transaction> transactionList = dao.findAll();

		request.setAttribute("transactionList", transactionList);

		request.getRequestDispatcher("/transaction-list.jsp")
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}