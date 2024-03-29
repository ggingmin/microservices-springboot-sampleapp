package com.ggingmin.loans.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ggingmin.loans.config.LoansServiceConfig;
import com.ggingmin.loans.model.Customer;
import com.ggingmin.loans.model.Loan;
import com.ggingmin.loans.model.Properties;
import com.ggingmin.loans.repository.LoansRepository;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoansController.class);

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    LoansServiceConfig loansConfig;

    @PostMapping("/myLoans")
    @Timed(value="getLoansDetails.time", description="Time taken to return Loans Details")
    public List<Loan> getLoansDetails(@RequestBody Customer customer) {
        LOGGER.info("getLoansDetails() method started");
        List<Loan> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        LOGGER.info("getLoansDetails() method ended");
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }

    @GetMapping("/loans/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(
                loansConfig.getMsg(),
                loansConfig.getBuildVersion(),
                loansConfig.getMailDetails(),
                loansConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
}
