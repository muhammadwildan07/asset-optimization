package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.model.request.CreateAssetRequest;
import com.finalproject.assetmanagement.model.request.TransactionRequest;
import com.finalproject.assetmanagement.model.request.UpdateAssetRequest;
import com.finalproject.assetmanagement.model.response.AssetResponse;
import com.finalproject.assetmanagement.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createNewTransaction(TransactionRequest request);
    TransactionResponse getTransactionById(String id);
    List<TransactionResponse> getAllTransaction();
    TransactionResponse updateTransaction(TransactionRequest request);
    void deleteTransactionById(String id);



}
