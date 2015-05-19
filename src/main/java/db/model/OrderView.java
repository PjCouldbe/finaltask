package db.model;

public class OrderView {
	private int id;
	private String customerData;
	private String salerData;
	private String goods;
	private int totalAmount;
	
	public OrderView() {
		
	}
	
	public OrderView(int id, String customerData, String salerData, String goods, int totalAmount) {
		this.id = id;
		this.customerData = customerData;
		this.salerData = salerData;
		this.goods = goods;
		this.totalAmount = totalAmount;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCustomerData() {
		return customerData;
	}
	
	public void setCustomerData(String customerData) {
		this.customerData = customerData;
	}
	
	public String getSalerData() {
		return salerData;
	}
	
	public void setSalerData(String salerData) {
		this.salerData = salerData;
	}
	
	public String getGoods() {
		return goods;
	}
	
	public void setGoods(String goods) {
		this.goods = goods;
	}
	
	public int getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Override
	public String toString() {
		return this.getCustomerData() + " -> " 
				+ this.getSalerData() + ": "
				+ this.getGoods() + " ("
				+ this.getTotalAmount() + ")";
	}
}