package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Category;

public class CategoryDAO {

	public List<Category> findAll() {

		List<Category> categoryList = new ArrayList<>();

		String sql = "SELECT id, name FROM categories ORDER BY id";

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
				   ②while (rs.next())は、categoriesテーブルにある取り出した結果（ResultSet）の
				   	行の数だけ繰り返される。 */
				while (rs.next()) {

					int id = rs.getInt("id");
					String name = rs.getString("name");

					Category category = new Category(
							id,
							name);
					categoryList.add(category);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categoryList;
	}
}