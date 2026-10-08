package model;

public class Transaction {

	private int id;
	private String date;
	private int categoryId;
	private String categoryName;
	private int amount;
	private String description;
	private String paymentMethod;

	public Transaction(String date, int categoryId, int amount,
			String description, String paymentMethod) {

		this.date = date;
		this.categoryId = categoryId;
		this.amount = amount;
		this.description = description;
		this.paymentMethod = paymentMethod;
	}

	public Transaction(int id, String date, int categoryId, int amount,
			String description, String paymentMethod) {

		this.id = id;
		this.date = date;
		this.categoryId = categoryId;
		this.amount = amount;
		this.description = description;
		this.paymentMethod = paymentMethod;
	}

	public Transaction(int id, String date, int categoryId, String categoryName, int amount,
			String description, String paymentMethod) {

		this.id = id;
		this.date = date;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.amount = amount;
		this.description = description;
		this.paymentMethod = paymentMethod;
	}
	
	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public int getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}
}