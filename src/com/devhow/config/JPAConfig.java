package com.devhow.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan("com.devhow.service")
@EnableJpaRepositories(basePackages={"com.devhow.repo"})
public class JPAConfig {

	@Autowired
	private Environment env;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSrc = new DriverManagerDataSource();
		dataSrc.setDriverClassName("org.postgresql.Driver");
		dataSrc.setPassword("apppwd");
		dataSrc.setUrl("jdbc:postgresql://localhost:5432/shopdb");
		dataSrc.setUsername("appuser");
		return dataSrc;
	}

	private Properties getHibernateProperties() {
		 Properties prop = new Properties();
         prop.put("hibernate.format_sql", "true");
         prop.put("hibernate.show_sql", "true");
         prop.put("hibernate.dialect", 
             "org.hibernate.dialect.PostgreSQLDialect");
         prop.put("hibernate.hbm2ddl.auto", "update");
         prop.put("hibernate.connection.autocommit", "true");
         
         return prop;

	}
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] {"com.devhow.springdata", "com.devhow.model"});
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setShowSql(true);
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setJpaProperties(getHibernateProperties());
		return factoryBean;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(
			LocalContainerEntityManagerFactoryBean emf) {
		EntityManagerFactory factory = entityManagerFactoryBean().getObject();
		return new JpaTransactionManager(factory);
	}
}
