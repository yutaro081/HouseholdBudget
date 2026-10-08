package validator;

import java.util.ArrayList;
import java.util.List;

public class TransactionValidator {

	// 入力された5つの値をチェックし、見つかったエラーメッセージの束を返す
	public static List<String> validate(String date, String categoryIdText, String amountText,
			String description, String paymentMethod) {

		// 入力チェックで見つかったエラーメッセージを入れる箱
		List<String> errors = new ArrayList<>();
		// 日付：必須
		if (date == null || date.isBlank()) {
			errors.add("日付を入力してください");
		}

		if (categoryIdText == null || categoryIdText.isBlank()) {
			errors.add("科目を選択してください");
		} else {
			try {
				int categoryId = Integer.parseInt(categoryIdText);

				if (categoryId < 1) {
					errors.add("科目を正しく選択してください");
				}
			} catch (NumberFormatException e) {
				errors.add("科目を正しく選択してください");
			}
		}

		// 金額：必須、数字、1円以上100万円未満
		if (amountText == null || amountText.isBlank()) {
			errors.add("金額を入力してください");
		} else {
			try {
				// ここで初めて、文字列を数字(int)に変換する
				int amount = Integer.parseInt(amountText);

				if (amount < 1 || amount >= 1000000) {
					errors.add("金額は1円以上100万円未満で入力してください");
				}
			} catch (NumberFormatException e) {
				// 数字に変換できなかったとき(「abc」や、大きすぎる数など)
				errors.add("金額は数字で入力してください");
			}
		}

		//摘要：任意、30文字以内
		if (description != null && description.length() > 30) {
			errors.add("摘要は30文字以内で入力してください");
		}

		//支払方法：必須、10文字以内
		if (paymentMethod == null || paymentMethod.isBlank()) {
			errors.add("支払方法を入力してください");
		} else if (paymentMethod.length() > 10) {
			errors.add("支払方法は10文字以内で入力してください");
		}

		return errors;
	}
}
