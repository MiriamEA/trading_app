package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"symbol", "companyName", "primaryExchange", "sector", "calculationPrice", "open", "openTime", "close", "closeTime", "high", "low", "latestPrice", "latestSource", "latestTime", "latestUpdate", "latestVolume", "iexRealtimePrice", "iexRealtimeSize", "iexLastUpdated", "delayedPrice", "delayedPriceTime", "extendedPrice", "extendedChange", "extendedChangePercent", "extendedPriceTime", "previousClose", "change", "changePercent", "iexMarketPercent", "iexVolume", "avgTotalVolume", "iexBidPrice", "iexBidSize", "iexAskPrice", "iexAskSize", "marketCap", "peRatio", "week52High", "week52Low", "ytdChange"})
public class IexQuote implements Entity<String> {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("primaryExchange")
    private String primaryExchange;
    @JsonProperty("sector")
    private Object sector;
    @JsonProperty("calculationPrice")
    private String calculationPrice;
    @JsonProperty("open")
    private Object open;
    @JsonProperty("openTime")
    private Object openTime;
    @JsonProperty("close")
    private Object close;
    @JsonProperty("closeTime")
    private Object closeTime;
    @JsonProperty("high")
    private Object high;
    @JsonProperty("low")
    private Object low;
    @JsonProperty("latestPrice")
    private String latestPrice;
    @JsonProperty("latestSource")
    private String latestSource;
    @JsonProperty("latestTime")
    private String latestTime;
    @JsonProperty("latestUpdate")
    private String latestUpdate;
    @JsonProperty("latestVolume")
    private Object latestVolume;
    @JsonProperty("iexRealtimePrice")
    private String iexRealtimePrice;
    @JsonProperty("iexRealtimeSize")
    private String iexRealtimeSize;
    @JsonProperty("iexLastUpdated")
    private String iexLastUpdated;
    @JsonProperty("delayedPrice")
    private Object delayedPrice;
    @JsonProperty("delayedPriceTime")
    private Object delayedPriceTime;
    @JsonProperty("extendedPrice")
    private Object extendedPrice;
    @JsonProperty("extendedChange")
    private Object extendedChange;
    @JsonProperty("extendedChangePercent")
    private Object extendedChangePercent;
    @JsonProperty("extendedPriceTime")
    private Object extendedPriceTime;
    @JsonProperty("previousClose")
    private String previousClose;
    @JsonProperty("change")
    private String change;
    @JsonProperty("changePercent")
    private String changePercent;
    @JsonProperty("iexMarketPercent")
    private String iexMarketPercent;
    @JsonProperty("iexVolume")
    private String iexVolume;
    @JsonProperty("avgTotalVolume")
    private String avgTotalVolume;
    @JsonProperty("iexBidPrice")
    private String iexBidPrice;
    @JsonProperty("iexBidSize")
    private String iexBidSize;
    @JsonProperty("iexAskPrice")
    private String iexAskPrice;
    @JsonProperty("iexAskSize")
    private String iexAskSize;
    @JsonProperty("marketCap")
    private String marketCap;
    @JsonProperty("peRatio")
    private String peRatio;
    @JsonProperty("week52High")
    private String week52High;
    @JsonProperty("week52Low")
    private String week52Low;
    @JsonProperty("ytdChange")
    private String ytdChange;

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonProperty("primaryExchange")
    public String getPrimaryExchange() {
        return primaryExchange;
    }

    @JsonProperty("primaryExchange")
    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    @JsonProperty("sector")
    public Object getSector() {
        return sector;
    }

    @JsonProperty("sector")
    public void setSector(Object sector) {
        this.sector = sector;
    }

    @JsonProperty("calculationPrice")
    public String getCalculationPrice() {
        return calculationPrice;
    }

    @JsonProperty("calculationPrice")
    public void setCalculationPrice(String calculationPrice) {
        this.calculationPrice = calculationPrice;
    }

    @JsonProperty("open")
    public Object getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(Object open) {
        this.open = open;
    }

