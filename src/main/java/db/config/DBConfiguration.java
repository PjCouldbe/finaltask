package db.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DBConfiguration {
	 @Value("jdbc:derby:./ITIS;create=true")
	 private String dbUrl;
	 
	 public String getDbUrl() {
		return dbUrl;
	 }
	 
	 @Bean
	 public static DataSource dataSource() {
		final DriverManagerDataSource ds = new DriverManagerDataSource();
	    ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
	    ds.setUrl("jdbc:derby:./ITIS;create=true");
	    return ds;
	 }
}