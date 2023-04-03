package br.rn.sesed.sides.core.jpa;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class JpaConfig {

	
	@Bean
	public DataSource dataSource() {
		
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		DataSource dataSource = dataSourceLookup.getDataSource("java:/SidesDS");
		return dataSource;
	}
	
	
}
