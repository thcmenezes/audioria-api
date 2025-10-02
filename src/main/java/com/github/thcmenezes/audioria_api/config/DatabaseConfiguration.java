package com.github.thcmenezes.audioria_api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setUrl(url);
//        driverManagerDataSource.setUsername(username);
//        driverManagerDataSource.setPassword(password);
//        driverManagerDataSource.setDriverClassName(driver);
//
//        return  driverManagerDataSource;
//    }

    @Bean
    public DataSource dataSource() {

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);

        hikariConfig.setPoolName("audioria-db-pool");
        hikariConfig.setMaximumPoolSize(10); // Máximo de conexões liberadas
        hikariConfig.setMinimumIdle(1); // Tamanho inicial do pool
        hikariConfig.setMaxLifetime(600000); // Tempo de vida da conexão

        return new HikariDataSource(hikariConfig);
    }
}
