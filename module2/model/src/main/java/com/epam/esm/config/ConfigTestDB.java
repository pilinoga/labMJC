package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database.properties")
@Profile("test")
public class ConfigTestDB {
    private final Environment env;
    private static final String DRIVER = "jdbc.driver";
    private static final String URL = "jdbc.url";
    private static final String USER = "jdbc.user";
    private static final String PASSWORD = "jdbc.password";

    @Autowired
    public ConfigTestDB( Environment env) {
        this.env=env;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(DRIVER));
        dataSource.setUrl(env.getProperty(URL));
        dataSource.setUsername(env.getProperty(USER));
        dataSource.setPassword(env.getProperty(PASSWORD));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

}
