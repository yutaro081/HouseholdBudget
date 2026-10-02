package model;

public class Transaction {

	private int id;
	private String date;
	private String category;
	private int amount;
	private String description;
	private String paymentMethod;

	public Transaction(String date, String category, int amount,
			String description, String paymentMethod) {

		this.date = date;
		this.category = category;
		this.amount = amount;
		this.description = description;
		this.paymentMethod = paymentMethod;
	}

	public Transaction(int id, String date, String category, int amount,
			String description, String paymentMethod) {

		this.id = id;
		this.date = date;
		this.category = category;
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

	public String getCategory() {
		return category;
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