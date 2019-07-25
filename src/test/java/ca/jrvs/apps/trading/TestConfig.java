package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.util.StringUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.apps.trading.dao", "ca.jrvs.apps.trading.service"})
public class TestConfig {
    private static final String JDBC_URL = "jdbc:postgresql://localhost/jrvstrading_test";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "password";

    @Bean
    public DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USER);
        dataSource.setPassword(JDBC_PASSWORD);
        return dataSource;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(50);
        manager.setDefaultMaxPerRoute(50);
        return manager;
    }

    @Bean
    public MarketDataConfig marketDataConfig() {
        String iex_host = "https://cloud.iexapis.com/stable/";
        if (StringUtil.isEmpty(Arrays.asList(System.getenv("IEX_PUB_TOKEN")))) {
            throw new IllegalArgumentException("ENV:IEX_PUB_TOKEN or property:iex_host is not set");
        }
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        marketDataConfig.setHost(iex_host);
        return marketDataConfig;
    }
}