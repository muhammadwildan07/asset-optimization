package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.entity.Asset;
import com.finalproject.assetmanagement.model.request.AssetRequest;
import com.finalproject.assetmanagement.model.request.CreateAssetRequest;
import com.finalproject.assetmanagement.model.request.UpdateAssetRequest;
import com.finalproject.assetmanagement.model.response.AssetResponse;

import java.util.List;

public interface AssetService {
    AssetResponse createNewAsset(CreateAssetRequest request);
    AssetResponse getAssetById(String id);
    List<AssetResponse> getAllAsset();
    AssetResponse updateAsset(UpdateAssetRequest request);
    void deleteAssetById(String id);



}
