package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "traderId", "amount"})
public class Account implements Entity<Integer> {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("traderId")
    private Integer traderId;
    @JsonProperty("amount")
    private Double amount;

    public Account(int traderId) {
        this.traderId = traderId;
        amount = 0.0;
    }

    public Account() {
    }

    @JsonProperty("id")
    @Override
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("traderId")
    public Integer getTraderId() {
        return traderId;
    }

    @JsonProperty("traderId")
    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
