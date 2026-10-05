package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Transaction;

public class TransactionDAO {

	public List<Transaction> findAll() {

		// new ArrayList<>()で空の箱を用意して、そこにTransactionを入れるようになる。
		List<Transaction> transactionList = new ArrayList<>();

		/* ①このSELECT文でデータベースの transactions テーブルの行を取り出し、ORDER BY idで昇順に並べている。
		   ②findAll()では利用者が入力した値は何もないので、?は使う必要がない。 */
		String sql = "SELECT id, transaction_date, category, amount, description, payment_method "
				+ "FROM transactions ORDER BY id";

		try {
			Class.forName("org.h2.Driver");

			try (
					Connection conn = DriverManager.getConnection(
							"jdbc:h2:tcp://localhost/~/householdbudget",
							"sa",
							"");

					PreparedStatement pstmt = conn.prepareStatement(sql);

					ResultSet rs = pstmt.executeQuery();) {

				/* ①executeQuery()で結果の表であるResultSetを受け取っている。
				 * ②while (rs.next())は、transactionsテーブルにある表の最後まで繰り返される。
				 */
				while (rs.next()) {

					// "transaction_date"は日付で、Java側の変数名はdate。
					int id = rs.getInt("id");
					String date = rs.getString("transaction_date");
					String category = rs.getString("category");
					int amount = rs.getInt("amount");
					String description = rs.getString("description");
					String paymentMethod = rs.getString("payment_method");

					Transaction transaction = new Transaction(
							id,
							date,
							category,
							amount,
							description,
							paymentMethod);
					// new Transaction()で伝票を1枚作り、addでそれを箱(transactionList)の最後に入れる。
					transactionList.add(transaction);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return transactionList;
	}

	public boolean insert(Transaction transaction) {

		/* ①このSQLは、transactionsテーブルに対して、1行追加する命令。
		 * ②利用者が入力した文字がそのままSQLの文章に張り付けてしまうと、SQLインジェクション攻撃の
		 *   おそれがあるため、VALUESの後ろを、実際の値ではなく?にして、入力された文字が「ただの値」となるようにする。
		 * ③列の一覧にidが入っていないのは、IDはデータベースが自動で番号を振っているため。
		 */
		String sql = "INSERT INTO transactions "
				+ "(transaction_date, category, amount, description, payment_method) "
				+ "VALUES (?, ?, ?, ?, ?)";

		// Class.forName("org.h2.Driver"); は、H2 Databaseとやり取りするための部品（ドライバ）を読み込む命令。
		try {
			Class.forName("org.h2.Driver");

			/* getConnectionには、URL、ユーザ名、パスワードの3つの値を渡している。
			 * conn.prepareStatement(sql)では、?の入ったSQLを、後から値をはめこめる形で準備している。
			   登録が途中で失敗してもデータベースへの接続を閉じられるように、処理をtry()で囲っている。 */
			try (
					Connection conn = DriverManager.getConnection(
							"jdbc:h2:tcp://localhost/~/householdbudget",
							"sa",
							"");

					PreparedStatement pstmt = conn.prepareStatement(sql);) {

				/* ①"1"は、SQLの1つ目の"?"を表す。transaction.getDate()の値は、もともと
				 *    フォームから送られてきたものを TransactionCreateServlet が受け取って Transaction に詰めたもの。
				 * ②金額は数値なので、金額だけsetIntになっている。 */
				pstmt.setString(1, transaction.getDate());
				pstmt.setString(2, transaction.getCategory());
				pstmt.setInt(3, transaction.getAmount());
				pstmt.setString(4, transaction.getDescription());
				pstmt.setString(5, transaction.getPaymentMethod());

				/* executeUpdate() は、追加・変更・削除された行の数を返す。1件追加できた場合、resultは1でtrueとなる。
				   ここで返したtrue/falseがinsertの呼び出し元であるTransactionCreateServletのsuccessに入り、
				   成功ならリダイレクトとなる。*/
				int result = pstmt.executeUpdate();

				return result == 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public Transaction findById(int id) {

		String sql = "SELECT id, transaction_date, category, amount, description, payment_method "
				+ "FROM transactions WHERE id = ?";

		try {
			Class.forName("org.h2.Driver");

			try (
					Connection conn = DriverManager.getConnection(
							"jdbc:h2:tcp://localhost/~/householdbudget",
							"sa",
							"");

					PreparedStatement pstmt = conn.prepareStatement(sql);) {

				pstmt.setInt(1, id);

				try (ResultSet rs = pstmt.executeQuery()) {

					if (rs.next()) {

						String date = rs.getString("transaction_date");
						String category = rs.getString("category");
						int amount = rs.getInt("amount");
						String description = rs.getString("description");
						String paymentMethod = rs.getString("payment_method");

						return new Transaction(
								id,
								date,
								category,
								amount,
								description,
								paymentMethod);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean update(Transaction transaction) {

		String sql = "UPDATE transactions "
				+ "SET transaction_date = ?, category = ?, amount = ?, "
				+ "description = ?, payment_method = ? "
				+ "WHERE id = ?";

		try {
			Class.forName("org.h2.Driver");

			try (
					Connection conn = DriverManager.getConnection(
							"jdbc:h2:tcp://localhost/~/householdbudget",
							"sa",
							"");

					PreparedStatement pstmt = conn.prepareStatement(sql);) {

				pstmt.setString(1, transaction.getDate());
				pstmt.setString(2, transaction.getCategory());
				pstmt.setInt(3, transaction.getAmount());
				pstmt.setString(4, transaction.getDescription());
				pstmt.setString(5, transaction.getPaymentMethod());
				pstmt.setInt(6, transaction.getId());

				int result = pstmt.executeUpdate();

				return result == 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean delete(int id) {

		String sql = "DELETE FROM transactions WHERE id = ?";

		try {
			Class.forName("org.h2.Driver");

			try (
					Connection conn = DriverManager.getConnection(
							"jdbc:h2:tcp://localhost/~/householdbudget",
							"sa",
							"");

					PreparedStatement pstmt = conn.prepareStatement(sql);) {

				pstmt.setInt(1, id);

				int result = pstmt.executeUpdate();

				return result == 1;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return false;

	}

}