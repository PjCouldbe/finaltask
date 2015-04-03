package db.model;

public class Order {
	int orderId;
	int customerId;
	int salesPersonId;
	int totalAmount;
	
	public Order() {
	
	}
	
	public Order(int customerId, int salesPersonId, int totalAmount) {
		this.customerId = customerId;
		this.salesPersonId = salesPersonId;
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
	
	public int getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
}