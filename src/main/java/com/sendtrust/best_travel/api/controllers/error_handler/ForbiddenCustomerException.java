package com.sendtrust.best_travel.api.controllers.error_handler;

public class ForbiddenCustomerException extends RuntimeException {

    public ForbiddenCustomerException() {
        super("This customer is blocked");
    }
}
