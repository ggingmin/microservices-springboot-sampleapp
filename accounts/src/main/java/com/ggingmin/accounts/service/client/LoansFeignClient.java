package com.ggingmin.accounts.service.client;

import com.ggingmin.accounts.model.Customer;
import com.ggingmin.accounts.model.Loan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "myLoans", consumes = "application/json")
    List<Loan> getLoanDetails(
            @RequestHeader("ggingbank-correlation-id")
            String correlationId,
            @RequestBody Customer customer);
}