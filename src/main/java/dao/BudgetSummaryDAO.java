package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.BudgetSummary;

// 予実比較(科目ごとの予算と実績)をDBから取り出すDAO
public class BudgetSummaryDAO {

	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/householdbudget";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	// 期間(start以上、end未満)の、科目ごとの予算と実績を取り出す
	public List<BudgetSummary> findByPeriod(LocalDate start, LocalDate end) {
		List<BudgetSummary> summaryList = new ArrayList<>();

		// JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		// H2で試したSQL。日付の部分を ? にした
		String sql = "SELECT c.id, c.name, c.budget, COALESCE(SUM(t.amount), 0) AS total "
				+ "FROM categories c "
				+ "LEFT JOIN transactions t "
				+ "ON t.category_id = c.id "
				+ "AND t.transaction_date >= ? "
				+ "AND t.transaction_date < ? "
				+ "GROUP BY c.id, c.name, c.budget "
				+ "ORDER BY c.id ";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// ? に期間の開始日と終了日を入れる
			pstmt.setDate(1, Date.valueOf(start));
			pstmt.setDate(2, Date.valueOf(end));

			try (ResultSet rs = pstmt.executeQuery()) {
				// 取り出した行の数だけ繰り返す
				while (rs.next()) {
					String name = rs.getString("name");
					int budget = rs.getInt("budget");
					int total = rs.getInt("total");
					summaryList.add(new BudgetSummary(name, budget, total));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return summaryList;
	}

}