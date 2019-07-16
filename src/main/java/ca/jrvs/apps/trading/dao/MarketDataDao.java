package ca.jrvs.apps.trading.dao;

import org.apache.http.conn.HttpClientConnectionManager;

public class MarketDataDao {

    private HttpClientConnectionManager connectionManager;

    public MarketDataDao(HttpClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}