package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MarketDataDao {

    private final String BATCH_QUOTE_URI;
    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager connectionManager;

    public MarketDataDao(HttpClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        String token = System.getenv("IEX_TOKEN");
        StringBuilder sb = new StringBuilder("https://cloud.iexapis.com/stable/stock/market/batch?token=");
        sb.append(token);
        sb.append("&types=quote&symbols=");
        BATCH_QUOTE_URI = sb.toString();
    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        List<IexQuote> quotes = findIexQuoteByTicker(Arrays.asList(ticker));
        return quotes.get(0);
    }

    public List<IexQuote> findIexQuoteByTicker(List<String> tickers) {
        String uri = getURI(tickers);
        logger.info("Get URI :" + uri);
        String response = executeHttpGet(uri);

        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.length() != tickers.size()) {
            throw new IllegalArgumentException("Invalid ticker");
        }
        return fromJsonObjectToQuoteList(jsonObject);
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
        List<IexQuote> quotes = new LinkedList<>();
        for (String key : jsonObject.keySet()) {
            JSONObject object = jsonObject.getJSONObject(key);
            Object o = object.get("quote");
            try {
                IexQuote iexQuote = toObjectFromJson(object.get("quote").toString(), IexQuote.class);
                quotes.add(iexQuote);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return quotes;
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(connectionManager)
                //prevent connectionManager shutdown when calling httpClient.close()
                .setConnectionManagerShared(true).build();
    }

    private <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T) mapper.readValue(json, clazz);
    }
}