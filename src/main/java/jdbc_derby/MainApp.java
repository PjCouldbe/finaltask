package jdbc_derby;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import db.repository.impl.OrderRepositoryImpl;
import db.repository.impl.UserRepositoryImpl;

public class MainApp {
	private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

	public void run() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		UserRepositoryImpl userRep = 
				(UserRepositoryImpl)context.getBean("userRepository", UserRepositoryImpl.class);
		OrderRepositoryImpl orderRep = 
				(OrderRepositoryImpl)context.getBean("orderRepository", OrderRepositoryImpl.class);
		logger.info("Welcome to the Main Application!");
		logger.info("url = " + userRep.getConfig().getDbUrl());
		
		//initial USERS check
		logger.info("USERS table is now available!");
		logger.info("Current user count: " + userRep.getCount());
		logger.info("USERS' content: " + "\n" + userRep.showAllUsers());

		//initial ORDERS check
		logger.info("ORDERS table is now available!");
		logger.info("Current order count: " + orderRep.getCount());
		logger.info("ORDERS' content: " + "\n" + orderRep.showAllOrders());

		
		
		//final checking
		logger.info("USERS' content: " + "\n" + userRep.showAllUsers());
		logger.info("Current user count: " + userRep.getCount());
		logger.info("ORDERS' content: " + "\n" + orderRep.showAllOrders());
		logger.info("Current order count: " + orderRep.getCount());

		context.close();
	}

	public static void main(String[] args) {
		MainApp main = new MainApp();
		main.run();
	}
}