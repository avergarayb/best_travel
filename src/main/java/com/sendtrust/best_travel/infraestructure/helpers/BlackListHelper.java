package com.sendtrust.best_travel.infraestructure.helpers;

import com.sendtrust.best_travel.api.controllers.error_handler.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {

    public void isInBlackListCustomer(String customerId) {
        if (customerId.equals("WALA771012HCRGR054")) {
            throw new ForbiddenCustomerException();
        }
    }
}
