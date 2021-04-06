package com.hotel.config;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.hotel.dao.AccountDao;
import com.hotel.messaging.JmsMessageListener;
import com.hotel.messaging.JmsMessageSender;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import bitronix.tm.resource.jms.PoolingConnectionFactory;

@Configuration
@ComponentScan("com.hotel") // searches in that package, and any nested packages for Spring annotations
@EnableJms
@EnableTransactionManagement
@Component("config")
@EnableAspectJAutoProxy
public class AppConfig {
	
	// JMS Broker Url
	public static final String BROKER_URL = "tcp://localhost:61616";

	// JMS Destinations
	public static final String ACCOUNT_QUEUE = "ACCOUNT_QUEUE";

	// DataSource info
	public static final String DATASOURCE_SCHEMA = System.getenv("HOTEL_DB_SCHEMA");
	public static final String DATASOURCE_URL = "jdbc:postgresql://" + System.getenv("HOTEL_DB_URL") + ":5432/"
			+ System.getenv("HOTEL_DB_NAME") + "?currentSchema="+ DATASOURCE_SCHEMA;
	public static final String DATASOURCE_DRIVERNAME = "org.postgresql.xa.PGXADataSource";
	public static final String DATASOURCE_USERNAME = System.getenv("HOTEL_DB_USERNAME");
	public static final String DATASOURCE_PASSWORD = System.getenv("HOTEL_DB_PASSWORD");
	

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		System.out.println(DATASOURCE_URL);
		PoolingDataSource dataSource = new PoolingDataSource();
		dataSource.setClassName(DATASOURCE_DRIVERNAME);
		dataSource.setUniqueName("PostGresDB");
		dataSource.setMaxPoolSize(10);
		dataSource.setAllowLocalTransactions(true);
		dataSource.getDriverProperties().put("Url", DATASOURCE_URL);
		dataSource.getDriverProperties().put("user", DATASOURCE_USERNAME);
		dataSource.getDriverProperties().put("password", DATASOURCE_PASSWORD);
		//dataSource.getDriverProperties().put("schema", DATASOURCE_SCHEMA);
		dataSource.init();
		return dataSource;

	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public ConnectionFactory bitronixConnectionFactory() {
		PoolingConnectionFactory connectionFactory = new PoolingConnectionFactory();
		connectionFactory.setClassName("org.apache.activemq.ActiveMQXAConnectionFactory");
		connectionFactory.setUniqueName("activemq");
		connectionFactory.setMaxPoolSize(10);
		connectionFactory.setAllowLocalTransactions(true);
		Properties props = new Properties();
		props.put("brokerURL", BROKER_URL);
		connectionFactory.setDriverProperties(props);
		return connectionFactory;
	}
	
	@Bean
	public Queue accountQueue() {
		return new ActiveMQQueue(ACCOUNT_QUEUE);
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory bitronixConnectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(bitronixConnectionFactory);
		jmsTemplate.setReceiveTimeout(10000);
		return jmsTemplate;
	}

	@Bean
	public DefaultMessageListenerContainer jmsContainer(ConnectionFactory connectionFactory,
			JmsMessageListener messageListener) {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setDestinationName(ACCOUNT_QUEUE);
		container.setPubSubDomain(true);

		container.setMessageListener(messageListener);
		return container;
	}
	
	// this will allow us to consume messages from the queue, using Spring for help
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory container = new DefaultJmsListenerContainerFactory();
		container.setConnectionFactory(connectionFactory);
		// container.setDestinationName(EXAMPLE_QUEUE);
		// container.setDestinationName(EXAMPLE_TOPIC);
		// container.setPubSubDomain(true);
		// container.setMessageListener(messageListener);
		return container;
	}

	@Bean
	public bitronix.tm.Configuration btmConfig() {
		bitronix.tm.Configuration config = TransactionManagerServices.getConfiguration();
		config.setDisableJmx(true);
		config.setServerId("spring-btm");
		return config;
	}

	@Bean(destroyMethod = "shutdown")
	@DependsOn("btmConfig")
	public TransactionManager primaryTransactionManager() {
		return TransactionManagerServices.getTransactionManager();
	}

	@Bean
	public JtaTransactionManager jtaTransactionManager(TransactionManager primaryTransactionManager) {
		JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
		jtaTransactionManager.setTransactionManager(primaryTransactionManager);
		return jtaTransactionManager;
	}

	@Bean
	@Scope("singleton")
	public Scanner consoleInScanner() {
		return new Scanner(System.in);
	}

	@Bean
	public Logger log() {
		return Logger.getRootLogger();
	}
	
	@Autowired
	private JmsMessageSender messageSender;
	
	@Autowired
	private AccountDao accountDao;
	
}
