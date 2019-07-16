package ca.jrvs.apps.trading.model.domain;

public class Position implements Entity<Integer> {
    private int accountId;
    private int position;
    private String ticker;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public Integer getId() {
        return accountId;
    }

    @Override
    public void setId(Integer id) {
        accountId = id;
    }
}
