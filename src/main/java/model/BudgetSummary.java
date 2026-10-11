package model;

// 予実比較の表の1行分(科目ごとの予算と実績)
public class BudgetSummary {

	private String categoryName; //科目名
	private int budget; //予算(1季節=3か月分)
	private int actual; //実績(その季節の合計金額)

	public BudgetSummary(String categoryName, int budget, int actual) {
		this.categoryName = categoryName;
		this.budget = budget;
		this.actual = actual;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public int getBudget() {
		return budget;
	}

	public int getActual() {
		return actual;
	}

	// 差額 (実績-予算)。プラスなら予算超過
	public int getDifference() {
		return actual - budget;
	}

	// 予算超過か (赤で表示する)
	public boolean isOver() {
		return actual > budget;
	}

	// 予算より少ないか (青で表示する)
	public boolean isUnder() {
		return actual < budget;
	}
}