package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"trader", "account"})
public class TraderAccountView {

    @JsonProperty("account")
    private Account account;
    @JsonProperty("trader")
    private Trader trader;

    public TraderAccountView() {
    }

    public TraderAccountView(Account account, Trader trader) {
        this.account = account;
        this.trader = trader;
    }

    @JsonProperty("account")
    public Account getAccount() {
        return account;
    }

    @JsonProperty("account")
    public void setAccount(Account account) {
        this.account = account;
    }

    @JsonProperty("trader")
    public Trader getTrader() {
        return trader;
    }

    @JsonProperty("trader")
    public void setTrader(Trader trader) {
        this.trader = trader;
    }
}
