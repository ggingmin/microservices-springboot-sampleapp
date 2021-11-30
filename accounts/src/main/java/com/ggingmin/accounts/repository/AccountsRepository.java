package com.ggingmin.accounts.repository;

import com.ggingmin.accounts.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@org.springframework.stereotype.Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {

    Account findByCustomerId(int customerId);
}
