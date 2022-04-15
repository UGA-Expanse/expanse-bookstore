package rocks.j5.uga.expanse.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import javax.sql.DataSource;

@Configuration
public class JDBCDataSourceConfig extends AbstractHttpSessionApplicationInitializer {

    @Value("${spring.datasource.url}")
    private String mysqlDbUrl;

    @Value("${spring.datasource.username}")
    private String mysqlDbUser;

    @Value("${spring.datasource.password}")
    private String mysqlDbPwd;

    @Value("${spring.datasource.driver-class-name}")
    private String mysqlDriverClassName;

    @Value("${spring.datasource.connection-test-query}")
    private String connectionTestQuery;

    @Bean
    public DataSource mySqlApplicationDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(mysqlDbUrl);
        config.setUsername(mysqlDbUser);
        config.setPassword(mysqlDbPwd);
        config.setConnectionTestQuery(connectionTestQuery);
        config.setDriverClassName(mysqlDriverClassName);

        return new HikariDataSource(config);
    }

//    @Bean
//    public EmbeddedDatabase dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("org/springframework/session/jdbc/schema-h2.sql").build();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
}
