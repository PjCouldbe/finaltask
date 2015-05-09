package db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfiguration {
	 @Value("${db.url}")
	 private String dbUrl;
	 
	 public String getDbUrl() {
		return dbUrl;
	 }
}