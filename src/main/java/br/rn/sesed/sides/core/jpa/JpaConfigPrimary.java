package br.rn.sesed.sides.core.jpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(
    basePackages = "br.rn.sesed.sides.domain.desaparecidos.repository",
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager"
)
public class JpaConfigPrimary {


    @Value("${sides.jpa.datasource.sides}")
    private String sidesDatasource;

	@Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
	public DataSource primaryDataSource() {
		
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		//DataSource dataSource = dataSourceLookup.getDataSource("java:/sidesDS");
        DataSource dataSource = dataSourceLookup.getDataSource(sidesDatasource);
		return dataSource;
	}
	
	@Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
        @Qualifier("primaryDataSource")  DataSource primaryDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(primaryDataSource);
        em.setPackagesToScan("br.rn.sesed.sides.domain.desaparecidos.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

	@SuppressWarnings("null")
	@Bean
    @Primary
    public JpaTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        return new JpaTransactionManager(primaryEntityManagerFactory.getObject());
    }
}
