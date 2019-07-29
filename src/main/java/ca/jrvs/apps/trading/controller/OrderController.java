package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "Submit a market order", notes = "Submit a market order")
    @PostMapping("/marketOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public SecurityOrder executeMarketOrder(MarketOrderDto order) {
        try {
            return orderService.executeMarketOrder(order);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}