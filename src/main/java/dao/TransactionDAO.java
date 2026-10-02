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

		List<Transaction> transactionList = new ArrayList<>();

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

				while (rs.next()) {

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
					transactionList.add(transaction);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return transactionList;
	}

	public boolean insert(Transaction transaction) {

		String sql = "INSERT INTO transactions "
				+ "(transaction_date, category, amount, description, payment_method) "
				+ "VALUES (?, ?, ?, ?, ?)";

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