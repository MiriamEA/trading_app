package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionUtil.class);

    public static ResponseStatusException getResponseStatusException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            logger.debug("Invalid input", e);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } else if (e instanceof ResourceNotFoundException) {
            logger.debug("Not found", e);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } else {
            logger.error("Internal error", e);
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error. Please contact admin");
        }
    }
}
