package com.ggingmin.loans.controller;

import com.ggingmin.loans.model.Customer;
import com.ggingmin.loans.model.Loan;
import com.ggingmin.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoansRepository loansRepository;

    @PostMapping("/myLoans")
    public List<Loan> getLoansDetails(@RequestBody Customer customer) {
        List<Loan> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }
}
