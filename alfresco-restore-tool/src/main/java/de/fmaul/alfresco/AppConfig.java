package de.fmaul.alfresco;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("/restore.properties")
public class AppConfig {
	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
	   return new DriverManagerDataSource(
			   env.getProperty("restoredb.url"), 
			   env.getProperty("restoredb.username"), 
			   env.getProperty("restoredb.password"));
	}
	
}
