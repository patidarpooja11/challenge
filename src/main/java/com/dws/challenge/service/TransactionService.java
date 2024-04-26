package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.repository.AccountsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

  private final AccountsRepository accountsRepository;
  private final NotificationService notificationService;



  public TransactionService(AccountsRepository accountsRepository, NotificationService notificationService) {
    this.accountsRepository = accountsRepository;
      this.notificationService = notificationService;

  }

  public void transferAmount(String fromAccountId, String toAccountId, BigDecimal amount) {

    Account fromAccount = accountsRepository.findById(fromAccountId).
            orElseThrow(() -> new IllegalArgumentException("From Account not found"));

    Account toAccount = accountsRepository.findById(toAccountId).
            orElseThrow(() -> new IllegalArgumentException("To Account not found"));

    if (fromAccount.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient balance");
    }

    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
    toAccount.setBalance(toAccount.getBalance().add(amount));
    accountsRepository.save(fromAccount);
    accountsRepository.save(toAccount);


    notificationService.notifyAboutTransfer(new Account(fromAccountId,amount),"Amount Transferred to account" + toAccountId);
    notificationService.notifyAboutTransfer(new Account(toAccountId,amount),"Amount received from account" + fromAccountId);
  }

}
