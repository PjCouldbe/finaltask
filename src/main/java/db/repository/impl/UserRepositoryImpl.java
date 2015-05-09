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

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import db.config.DBConfiguration;
import db.model.User;
import db.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private NamedParameterJdbcTemplate jdbcTemplate;
		
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
	
	public UserRepositoryImpl() {
		
	}

	public void create() {
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
	}

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
		User user = this.jdbcTemplate.queryForObject(sql, namedParameters, User.class);
		
		/*s.append(user.getId() 
				+ ", " + user.getLastname()
				+ ", " + user.getFirstname()
				+ ", " + user.getMiddlename()
				+ ", " + user.getAge());
		s.append("\n");*/
		return user;
	}

	@Override
	public List<User> showAllUsers() {
		//StringBuilder s = new StringBuilder();
		List<User> users = this.jdbcTemplate.query(
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