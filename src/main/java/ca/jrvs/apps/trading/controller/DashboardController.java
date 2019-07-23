package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @ApiOperation(value = "Show portfolio by trader id")
    @GetMapping("/portfolio/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PortfolioView showPortfolio(@PathVariable Integer traderId) {
        try {
            return dashboardService.getProfileViewByTraderId(traderId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Show portfolio by trader id", notes = "Show trader and account details. TraderId and AccountId should be identical")
    @GetMapping("/profile/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TraderAccountView showProfile(@PathVariable Integer traderId) {
        try {
            return dashboardService.getTraderAccount(traderId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
