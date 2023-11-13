package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.Asset;
import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.model.request.CreateAssetRequest;
import com.finalproject.assetmanagement.model.request.UpdateAssetRequest;
import com.finalproject.assetmanagement.model.response.AssetResponse;
import com.finalproject.assetmanagement.model.response.BranchResponse;
import com.finalproject.assetmanagement.repository.AssetRepository;
import com.finalproject.assetmanagement.repository.BranchRepository;
import com.finalproject.assetmanagement.service.AssetService;
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
public class AssetServiceImplementation implements AssetService {

    private final BranchRepository branchRepository;
    private final AssetRepository assetRepository;
    @Transactional(rollbackOn = Exception.class)
    @Override
    public AssetResponse createNewAsset(CreateAssetRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "branch not found"));
        Asset asset = Asset.builder()
                .id(request.getId())
                .name(request.getName())
                .assetCode(request.getAssetCode())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .branch(branch)
                .build();
        assetRepository.save(asset);
        return assetResponse(branch, asset);
    }

    @Override
    public AssetResponse getAssetById(String id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "asset not found"));
        return assetResponse(asset.getBranch(), asset);
    }

    @Override
    public List<AssetResponse> getAllAsset() {
        List<Asset> assets = assetRepository.findAll();
        List<AssetResponse> assetResponses = assets.stream()
                .map(asset -> assetResponse(asset.getBranch(), asset))
                .collect(Collectors.toList());
        return assetResponses;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public AssetResponse updateAsset(UpdateAssetRequest request) {
        Asset asset = assetRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "asset not found"));
       if (Objects.nonNull(asset)) {
           asset.setId(request.getId());
           asset.setName(request.getName());
           asset.setAssetCode(request.getAssetCode());
           asset.setDescription(request.getDescription());
           asset.setQuantity(request.getQuantity());
           assetRepository.save(asset);
           return assetResponse(asset.getBranch(), asset);
       }
        return null;
    }


    @Override
    public void deleteAssetById(String id) {
        assetRepository.deleteById(id);
    }

    private static AssetResponse assetResponse(Branch branch, Asset asset) {
        return AssetResponse.builder()
                .id(asset.getId())
                .assetCode(asset.getAssetCode())
                .name(asset.getName())
                .description(asset.getDescription())
                .quantity(asset.getQuantity())
                .branch(BranchResponse.builder()
                        .id(branch.getId())
                        .branchCode(branch.getBranchCode())
                        .branchName(branch.getBranchName())
                        .mobilePhone(branch.getMobilePhone())
                        .address(branch.getAddress())
                        .build())
                .build();
    }
}
