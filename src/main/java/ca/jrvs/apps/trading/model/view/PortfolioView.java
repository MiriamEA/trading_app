package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;

import java.util.LinkedList;
import java.util.List;

public class PortfolioView {

    private List<SecurityRow> securityRows;

    public PortfolioView() {
        securityRows = new LinkedList<>();
    }

    public List<SecurityRow> getSecurityRows() {
        return securityRows;
    }

    public void setSecurityRows(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }

    public void addSecurityRow(Position position, Quote quote, String ticker) {
        SecurityRow securityRow = new SecurityRow(position, quote, ticker);
        securityRows.add(securityRow);
    }

    class SecurityRow {
        private Position position;
        private Quote quote;
        private String ticker;

        public SecurityRow() {
        }

        public SecurityRow(Position position, Quote quote, String ticker) {
            this.position = position;
            this.quote = quote;
            this.ticker = ticker;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public Quote getQuote() {
            return quote;
        }

        public void setQuote(Quote quote) {
            this.quote = quote;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }
    }
}
