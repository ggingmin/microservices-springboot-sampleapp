package com.ggingmin.loans.repository;

import com.ggingmin.loans.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoansRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByCustomerIdOrderByStartDtDesc(int cutomerId);
}
