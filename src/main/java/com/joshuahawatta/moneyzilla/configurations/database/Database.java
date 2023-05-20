package com.joshuahawatta.moneyzilla.configurations.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/** Values loaded from resources.application.properties file. There, the .env variables are loaded. */
@Configuration
public class Database {
    @Value("${DB.url}")
    private String url;

    @Value("${DB.user}")
    private String name;

    @Value("${DB.password}")
    private String password;

    /** @return a new DataSource that´ll connect to the database. */
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSource = DataSourceBuilder.create();

        dataSource.driverClassName("org.postgresql.Driver");
        dataSource.url(url);
        dataSource.username(name);
        dataSource.password(password);

        return dataSource.build();
    }
}
