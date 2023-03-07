package com.whitebox.accounts.repositories;

import com.whitebox.accounts.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<BankAccount, String > {
    Optional<BankAccount> findByAccountHolderId(String accountHolderId);
    List<BankAccount> findAll();
}
