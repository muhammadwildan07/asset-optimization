package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.Asset;
import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.entity.Employee;
import com.finalproject.assetmanagement.entity.Transaction;
import com.finalproject.assetmanagement.model.request.TransactionRequest;
import com.finalproject.assetmanagement.model.response.AssetResponse;
import com.finalproject.assetmanagement.model.response.BranchResponse;
import com.finalproject.assetmanagement.model.response.EmployeeResponse;
import com.finalproject.assetmanagement.model.response.TransactionResponse;
import com.finalproject.assetmanagement.repository.AssetRepository;
import com.finalproject.assetmanagement.repository.EmployeeRepository;
import com.finalproject.assetmanagement.repository.TransactionRepository;
import com.finalproject.assetmanagement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse createNewTransaction(TransactionRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())//validation employee
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        Asset asset = assetRepository.findById(request.getAssetId())//validation asset
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found"));

        long requestLoanAmount = request.getLoanAmount();// Validation currentQuantity
        if (requestLoanAmount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current quantity must be greater than 0");
        }
        // Deduct currentQuantity from the asset
        if (asset.getQuantity() < requestLoanAmount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough quantity of the asset available");
        }

        Transaction transaction = Transaction.builder()
                .inboundItem(request.getInboundItem())
                .outboundItem(request.getOutboundItem())
                .loanAmount(requestLoanAmount)
                .status("approval process")
                .employee(employee)
                .asset(asset)
                .build();

        transactionRepository.save(transaction);

        return transactionResponse(transaction, employee, asset, transaction.getAsset().getBranch());
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transactions = transactionRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "asset not found"));
                return transactionResponse(transactions, transactions.getEmployee() , transactions.getAsset(), transactions.getAsset().getBranch());
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public List<TransactionResponse> getAllTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
            List<TransactionResponse> transactionResponses = transactions.stream()
                    .map(transaction -> transactionResponse(transaction, transaction.getEmployee(),transaction.getAsset(), transaction.getAsset().getBranch()))
                    .collect(Collectors.toList());
            return transactionResponses;
    }
    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse updateTransaction(TransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "asset not found"));
        if (Objects.nonNull(transaction)) {
            transaction.setId(request.getId());
            transaction.setInboundItem(request.getInboundItem());
            transaction.setOutboundItem(request.getOutboundItem());
            transaction.setLoanAmount(request.getLoanAmount());
            transactionRepository.save(transaction);
            return transactionResponse(transaction, transaction.getEmployee(), transaction.getAsset(), transaction.getAsset().getBranch());
        }
        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deleteTransactionById(String id) {
        transactionRepository.deleteById(id);
    }

    private static TransactionResponse transactionResponse(Transaction transaction, Employee employee, Asset asset, Branch branch) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .inboundItem(transaction.getInboundItem())
                .outboundItem(transaction.getOutboundItem())
                .loanAmount(transaction.getLoanAmount())
                .status(transaction.getStatus())
                .employees(EmployeeResponse.builder()
                        .id(employee.getId())
                        .username(employee.getUsername())
                        .password(employee.getPassword())
                        .email(employee.getEmail())
                        .mobilePhone(employee.getMobilePhone())
                        .build())
                .assets(AssetResponse.builder()
                        .id(asset.getId())
                        .assetCode(asset.getAssetCode())
                        .name(asset.getName())
                        .description(asset.getDescription())
                        .quantity(asset.getQuantity())
                        .branch(BranchResponse.builder()
                                .id(branch.getId())
                                .branchCode(branch.getBranchCode())
                                .branchName(branch.getBranchName())
                                .address(branch.getAddress())
                                .mobilePhone(branch.getMobilePhone())
                                .build())
                        .build())
                .build();
    }
}
