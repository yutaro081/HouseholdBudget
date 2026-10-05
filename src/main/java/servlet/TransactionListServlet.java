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

	/* 登録のあと、TransactionCreateServletが/TransactionListServletを開くようにブラウザに伝える(sendRedirect)。
	   リダイレクトやリンクでブラウザがURLを開くのはGETで、GETで来たのでdoGetが呼ばれている。 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TransactionDAO dao = new TransactionDAO();

		//  Transaction 1つでは1件の取引しか入らないので、Transactionを何個でも入れられるListで受け取っている。
		List<Transaction> transactionList = dao.findAll();

		/* ①requestは、1回のやり取りの間、Servletから JSP まで一緒に運ばれる「お盆」のようなもの
		 *   setAttributeはそのお盆にデータを載せる命令。"transactionList"はデータにつける名札。transactionListは中身。
		 *   JSPの側では、この名札の名前を使ってデータを取り出す。
		 * ②forwardでtransaction-list.jspに処理を渡しているのは、リダイレクトだとお盆(request)が新しくなり、載せた
		 *   データは消えてしまうため。 */
		request.setAttribute("transactionList", transactionList);

		request.getRequestDispatcher("/transaction-list.jsp")
				.forward(request, response);
	}

	// POSTで送られてきた場合でも、GETと同じ処理をさせるようにdoPostの中でdoGetを呼んでいる。
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}