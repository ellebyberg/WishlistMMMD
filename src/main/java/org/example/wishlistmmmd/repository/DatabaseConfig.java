package org.example.wishlistmmmd.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_user;
    @Value("${spring.datasource.password}")
    private String db_password;

@Bean
    public ConnectionManager connectionManager() {
    return ConnectionManager.getInstance(db_url, db_user, db_password);
}
//I vores WishRepository har vi en ConnectionManager-afhængighed/dependency specificeret i
//konstruktøren. Når Spring skaber en instans af WishRepository, så gennemsøger den alle
//beans for at finde en af typen ConnectionManager. Vi har her i DatabaseConfig annoteret metoden
//ConnectionManager connectionManager med @Bean og den er public, så Spring vil anvende denne
//til at oprette ConnectionManager-instansen og injicere den automatisk i WishRepository.




}
