package db.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@DependsOn({"appConfig"})
@PropertySource("file:/tmp/db-env.properties")
public class DBConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(DBConfiguration.class);
	
	
    @Value("${db.url}")
    private String dbUrl;

    public String getDbUrl() {
        return dbUrl;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
    	logger.info("bean: datasource");
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        logger.info("DB URL: " + dbUrl);
        ds.setUrl(dbUrl);

        return ds;
    }
}