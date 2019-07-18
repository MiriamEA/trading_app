package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(50);
        manager.setDefaultMaxPerRoute(50);
        return manager;
    }

    @Bean
    public MarketDataConfig marketDataConfig() {
        return new MarketDataConfig();
    }

    @Bean
    public DataSource dataSource() {
        String host = "localhost";
        String jdbcUrl = "jdbc:postgresql://" + host + "/jrvstrading";
        String user = "postgres"; //System.getenv("PSQL_USER");
        String password = "password"; //System.getenv("PSQL_PASSWORD");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }


}
