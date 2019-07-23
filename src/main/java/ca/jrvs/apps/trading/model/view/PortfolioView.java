package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortfolioView {

    @JsonProperty("securityRows")
    private List<SecurityRow> securityRows;

    public PortfolioView() {
        securityRows = new LinkedList<>();
    }

    @JsonProperty("securityRows")
    public List<SecurityRow> getSecurityRows() {
        return securityRows;
    }

    @JsonProperty("securityRows")
    public void setSecurityRows(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }

    public void addSecurityRow(Position position, Quote quote, String ticker) {
        SecurityRow securityRow = new SecurityRow(position, quote, ticker);
        securityRows.add(securityRow);
    }

    @JsonPropertyOrder({"ticker", "position", "quote"})
    class SecurityRow {

        @JsonProperty("ticker")
        private Position position;
        @JsonProperty("positon")
        private Quote quote;
        @JsonProperty("quote")
        private String ticker;

        public SecurityRow() {
        }

        public SecurityRow(Position position, Quote quote, String ticker) {
            this.position = position;
            this.quote = quote;
            this.ticker = ticker;
        }

        @JsonProperty("positon")
        public Position getPosition() {
            return position;
        }

        @JsonProperty("positon")
        public void setPosition(Position position) {
            this.position = position;
        }

        @JsonProperty("quote")
        public Quote getQuote() {
            return quote;
        }

        @JsonProperty("quote")
        public void setQuote(Quote quote) {
            this.quote = quote;
        }

        @JsonProperty("ticker")
        public String getTicker() {
            return ticker;
        }

        @JsonProperty("ticker")
        public void setTicker(String ticker) {
            this.ticker = ticker;
        }
    }
}
