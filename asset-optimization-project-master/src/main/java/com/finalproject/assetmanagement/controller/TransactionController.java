package com.finalproject.assetmanagement.controller;

import com.finalproject.assetmanagement.entity.Transaction;
import com.finalproject.assetmanagement.model.request.TransactionRequest;
import com.finalproject.assetmanagement.model.response.CommonResponse;
import com.finalproject.assetmanagement.model.response.TransactionResponse;
import com.finalproject.assetmanagement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    @PostMapping
    public ResponseEntity<?> createNewTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.createNewTransaction(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.<TransactionResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new transaction")
                        .data(transactionResponse)
                        .build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<TransactionResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get transaction by id")
                        .data(transactionResponse)
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        List<TransactionResponse> transasactions = transactionService.getAllTransaction();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all transactions")
                        .data(transasactions)
                        .build());
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactions = transactionService.updateTransaction(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<TransactionResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update transaction")
                        .data(transactions)
                        .build());
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransactionById(id);
        Transaction transaction = new Transaction();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message("Successfully delete transaction")
                        .data(String.valueOf(transaction))
                        .build());
    }
}
