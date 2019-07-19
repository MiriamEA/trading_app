package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"ticker", "lastPrice", "bidPrice", "bidSize", "askPrice", "askSize", "id"})

public class Quote implements Entity<String> {

    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("lastPrice")
    private Double lastPrice;
    @JsonProperty("bidPrice")
    private Double bidPrice;
    @JsonProperty("bidSize")
    private Integer bidSize;
    @JsonProperty("askPrice")
    private Double askPrice;
    @JsonProperty("askSize")
    private Integer askSize;
    @JsonProperty("id")
    private String id;

    @JsonProperty("ticker")
    public String getTicker() {
        return ticker;
    }

    @JsonProperty("ticker")
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @JsonProperty("lastPrice")
    public Double getLastPrice() {
        return lastPrice;
    }

    @JsonProperty("lastPrice")
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @JsonProperty("bidPrice")
    public Double getBidPrice() {
        return bidPrice;
    }

    @JsonProperty("bidPrice")
    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    @JsonProperty("bidSize")
    public Integer getBidSize() {
        return bidSize;
    }

    @JsonProperty("bidSize")
    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    @JsonProperty("askPrice")
    public Double getAskPrice() {
        return askPrice;
    }

    @JsonProperty("askPrice")
    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    @JsonProperty("askSize")
    public Integer getAskSize() {
        return askSize;
    }

    @JsonProperty("askSize")
    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }
}