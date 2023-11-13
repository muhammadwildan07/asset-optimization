package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.*;
import com.finalproject.assetmanagement.model.request.ApprovedTransactionRequest;
import com.finalproject.assetmanagement.model.request.ManagerRequest;
import com.finalproject.assetmanagement.model.response.*;
import com.finalproject.assetmanagement.repository.ManagerRepository;
import com.finalproject.assetmanagement.repository.TransactionRepository;
import com.finalproject.assetmanagement.service.ManagerService;
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
public class ManagerServiceImplementation implements ManagerService {
private final ManagerRepository managerRepository;
private final TransactionRepository transactionRepository;
    @Transactional(rollbackOn = Exception.class)
    @Override
    public ManagerResponse createNewManager(ManagerRequest request) {
            Manager manager = Manager.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .mobilePhone(request.getMobilePhone())
                    .build();
            managerRepository.save(manager);
        return managerResponse(manager);

    }

    @Override
    public Manager getManagerById(String id) {
        return managerRepository.findById(id).get();
    }

    @Override
    public List<ManagerResponse> getAllManager() {
        List<Manager> managers = managerRepository.findAll();
        List<ManagerResponse> managerResponses = managers.stream()
                .map(manager -> managerResponse(manager)).collect(Collectors.toList());

        return managerResponses;
    }

    @Override
    public ManagerResponse updateManager(ManagerRequest request) {
        Manager manager = getManagerById(request.getId());
        if (Objects.nonNull(manager)){
            manager.setId(request.getId());
            manager.setEmail(request.getEmail());
            manager.setUsername(request.getUsername());
            manager.setPassword(request.getPassword());
            manager.setMobilePhone(request.getMobilePhone());
            managerRepository.save(manager);

            return managerResponse(manager);
        }
        return null;
    }

    @Override
    public void deleteManagerById(String id) {
        managerRepository.deleteById(id);
    }

    private static ManagerResponse managerResponse(Manager manager) {
        return ManagerResponse.builder()
                .id(manager.getId())
                .username(manager.getUsername())
                .password(manager.getPassword())
                .email(manager.getEmail())
                .mobilePhone(manager.getMobilePhone())
                .build();
    }
    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction not found"));
        return transactionResponse(
                transaction,
                transaction.getEmployee(),
                transaction.getAsset(),
                transaction.getAsset().getBranch());

    }
    @Transactional(rollbackOn = Exception.class)
    @Override
    public List<TransactionResponse> getAllTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionResponse> managerResponses = transactions.stream()
                .map(transaction -> transactionResponse(
                        transaction,
                        transaction.getEmployee(),
                        transaction.getAsset(),
                        transaction.getAsset().getBranch()))
                .collect(Collectors.toList());
        return managerResponses;
    }
    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionResponse updateTransaction(ApprovedTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction not found"));
        if (Objects.nonNull(transaction)) {
            transaction.setId(request.getId());
            transaction.setStatus(request.getStatus());
            if ("approved".equalsIgnoreCase(request.getStatus())) {
                Asset asset = transaction.getAsset();
                if (asset != null) {
                    long loanAmount = transaction.getLoanAmount();
                    if (loanAmount <= asset.getQuantity()) {
                        asset.setQuantity(asset.getQuantity() - loanAmount);
                    } else {
                        throw new IllegalArgumentException("Loan amount exceeds available quantity.");
                    }
                }
            }
            transactionRepository.save(transaction);
        }
        return transactionResponse(
                transaction,
                transaction.getEmployee(),
                transaction.getAsset(),
                transaction.getAsset().getBranch());
    }

    @Override
    public void deleteTransactionById(String id) {
        managerRepository.deleteById(id);
    }

    private static MonitoringTransactionResponse managerToTransactionResponse(Transaction transaction, Employee employee, Asset asset, Branch branch) {
        return MonitoringTransactionResponse.builder()
                .transaction(TransactionResponse.builder()
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
                                .quantity(asset.getQuantity() - transaction.getLoanAmount())
                                .branch(BranchResponse.builder()
                                        .id(branch.getId())
                                        .branchCode(branch.getBranchCode())
                                        .branchName(branch.getBranchName())
                                        .address(branch.getAddress())
                                        .mobilePhone(branch.getMobilePhone())
                                        .build())
                                .build())
                        .build())
                .build();
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
