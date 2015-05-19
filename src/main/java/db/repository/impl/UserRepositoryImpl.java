package db.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import db.model.User;
import db.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
    @Autowired(required = true)
    public void setDataSource(DataSource dataSource) {
        System.out.println("DATASOURCE: " + dataSource);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public UserRepositoryImpl() {
	}

	/*@PostConstruct
	public void create() {
        System.out.println("===create===");
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
    		addUser(new User("Каяшев", "Михаил", "Владиславович", 20));
    		addUser(new User("Абракшян", "Ильмир", "Мухранбекович", 34));
    		addUser(new User("Воротов", "Николай", "Сергеевич", 28));
    		addUser(new User("Прохорова", "Ксения", "Сергеевна", 19));
    		addUser(new User("Павлюк", "Анастасия", "Андреевна", 9));
    	} catch (SQLException ex) {
    		if (!ex.getSQLState().equals("X0Y32")) {
				ex.printStackTrace();
			}
		}
	}*/

	@Override
	public void addUser(final User user) {
		String sql = "INSERT INTO USERS (lastname, firstname, middlename, age) "
				+ "VALUES (:lastname, :firstname, :middlename, :age)";
		SqlParameterSource source = new BeanPropertySqlParameterSource(user);
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, source, holder);
		user.setId(holder.getKey().intValue());
	}

	@Override
	public boolean update(final User user, int id) {
		user.setId(id);
		try {
			String sql = "UPDATE USERS SET lastname = :lastname, "
					+ "firstname = :firstname, "
					+ "middlename = :middlename, "
					+ "age = :age "
					+ "WHERE id = :id";
			SqlParameterSource source = new BeanPropertySqlParameterSource(user);
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
			String sql = "DELETE FROM USERS WHERE id = :id";
			Map<String, Integer> namedParameters = Collections.singletonMap("id", id);
			jdbcTemplate.update(sql, namedParameters);
			logger.info("user deleted");
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User selectUser(int id) {
		//StringBuilder s = new StringBuilder();
		Map<String, Integer> namedParameters = Collections.singletonMap("id", id);
		String sql = "SELECT * FROM USERS WHERE id = :id";
		User user = this.jdbcTemplate.queryForObject(
				sql, 
				namedParameters, 
				new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setLastname(rs.getString("lastname"));
						user.setFirstname(rs.getString("firstname"));
						user.setMiddlename(rs.getString("middlename"));
						user.setAge(rs.getInt("age"));
						return user;
					}
				});
		
		/*s.append(user.getId() 
				+ ", " + user.getLastname()
				+ ", " + user.getFirstname()
				+ ", " + user.getMiddlename()
				+ ", " + user.getAge());
		s.append("\n");*/
		return user;
	}

	@Override
	public List<User> selectGroupUsers(String specialization) {
		if (!(specialization.equals("customer") || specialization.equals("saler"))) {
    		return null;
    	}
    	specialization = specialization.equals("customer") ? "salesPersonId" : "customerId";
		String sql = "SELECT * FROM USERS WHERE id NOT IN (SELECT DISTINCT " 
				+ specialization + " from ORDERS)";
		List<User> users = this.jdbcTemplate.query(
				sql, 
				new HashMap<String, Object>(),
				new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setLastname(rs.getString("lastname"));
						user.setFirstname(rs.getString("firstname"));
						user.setMiddlename(rs.getString("middlename"));
						user.setAge(rs.getInt("age"));
						return user;
					}
				});
		return users;
	}
	
	@Override
	public List<User> showAllUsers() {
        System.out.println(this.jdbcTemplate);
		//StringBuilder s = new StringBuilder();
        List<User> users = new ArrayList<>();
		List<User> userList = this.jdbcTemplate.query(
				"SELECT * FROM USERS", 
				new HashMap<String, Object>(),
				new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setLastname(rs.getString("lastname"));
						user.setFirstname(rs.getString("firstname"));
						user.setMiddlename(rs.getString("middlename"));
						user.setAge(rs.getInt("age"));
						return user;
					}
				});

        for (User user : userList) {
            users.add(user);
        }

		/*for (User u : users) {
			s.append(u.getId() 
					+ ", " + u.getLastname()
					+ ", " + u.getFirstname()
					+ ", " + u.getMiddlename()
					+ ", " + u.getAge());
			s.append("\n");
		}*/

		return users;
	}

	@Override
	public int getCount() {
		String sql = "SELECT COUNT(*) FROM USERS";
		return this.jdbcTemplate.queryForInt(sql, new HashMap<String, Object>());
	}
}