    @JsonProperty("openTime")
    public Object getOpenTime() {
        return openTime;
    }

    @JsonProperty("openTime")
    public void setOpenTime(Object openTime) {
        this.openTime = openTime;
    }

    @JsonProperty("close")
    public Object getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(Object close) {
        this.close = close;
    }

    @JsonProperty("closeTime")
    public Object getCloseTime() {
        return closeTime;
    }

    @JsonProperty("closeTime")
    public void setCloseTime(Object closeTime) {
        this.closeTime = closeTime;
    }

    @JsonProperty("high")
    public Object getHigh() {
        return high;
    }

    @JsonProperty("high")
    public void setHigh(Object high) {
        this.high = high;
    }

    @JsonProperty("low")
    public Object getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(Object low) {
        this.low = low;
    }

    @JsonProperty("latestPrice")
    public String getLatestPrice() {
        return latestPrice;
    }

    @JsonProperty("latestPrice")
    public void setLatestPrice(String latestPrice) {
        this.latestPrice = latestPrice;
    }

    @JsonProperty("latestSource")
    public String getLatestSource() {
        return latestSource;
    }

    @JsonProperty("latestSource")
    public void setLatestSource(String latestSource) {
        this.latestSource = latestSource;
    }

    @JsonProperty("latestTime")
    public String getLatestTime() {
        return latestTime;
    }

    @JsonProperty("latestTime")
    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    @JsonProperty("latestUpdate")
    public String getLatestUpdate() {
        return latestUpdate;
    }

