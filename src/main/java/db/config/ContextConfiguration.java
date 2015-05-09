package db.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ContextConfiguration {
	@Autowired(required=true)
    private DBConfiguration config;

    public DBConfiguration getConfig() {
        return config;
    }

    public void setConfig(DBConfiguration config) {
        this.config = config;
    }
    
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
    	final DriverManagerDataSource ds = new DriverManagerDataSource();
    	ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
    	ds.setUrl("${db.url}");
    	return ds;
    }
    
    
}
