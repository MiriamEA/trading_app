package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.util.StringUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

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

/*    @Value("${iex.host}")
    private String iex_host;

    @Bean
    public MarketDataConfig marketDataConfig() {
        if (StringUtil.isEmpty(System.getenv("IEX_PUB_TOKEN")) || StringUtil.isEmpty(iex_host)) {
            throw new IllegalArgumentException("ENV:IEX_PUB_TOKEN or property:iex_host is not set");
        }
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        marketDataConfig.setHost(iex_host);
        return marketDataConfig;
    }*/

    @Bean
    public DataSource dataSource() {

        String jdbcUrl;
        String user;
        String password;

        jdbcUrl = "jdbc:postgresql://localhost/jrvstrading";
        user = "postgres";
        password = "password";

        //jdbcUrl = System.getenv("PSQL_URL");
        //user = System.getenv("PSQL_USER");
        //password = System.getenv("PSQL_PASSWORD");

        logger.error("JDBC:" + jdbcUrl);

        if (StringUtil.isEmpty(Arrays.asList(jdbcUrl, user, password))) {
            throw new IllegalArgumentException("Missing data source config env vars");
        }

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

}
