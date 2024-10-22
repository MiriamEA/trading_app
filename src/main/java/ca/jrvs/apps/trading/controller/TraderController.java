package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.service.RegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/trader")
public class TraderController {

    private RegisterService registerService;
    private FundTransferService fundTransferService;

    @Autowired
    public TraderController(RegisterService registerService, FundTransferService fundTransferService) {
        this.registerService = registerService;
        this.fundTransferService = fundTransferService;
    }

    @ApiOperation(value = "Delete a trader", notes = "Delete a trader IFF its account amount is 0 and there are not open positions associated to that trader. Also deletes the associated account and security orders.")
    @DeleteMapping("/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(Integer traderId) {
        try {
            registerService.deleteTraderById(traderId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Create a trader and an account", notes = "TraderId and AccountId are automatically generated by the database, and they should be identical")
    @PostMapping("/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public TraderAccountView createNewTrader(@PathVariable String firstname, @PathVariable String lastname, @PathVariable Date dob, @PathVariable String country, @PathVariable String email) {
        Trader trader = new Trader();
        trader.setFirstName(firstname);
        trader.setEmail(email);
        trader.setCountry(country);
        trader.setLastName(lastname);
        trader.setDob(dob);
        return createNewTrader(trader);
    }

    @ApiOperation(value = "Create a trader and an account with DTO", notes = "TraderId and AccountId are automatically generated by the database, and they should be identical")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TraderAccountView createNewTrader(@RequestBody Trader trader) {
        try {
            return registerService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Deposit a fund", notes = "Deposit a fund to a given account. Deposit amount must be greater than 0")
    @PutMapping("/deposit/accountId/{accountId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public Account depositeByAccountId(Integer accountId, Double amount) {
        try {
            return fundTransferService.deposit(accountId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Withdraw a fund", notes = "Withdraw a fund from a given account. Withdraw amount must be greater than 0 and not exceed account amount.")
    @PutMapping("/withdraw/accountId/{accountId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public Account withdrawByAccountId(Integer accountId, Double amount) {
        try {
            return fundTransferService.withdraw(accountId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
