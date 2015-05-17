package db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DBConfiguration {
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
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        System.out.println("DB URL: " + dbUrl);
        ds.setUrl(dbUrl);

        return ds;
    }
}