package db.repository;

import java.util.List;

import db.model.Order;

public interface OrderRepository {
	void addOrder(final Order order);
	
	boolean update(final Order order, int id);
	
	boolean delete(int id);
	
	String selectOrder(int id);
	
	List<Order> showAllOrders();
	
	int getCount();
	
	Integer[] getPeople(String specialization);
}
