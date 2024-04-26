package com.dws.challenge.web;

import com.dws.challenge.domain.TransferRequest;
import com.dws.challenge.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TransactionController {
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/transfer")
  public ResponseEntity transferAmount(@RequestBody @Valid TransferRequest transferRequest) {
    transactionService.transferAmount(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount());

    return ResponseEntity.ok("Transfer successful");
  }

}