    @JsonProperty("latestUpdate")
    public void setLatestUpdate(String latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    @JsonProperty("latestVolume")
    public Object getLatestVolume() {
        return latestVolume;
    }

    @JsonProperty("latestVolume")
    public void setLatestVolume(Object latestVolume) {
        this.latestVolume = latestVolume;
    }

    @JsonProperty("iexRealtimePrice")
    public String getIexRealtimePrice() {
        return iexRealtimePrice;
    }

    @JsonProperty("iexRealtimePrice")
    public void setIexRealtimePrice(String iexRealtimePrice) {
        this.iexRealtimePrice = iexRealtimePrice;
    }

    @JsonProperty("iexRealtimeSize")
    public String getIexRealtimeSize() {
        return iexRealtimeSize;
    }

    @JsonProperty("iexRealtimeSize")
    public void setIexRealtimeSize(String iexRealtimeSize) {
        this.iexRealtimeSize = iexRealtimeSize;
    }

    @JsonProperty("iexLastUpdated")
    public String getIexLastUpdated() {
        return iexLastUpdated;
    }

    @JsonProperty("iexLastUpdated")
    public void setIexLastUpdated(String iexLastUpdated) {
        this.iexLastUpdated = iexLastUpdated;
    }

    @JsonProperty("delayedPrice")
    public Object getDelayedPrice() {
        return delayedPrice;
    }

    @JsonProperty("delayedPrice")
    public void setDelayedPrice(Object delayedPrice) {
        this.delayedPrice = delayedPrice;
    }

    @JsonProperty("delayedPriceTime")
    public Object getDelayedPriceTime() {
        return delayedPriceTime;
    }

    @JsonProperty("delayedPriceTime")
    public void setDelayedPriceTime(Object delayedPriceTime) {
        this.delayedPriceTime = delayedPriceTime;
    }

    @JsonProperty("extendedPrice")
    public Object getExtendedPrice() {
        return extendedPrice;
    }

    @JsonProperty("extendedPrice")
    public void setExtendedPrice(Object extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    @JsonProperty("extendedChange")
    public Object getExtendedChange() {
        return extendedChange;
    }

    @JsonProperty("extendedChange")
    public void setExtendedChange(Object extendedChange) {
        this.extendedChange = extendedChange;
    }

    @JsonProperty("extendedChangePercent")
    public Object getExtendedChangePercent() {
        return extendedChangePercent;
    }

    @JsonProperty("extendedChangePercent")
    public void setExtendedChangePercent(Object extendedChangePercent) {
        this.extendedChangePercent = extendedChangePercent;
    }

    @JsonProperty("extendedPriceTime")
    public Object getExtendedPriceTime() {
        return extendedPriceTime;
    }

    @JsonProperty("extendedPriceTime")
    public void setExtendedPriceTime(Object extendedPriceTime) {
        this.extendedPriceTime = extendedPriceTime;
    }

    @JsonProperty("previousClose")
    public String getPreviousClose() {
        return previousClose;
    }

    @JsonProperty("previousClose")
    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }

    @JsonProperty("change")
    public String getChange() {
        return change;
    }

    @JsonProperty("change")
    public void setChange(String change) {
        this.change = change;
    }

    @JsonProperty("changePercent")
    public String getChangePercent() {
        return changePercent;
    }

    @JsonProperty("changePercent")
    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    @JsonProperty("iexMarketPercent")
    public String getIexMarketPercent() {
        return iexMarketPercent;
    }

    @JsonProperty("iexMarketPercent")
    public void setIexMarketPercent(String iexMarketPercent) {
        this.iexMarketPercent = iexMarketPercent;
    }

    @JsonProperty("iexVolume")
    public String getIexVolume() {
        return iexVolume;
    }

    @JsonProperty("iexVolume")
    public void setIexVolume(String iexVolume) {
        this.iexVolume = iexVolume;
    }

    @JsonProperty("avgTotalVolume")
    public String getAvgTotalVolume() {
        return avgTotalVolume;
    }

    @JsonProperty("avgTotalVolume")
    public void setAvgTotalVolume(String avgTotalVolume) {
        this.avgTotalVolume = avgTotalVolume;
    }

    @JsonProperty("iexBidPrice")
    public String getIexBidPrice() {
        return iexBidPrice;
    }

    @JsonProperty("iexBidPrice")
    public void setIexBidPrice(String iexBidPrice) {
        this.iexBidPrice = iexBidPrice;
    }

    @JsonProperty("iexBidSize")
    public String getIexBidSize() {
        return iexBidSize;
    }

    @JsonProperty("iexBidSize")
    public void setIexBidSize(String iexBidSize) {
        this.iexBidSize = iexBidSize;
    }

    @JsonProperty("iexAskPrice")
    public String getIexAskPrice() {
        return iexAskPrice;
    }

    @JsonProperty("iexAskPrice")
    public void setIexAskPrice(String iexAskPrice) {
        this.iexAskPrice = iexAskPrice;
    }

    @JsonProperty("iexAskSize")
    public String getIexAskSize() {
        return iexAskSize;
    }

    @JsonProperty("iexAskSize")
    public void setIexAskSize(String iexAskSize) {
        this.iexAskSize = iexAskSize;
    }

    @JsonProperty("marketCap")
    public String getMarketCap() {
        return marketCap;
    }

    @JsonProperty("marketCap")
    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    @JsonProperty("peRatio")
    public String getPeRatio() {
        return peRatio;
    }

    @JsonProperty("peRatio")
    public void setPeRatio(String peRatio) {
        this.peRatio = peRatio;
    }

    @JsonProperty("week52High")
    public String getWeek52High() {
        return week52High;
    }

    @JsonProperty("week52High")
    public void setWeek52High(String week52High) {
        this.week52High = week52High;
    }

    @JsonProperty("week52Low")
    public String getWeek52Low() {
        return week52Low;
    }

    @JsonProperty("week52Low")
    public void setWeek52Low(String week52Low) {
        this.week52Low = week52Low;
    }

    @JsonProperty("ytdChange")
    public String getYtdChange() {
        return ytdChange;
    }

    @JsonProperty("ytdChange")
    public void setYtdChange(String ytdChange) {
        this.ytdChange = ytdChange;
    }

    @Override
    public String getId() {
        return symbol;
    }

    @Override
    public void setId(String id) {
        this.symbol = id;
    }
}