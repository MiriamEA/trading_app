package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequestMapping("/trader")
public class TraderController {

    private RegisterService registerService;
    private FundTransferService fundTransferService;

    @Autowired
    public TraderController(RegisterService registerService, FundTransferService fundTransferService) {
        this.registerService = registerService;
        this.fundTransferService = fundTransferService;
    }

    @DeleteMapping("/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteTrader(Integer traderId) {
        try {
            registerService.deleteTraderById(traderId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PostMapping("/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TraderAccountView createNewTrader(@PathVariable String firstname, @PathVariable String lastname, @PathVariable Date dob, @PathVariable String country, @PathVariable String email) {
        Trader trader = new Trader();
        trader.setFirstName(firstname);
        trader.setEmail(email);
        trader.setCountry(country);
        trader.setLastName(lastname);
        trader.setDob(dob);
        return createNewTrader(trader);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TraderAccountView createNewTrader(Trader trader) {
        try {
            return registerService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping("/deposit/accountId/{accountId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account depositeByAccountId(Integer accountId, Double amount) {
        try {
            return fundTransferService.deposit(accountId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping("/withdraw/accountId/{accountId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account withdrawByAccountId(Integer accountId, Double amount) {
        try {
            return fundTransferService.withdraw(accountId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
