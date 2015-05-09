package db.model;

public class Order {
	private int orderId;
	private int customerId;
	private int salesPersonId;
	private String goods;
	private int totalAmount;
	
	public Order() {
	
	}
	
	public Order(int customerId, int salesPersonId, String goods, int totalAmount) {
		this.customerId = customerId;
		this.salesPersonId = salesPersonId;
		this.goods = goods;
		this.totalAmount = totalAmount;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public int getSalesPersonId() {
		return salesPersonId;
	}
	
	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
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
}