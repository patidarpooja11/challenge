package com.dws.challenge;

import com.dws.challenge.domain.Account;
import com.dws.challenge.service.NotificationService;
import com.dws.challenge.service.TransactionService;
import com.dws.challenge.web.TransactionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransferAmount() throws Exception {

        doNothing().when(transactionService).transferAmount(any(String.class), any(String.class), any(BigDecimal.class));
        doNothing().when(notificationService).notifyAboutTransfer(any(Account.class), any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromAccountId\":\"1234\",\"toAccountId\":\"4567\",\"amount\":100}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testNegativeAmount() throws Exception {

        doNothing().when(transactionService).transferAmount(any(String.class), any(String.class), any(BigDecimal.class));
        doNothing().when(notificationService).notifyAboutTransfer(any(Account.class), any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromAccountId\":\"1234\",\"toAccountId\":\"4567\",\"amount\":-100}"))
                .andExpect(status().isBadRequest());
    }

}