package com.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;

@Configuration
@EnableSwagger2
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private ApplicationConfigurationProperties applicationConfigurationProperties;

    @Bean
    public DataSource dataSource() {
        logger.info("DataSource properties: {}", applicationConfigurationProperties);

        DriverManagerDataSource dm = new DriverManagerDataSource(applicationConfigurationProperties.getDbUrl(),
                applicationConfigurationProperties.getDbUser(),
                applicationConfigurationProperties.getDbPassword());
//        dm.setDriverClassName("org.mysql.cj.jdbc.Driver");
        return dm;
    }

    @Bean()
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Virtual cable test service")
                        .build())
                .groupName("public-api")
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.any())
                .build();
    }
}
