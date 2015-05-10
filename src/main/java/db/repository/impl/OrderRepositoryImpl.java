package db.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import db.config.DBConfiguration;
import db.model.Order;
import db.repository.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Resource
	@Bean
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired(required=true)
    private DBConfiguration config;

    public DBConfiguration getConfig() {
        return config;
    }

    public void setConfig(DBConfiguration config) {
        this.config = config;
    }
	
	public OrderRepositoryImpl() {
		
	}
	
	@PostConstruct
    public void create() {
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
    		
    		addOrder(new Order(1, 2, "слоник_игрушечный_2_шт", 1200));
    		addOrder(new Order(4, 2, "кукла_пластмассовая_1_шт", 230));
    		addOrder(new Order(5, 3, "тортик бисквитный_1_шт", 340));
    	} catch (SQLException ex) {
    		if (!ex.getSQLState().equals("X0Y32")) {
				ex.printStackTrace();
			}
		}
	}
    
    @Override
	public void addOrder(final Order order) {
		String sql = "INSERT INTO ORDERS (customerId, salesPersonId, totalAmount) "
				+ "VALUES (:customerId, :salesPersonId, :totalAmount)";
		SqlParameterSource source = new BeanPropertySqlParameterSource(order);
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, source, holder);
		order.setOrderId(holder.getKey().intValue());
	}
	
    @Override
	public boolean update(final Order order, int id) {
		order.setOrderId(id);
		try {
			String sql = "UPDATE ORDERS SET customerId = :customerId, "
					+ "salesPersonId = :salesPersonId, "
					+ "goods = :goods, "
					+ "totalAmount = :totalAmount "
					+ "WHERE orderId = :orderId";
			SqlParameterSource source = new BeanPropertySqlParameterSource(order);
			jdbcTemplate.update(sql, source);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
    @Override
	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM ORDERS WHERE orderId = :orderId";
			Map<String, Integer> namedParameters = Collections.singletonMap("orderId", id);
			jdbcTemplate.update(sql, namedParameters);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
    @Override
	public String selectOrder(int id) {	
		StringBuilder s = new StringBuilder();
		Map<String, Integer> namedParameters = Collections.singletonMap("orderId", id);
		String sql = "SELECT * FROM USERS WHERE id = :orderId";
		Order order = this.jdbcTemplate.queryForObject(sql, namedParameters, Order.class);
		
		s.append(order.getOrderId() 
				+ ", " + order.getCustomerId()
				+ ", " + order.getSalesPersonId()
				+ ", " + order.getGoods()
				+ ", " + order.getTotalAmount());
		s.append("\n");
		return s.toString();
	}
	
    @Override
	public List<Order> showAllOrders() {
		//StringBuilder s = new StringBuilder();
		List<Order> orders = this.jdbcTemplate.query(
				"SELECT * FROM ORDERS", 
				new HashMap<String, Object>(),
				new RowMapper<Order>() {
					@Override
					public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
						Order order = new Order();
						order.setOrderId(rs.getInt("orderId"));
						order.setCustomerId(rs.getInt("customerId"));
						order.setSalesPersonId(rs.getInt("salesPersonId"));
						order.setGoods(rs.getString("goods"));
						order.setTotalAmount(rs.getInt("totalAmount"));
						return order;
					}
				});
		
		/*for (Order o : orders) {
			s.append(o.getOrderId() 
					+ ", " + o.getCustomerId()
					+ ", " + o.getSalesPersonId()
					+ ", " + o.getTotalAmount());
			s.append("\n");
		}*/
		return orders;
	}
	
    @Override
	public int getCount() {
		String sql = "SELECT COUNT(*) FROM ORDERS";
		return this.jdbcTemplate.queryForInt(sql, new HashMap<String, Object>());
	}
}