package com.ggingmin.accounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ggingmin.accounts.config.AccountsServiceConfig;
import com.ggingmin.accounts.model.*;
import com.ggingmin.accounts.repository.AccountsRepository;
import com.ggingmin.accounts.service.client.CardsFeignClient;
import com.ggingmin.accounts.service.client.LoansFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    AccountsServiceConfig accountsConfig;

    @Autowired
    LoansFeignClient loansFeignClient;

    @Autowired
    CardsFeignClient cardsFeignClient;

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer) {

        Account account = accountsRepository.findByCustomerId(customer.getCustomerId());
        if (account != null) {
            return account;
        } else {
            return null;
        }
    }

    @GetMapping("/accounts/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(
                accountsConfig.getMsg(),
                accountsConfig.getBuildVersion(),
                accountsConfig.getMailDetails(),
                accountsConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @PostMapping("/myCustomerDetails")
//    @CircuitBreaker(name="detailsForCustomerSupportApp", fallbackMethod="myCustomerDetailsFallBack")
    @Retry(name="retryForCustomerDetails")
    public CustomerDetails myCustomerDetails(
            @RequestHeader("ggingbank-correlation-id")
            String correlationId,
            @RequestBody Customer customer) {

        Account account = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loan> loans = loansFeignClient.getLoanDetails(correlationId, customer);
        List<Card> cards = cardsFeignClient.getCardDetails(correlationId, customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccount(account);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);

        return customerDetails;
    }

    private CustomerDetails myCustomerDetailsFallBack(
            @RequestHeader("ggingbank-correlation-id")
            String correlationId,
            Customer customer,
            Throwable t) {
        Account account = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loan> loans = loansFeignClient.getLoanDetails(correlationId, customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccount(account);
        customerDetails.setLoans(loans);
        return customerDetails;
    }
}
