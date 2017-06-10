package com.code405.infrastructure.root.db;

import com.code405.util.UsernameAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by birth on 02.02.2017.
 */
@Configuration
@Import(DataSourceConfig.class)
@EnableTransactionManagement//<!-- activate @Transactional JPA annotation --> <tx:annotation-driven/>
@EnableJpaRepositories(basePackages = {"com.code405.repository"}) // Enable and scan Spring Data repositories.
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider", auditorAwareRef = "auditorProvider")
public class JpaPersistenceConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private Environment environment;


    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS = "hibernate.generate_statistics";
    private static final String PROPERTY_NAME_HIBERNATE_SESSION_FACTORY_NAME = "hibernate.session_factory_name";

    /**
     * <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean" >
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.code405.entity");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaPropertyMap(mapAdditionalProperties());
        // entityManagerFactory.setJpaProperties(additionalProperties());
        return entityManagerFactory;
    }

    private Map<String, ?> mapAdditionalProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_SESSION_FACTORY_NAME, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SESSION_FACTORY_NAME));
        properties.put(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS));
        properties.put("hibernate.enable_lazy_load_no_trans", "true");
        properties.put("current_session_context_class", "thread");
        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return CurrentDateTimeProvider.INSTANCE;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }

//    private Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_SESSION_FACTORY_NAME, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SESSION_FACTORY_NAME));
//        properties.setProperty(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS));
//        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
//        properties.setProperty("current_session_context_class", "thread");
//        return properties;
//    }

    //Automatic Transaction Participation
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
