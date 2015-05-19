package db.config;

import db.model.Order;
import db.model.User;
import db.repository.OrderRepository;
import db.repository.UserRepository;
import db.web.DBUserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ayratn on 12/05/15.
 */
@Configuration
public class AppConfig {
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	
    @Autowired
    private DBConfiguration config;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public static void init() {
        logger.info("INIT");
    }

    @PostConstruct
    public void initBean() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        createUsers();
        createOrders();
    }

    protected void createUsers() {
        System.out.println("CREATE USERS: " + config.getDbUrl());

        try (Connection conn = DriverManager.getConnection(config.getDbUrl())){
            Statement stmt = conn.createStatement();
            String creator = "CREATE TABLE USERS ("
                    + "id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "lastname VARCHAR(256),"
                    + "firstname VARCHAR(256),"
                    + "middlename VARCHAR(256),"
                    + "age INTEGER not NULL,"
                    + "PRIMARY KEY(id))";
            stmt.execute(creator);
            userRepository.addUser(new User("Каяшев", "Михаил", "Владиславович", 20));
            userRepository.addUser(new User("Абракшян", "Ильмир", "Мухранбекович", 34));
            userRepository.addUser(new User("Воротов", "Николай", "Сергеевич", 28));
            userRepository.addUser(new User("Прохорова", "Ксения", "Сергеевна", 19));
            userRepository.addUser(new User("Павлюк", "Анастасия", "Андреевна", 9));
        } catch (SQLException ex) {
            if (!ex.getSQLState().equals("X0Y32")) {
                ex.printStackTrace();
            }
        }

        System.out.println("DONE");
    }

    protected void createOrders() {
        System.out.println("CREATE ORDERS: " + config.getDbUrl());

        try (Connection conn = DriverManager.getConnection(config.getDbUrl())){
            Statement stmt = conn.createStatement();
            String creator = "CREATE TABLE ORDERS ("
                    + "orderId INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "customerId INTEGER not NULL,"
                    + "salesPersonId INTEGER not NULL,"
                    + "goods VARCHAR(256),"
                    + "totalAmount INTEGER not NULL,"
                    + "PRIMARY KEY(orderId))";
            stmt.execute(creator);

            orderRepository.addOrder(new Order(1, 2, "слоник_игрушечный_2_шт", 1200));
            orderRepository.addOrder(new Order(4, 2, "кукла_пластмассовая_1_шт", 230));
            orderRepository.addOrder(new Order(5, 3, "тортик бисквитный_1_шт", 340));
        } catch (SQLException ex) {
            if (!ex.getSQLState().equals("X0Y32")) {
                ex.printStackTrace();
            }
        }

        System.out.println("DONE");
    }
}
