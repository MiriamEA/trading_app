package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.Account;

public class TraderAccountView {

    private Account account;
    private Trader trader;

    public TraderAccountView() {
    }

    public TraderAccountView(Account account, Trader trader) {
        this.account = account;
        this.trader = trader;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }
}
