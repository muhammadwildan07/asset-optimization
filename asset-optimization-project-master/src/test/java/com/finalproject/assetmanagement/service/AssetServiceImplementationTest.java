package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.entity.Asset;
import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.model.request.AssetRequest;
import com.finalproject.assetmanagement.model.request.CreateAssetRequest;
import com.finalproject.assetmanagement.model.request.UpdateAssetRequest;
import com.finalproject.assetmanagement.model.response.AssetResponse;
import com.finalproject.assetmanagement.repository.AssetRepository;
import com.finalproject.assetmanagement.repository.BranchRepository;
import com.finalproject.assetmanagement.service.implementation.AssetServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AssetServiceImplementationTest {
    private final AssetRepository assetRepository = mock(AssetRepository.class);
    private final BranchRepository branchRepository = mock(BranchRepository.class);
    private final AssetService assetService = new AssetServiceImplementation(branchRepository, assetRepository);

//    @Test
//    void createNewAsset() {
//        CreateAssetRequest dummyAsset = new CreateAssetRequest();
//        dummyAsset.setId("123");
//        dummyAsset.setName("Printer");
//
//        when(assetRepository.save(any(Asset.class))).thenReturn(dummyAsset);
//
//        AssetResponse createAsset = assetService.createNewAsset(dummyAsset);
//
//        verify(assetRepository, times(1)).save(dummyAsset);
//
//        assertEquals("123", createAsset.getId());
//        assertEquals("Printer", createAsset.getName());
//    }

//    @Test
//    void getAssetById() {
//        Asset dummyAsset1 = new Asset();
//        dummyAsset1.setId("1");
//        dummyAsset1.setName("Printer");
//        dummyAsset1.setAssetCode("234123");
//        dummyAsset1.setQuantity(5L);
//        dummyAsset1.setDescription("asasa");
//
//        when(assetRepository.findById("1")).thenReturn(Optional.<Asset>of(dummyAsset1));
//
//        AssetResponse retrievedAsset = assetService.getAssetById("0");
//
//        verify(assetRepository, times(1)).findById("1");
//
//        assertEquals("1", retrievedAsset.getId());
//        assertEquals("Printer", retrievedAsset.getName());
//    }

    @Test
    void getAllAsset() {

        Branch branch = new Branch();
        branch.setBranchName("branch1");

        List<Asset> dummyAsset = new ArrayList<>();
        dummyAsset.add(new Asset("1", "123", "Printer", "", 5L, branch));
        dummyAsset.add(new Asset("2", "456", "Laptop", "", 5L, branch));
        dummyAsset.add(new Asset("3", "789", "Scanner", "", 5L, branch));

        when(assetRepository.findAll()).thenReturn(dummyAsset);

        List<AssetResponse> retriviedAsset = assetService.getAllAsset();

        verify(assetRepository, times(1)).findAll();

        assertEquals(dummyAsset.size(), retriviedAsset.size());

        for (int i = 0; i < dummyAsset.size(); i++){
            assertEquals(dummyAsset.get(i).getId(), retriviedAsset.get(i).getId());
            assertEquals(dummyAsset.get(i).getName(), retriviedAsset.get(i).getName());
        }
    }

//    @Test
//    void updateAsset() {
//        String assetId = "1";
//        UpdateAssetRequest dummyAssetToUpdate = new UpdateAssetRequest(assetId, "123 (Update)", "Printer", "", 6L);
//
//        when(assetRepository.findById(assetId)).thenReturn(Optional.of(new Asset(assetId, "123 (Update)", "Printer", "", 5L)));
//
//        when(assetRepository.save(dummyAssetToUpdate)).thenReturn(dummyAssetToUpdate);
//
//        AssetResponse updateAsset = assetService.updateAsset(dummyAssetToUpdate);
//
//        verify(assetRepository, times(1)).findById(assetId);
//        verify(assetRepository, times(1)).save(dummyAssetToUpdate);
//
//        assertEquals(dummyAssetToUpdate.getName(), updateAsset.getName());
//    }

    @Test
    void deleteAssetById() {
        String assetId = "1";
        assetService.deleteAssetById(assetId);
        verify(assetRepository, times(1)).deleteById(assetId);
    }
}