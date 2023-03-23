package com.joshuahawatta.moneyzilla.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Database {
    @Value("${DB.url}")
    private String url;

    @Value("${DB.user}")
    private String name;

    @Value("${DB.password}")
    private String password;

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
