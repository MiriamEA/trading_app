package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"ticker", "lastPrice", "bidPrice", "bidSize", "askPrice", "askSize", "id"})

public class Quote implements Entity<String> {

    @JsonProperty("ticker")
    public String ticker;
    @JsonProperty("lastPrice")
    public Double lastPrice;
    @JsonProperty("bidPrice")
    public Double bidPrice;
    @JsonProperty("bidSize")
    public Integer bidSize;
    @JsonProperty("askPrice")
    public Double askPrice;
    @JsonProperty("askSize")
    public Integer askSize;
    @JsonProperty("id")
    public String id;

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public int getAskSize() {
        return askSize;
    }

    public void setAskSize(int askSize) {
        this.askSize = askSize;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getBidSize() {
        return bidSize;
    }

    public void setBidSize(int bidSize) {
        this.bidSize = bidSize;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
        setId(ticker);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
