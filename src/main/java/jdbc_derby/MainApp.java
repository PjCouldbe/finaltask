package jdbc_derby;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import db.model.Order;
import db.model.User;
import db.repository.OrderRepository;
import db.repository.UserRepository;

public class MainApp {
	private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

	public void run() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		UserRepository userRep = 
				(UserRepository)context.getBean("userRepository", UserRepository.class);
		OrderRepository orderRep = 
				(OrderRepository)context.getBean("orderRepository", OrderRepository.class);
		logger.info("Welcome to the Main Application!");
		logger.info("url = " + userRep.getConfig().getDbUrl());
		
		//initial USERS check
		logger.info("USERS table is now available!");
		logger.info("Current user count: " + userRep.getCount());
		logger.info("USERS' content: " + "\n" + userRep.showAllUsers());

		//adding users
		userRep.addUser(new User("fam1", "nam1", "mid1", 21));
		userRep.addUser(new User("fam2", "nam2", "mid2", 22));
		userRep.addUser(new User("fam3", "nam3", "mid3", 23));
		
		logger.info("3 users have been added!");
		logger.info("Current user count: " + userRep.getCount());

		//initial ORDERS check
		logger.info("ORDERS table is now available!");
		logger.info("Current order count: " + orderRep.getCount());
		logger.info("ORDERS' content: " + "\n" + orderRep.showAllOrders());

		//adding orders
		orderRep.addOrder(new Order(2, 1, 1000));
		orderRep.addOrder(new Order(3, 1, 2500));

		logger.info("2 orders have been added!");

		//updates & deletes
		orderRep.update(new Order(2, 1, 1300), 3);
		logger.info("Order has been updated! ");

		orderRep.delete(2);
		logger.info("Order has been deleted! ");

		userRep.delete(3);
		logger.info("User has been deleted! ");

		userRep.update(new User("fam1", "nam4", "mid1", 27), 1);
		logger.info("User has been updated! ");

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