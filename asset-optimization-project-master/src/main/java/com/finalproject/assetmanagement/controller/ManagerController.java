package com.finalproject.assetmanagement.controller;

import com.finalproject.assetmanagement.entity.Manager;
import com.finalproject.assetmanagement.model.request.ApprovedTransactionRequest;
import com.finalproject.assetmanagement.model.request.ManagerRequest;
import com.finalproject.assetmanagement.model.request.TransactionRequest;
import com.finalproject.assetmanagement.model.response.ManagerResponse;
import com.finalproject.assetmanagement.model.response.CommonResponse;
import com.finalproject.assetmanagement.model.response.MonitoringTransactionResponse;
import com.finalproject.assetmanagement.model.response.TransactionResponse;
import com.finalproject.assetmanagement.repository.TransactionRepository;
import com.finalproject.assetmanagement.service.ManagerService;
import com.finalproject.assetmanagement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createNewManager(@RequestBody ManagerRequest request) {
        ManagerResponse managerResponse = managerService.createNewManager(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.<ManagerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new manager")
                        .data(managerResponse)
                        .build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable String id) {
        Manager manager = managerService.getManagerById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<ManagerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get manager by id")
                        .data(ManagerResponse.builder()
                                .id(manager.getId())
                                .username(manager.getUsername())
                                .email(manager.getEmail())
                                .password(manager.getPassword())
                                .mobilePhone(manager.getMobilePhone())
                                .build())
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllManager() {
        List<ManagerResponse> managers = managerService.getAllManager();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all managers")
                        .data(managers)
                        .build());
    }


    @PutMapping
    public ResponseEntity<?> updateManager(@RequestBody ManagerRequest request) {
        ManagerResponse managerResponse = managerService.updateManager(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<ManagerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully updated manager")
                        .data(managerResponse)
                        .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable String id) {
        managerService.deleteManagerById(id);
        Manager manager = new Manager();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully deleted manager")
                        .build());
    }
    @GetMapping(path = "/getByIdTransaction/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse manager = transactionService.getTransactionById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<TransactionResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Mr.Manager Successfully get transaction by id")
                        .data(manager)
                        .build());
    }

    @GetMapping(path = "/getAllTransaction")
    public ResponseEntity<?> getAllTransaction() {
        List<TransactionResponse> managers = transactionService.getAllTransaction();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully monitoring all transaction")
                        .data(managers)
                        .build());
    }

    @PutMapping(path = "/updateTransaction")
    public ResponseEntity<?> updateTransactionByManager(@RequestBody ApprovedTransactionRequest request) {
        TransactionResponse monitoringResponses = managerService.updateTransaction(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<TransactionResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully approved status transaction")
                        .data(monitoringResponses)
                        .build());
    }

    @DeleteMapping(path = "deleteTransaction/{id}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable String id) {
        managerService.deleteTransactionById(id);
        Manager manager = new Manager();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully deleted transaction")
                        .build());
    }
}
