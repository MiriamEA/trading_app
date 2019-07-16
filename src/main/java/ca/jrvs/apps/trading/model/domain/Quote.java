package ca.jrvs.apps.trading.model.domain;

public class Quote implements Entity<String> {

    private double askPrice;
    private long askSize;
    private double bidPrice;
    private long bidSize;
    private String id;
    private double lastPrice;
    private String ticker;

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public long getAskSize() {
        return askSize;
    }

    public void setAskSize(long askSize) {
        this.askSize = askSize;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public long getBidSize() {
        return bidSize;
    }

    public void setBidSize(long bidSize) {
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
    }

    public String getId() {
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }
}
