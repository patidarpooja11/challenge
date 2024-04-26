package com.dws.challenge.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull
    @NotEmpty
    private String fromAccountId;

    @NotNull
    @NotEmpty
    private String toAccountId;

    @Positive(message = "Amount must be greater than Zero.")
    private BigDecimal amount;

}
