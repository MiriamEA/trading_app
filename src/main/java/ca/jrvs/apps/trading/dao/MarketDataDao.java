package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Repository
public class MarketDataDao {

    private final String BATCH_QUOTE_URI;
    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager connectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager connectionManager, MarketDataConfig marketDataConfig) {
        this.connectionManager = connectionManager;
        StringBuilder sb = new StringBuilder(marketDataConfig.getHost());
        sb.append("stock/market/batch?token=");
        sb.append(marketDataConfig.getToken());
        sb.append("&types=quote&symbols=");
        BATCH_QUOTE_URI = sb.toString();
    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        List<IexQuote> quotes = findIexQuoteByTickers(Arrays.asList(ticker));
        if (quotes == null || quotes.size() != 1) {
            throw new DataRetrievalFailureException("Failed to get correct iexQuote");
        }
        return quotes.get(0);
    }

    public List<IexQuote> findIexQuoteByTickers(List<String> tickers) {
        String uri = getURI(tickers);
        logger.info("Get URI :" + uri);
        String response = executeHttpGet(uri);

        JSONObject responseAsJsonObject = new JSONObject(response);
        if (responseAsJsonObject.length() != tickers.size()) {
            throw new IllegalArgumentException("Invalid ticker");
        }
        List<IexQuote> iexQuotes = fromJsonObjectToQuoteList(responseAsJsonObject);
        return iexQuotes;
    }

    private String getURI(List<String> tickers) {
        StringBuilder uri = new StringBuilder(BATCH_QUOTE_URI);
        String tickersCommaSeparated = String.join(",", tickers);
        uri.append(tickersCommaSeparated);
        return uri.toString();
    }

    private String executeHttpGet(String uri) {
        try (CloseableHttpClient client = getHttpClient()) {
            HttpGet httpGet = new HttpGet(uri);
            try (CloseableHttpResponse response = client.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                switch (statusCode) {
                    case 200:
                        String body = EntityUtils.toString(response.getEntity());
                        return body;
                    case 404:
                        throw new ResourceNotFoundException("Not found");
                    default:
                        throw new DataRetrievalFailureException("Unexpected status:" + response.getStatusLine().getStatusCode());
                }
            }
        } catch (IOException e) {
            throw new DataRetrievalFailureException("Http execution error", e);
        }
    }

    private List<IexQuote> fromJsonObjectToQuoteList(JSONObject jsonObject) {
        List<IexQuote> iexQuotes = new LinkedList<>();
        for (String key : jsonObject.keySet()) {
            JSONObject object = jsonObject.getJSONObject(key);
            Object o = object.get("quote");
            try {
                IexQuote iexQuote = JsonUtil.toObjectFromJson(object.get("quote").toString(), IexQuote.class);
                iexQuotes.add(iexQuote);
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert JSON string to IexQuote.", e);
            }
        }
        return iexQuotes;
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(connectionManager).setConnectionManagerShared(true).build();
    }
}