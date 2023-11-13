package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.entity.Manager;
import com.finalproject.assetmanagement.model.request.ApprovedTransactionRequest;
import com.finalproject.assetmanagement.model.request.ManagerRequest;
import com.finalproject.assetmanagement.model.response.ManagerResponse;
import com.finalproject.assetmanagement.model.response.MonitoringTransactionResponse;
import com.finalproject.assetmanagement.model.response.TransactionResponse;

import java.util.List;

public interface ManagerService {
    ManagerResponse createNewManager(ManagerRequest request);
    Manager getManagerById(String id);
    List<ManagerResponse> getAllManager();// parameter diisi jika menggunakan seaching

    ManagerResponse updateManager(ManagerRequest request);

    void deleteManagerById(String id);

    TransactionResponse getTransactionById(String id);
    List<TransactionResponse> getAllTransaction();

    TransactionResponse updateTransaction(ApprovedTransactionRequest request);

    void deleteTransactionById(String id);
}
