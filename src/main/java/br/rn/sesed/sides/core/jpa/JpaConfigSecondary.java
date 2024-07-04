package br.rn.sesed.sides.core.jpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(
    basePackages = "br.rn.sesed.sides.domain.rotafx.repository",
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager"
)
public class JpaConfigSecondary {

    @Value("${sides.jpa.datasource.rotafx}")
    private String rotafxDatasource;


	@Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource secondaryDataSource() {
		
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
//		DataSource dataSource = dataSourceLookup.getDataSource("java:/rotafxDS");
        DataSource dataSource = dataSourceLookup.getDataSource(rotafxDatasource);
		return dataSource;
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
        @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondaryDataSource);
        em.setPackagesToScan("br.rn.sesed.sides.domain.rotafx.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

	@SuppressWarnings("null")
	@Bean
    public JpaTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory) {
        return new JpaTransactionManager(secondaryEntityManagerFactory.getObject());
    }
}
