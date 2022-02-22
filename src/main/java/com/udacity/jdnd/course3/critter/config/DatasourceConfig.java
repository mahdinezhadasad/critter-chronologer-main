package com.udacity.jdnd.course3.critter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/critter?serverTimezone=UTC";

    @Bean
//    @Primary taking away primary to prevent this config from always been loaded
//    (especially needed to prevent config from being loaded when running tests)
    @ConfigurationProperties(prefix = "critter.datasource")
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
       return dataSourceBuilder.url(DB_URL).build();
    }
}
