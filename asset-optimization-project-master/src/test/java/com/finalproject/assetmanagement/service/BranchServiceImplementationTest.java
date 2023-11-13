package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.entity.Asset;
import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.model.request.BranchRequest;
import com.finalproject.assetmanagement.model.request.CreateBranchRequest;
import com.finalproject.assetmanagement.model.response.BranchResponse;
import com.finalproject.assetmanagement.repository.BranchRepository;
import com.finalproject.assetmanagement.service.implementation.BranchServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class BranchServiceImplementationTest {
    private final BranchRepository branchRepository = mock(BranchRepository.class);
    private Branch branch;
    private final BranchService branchService = new BranchServiceImplementation(branchRepository);

//    @Test
//    void createBranch() {
//        CreateBranchRequest dummyBranch = new CreateBranchRequest();
//        dummyBranch.setId("123");
//        dummyBranch.setBranchName("Cabang Medan");
//
//        when(branchRepository.save(any(Branch.class))).thenReturn(dummyBranch);
//
//        BranchResponse createBranch = branchService.createBranch(dummyBranch);
//
//        verify(branchRepository, times(1)).save(createBranch);
//
//        assertEquals("123", createBranch.getId());
//        assertEquals("Cabang Medan", createBranch.getBranchName());
//    }

    @Test
    void getBranchById() {
        String branchId = "1";
        Branch dummyBranch = new Branch();
        dummyBranch.setId(branchId);
        dummyBranch.setBranchName("Cabang Medan");

        when(branchRepository.findById(branchId)).thenReturn(Optional.of(dummyBranch));

        BranchResponse retriviedBranch = branchService.getBranchById(branchId);

        verify(branchRepository, times(1)).findById(branchId);

        assertEquals(branchId, retriviedBranch.getId());
        assertEquals("Cabang Medan", retriviedBranch.getBranchName());
    }

//    @Test
//    void getAllBranch() {
//        List<BranchRequest> dummyBranch = new ArrayList<>();
//        dummyBranch.add(new Branch("1", "123", "Berkah Selalu", "Ragunan", "0813337"));
//        dummyBranch.add(new Branch("2", "456", "Selalu Jaya", "Ragunan", "08134345"));
//        dummyBranch.add(new Branch("3", "789", "Maju Mundur", "Ragunan", "08987544"));
//
//        when(branchRepository.findAll()).thenReturn(dummyBranch);
//
//        List<BranchResponse> retriviedBranch = branchService.getAllBranch();
//
//        verify(branchRepository, times(1)).findAll();
//
//        assertEquals(dummyBranch.size(), retriviedBranch.size());
//
//        for (int i = 0; i < dummyBranch.size(); i++){
//            assertEquals(dummyBranch.get(i).getId(), retriviedBranch.get(i).getId());
//            assertEquals(dummyBranch.get(i).getBranchName(), retriviedBranch.get(i).getBranchName());
//        }
//    }

//    @Test
//    void updateBranch() {
//        String branchId = "1";
//        List<Asset> dummyAsset = new ArrayList<>();
//        String assetId = "1";
//        Branch dummyBranchToUpdate = new Branch(branchId, "123 (Update)", "Store 1", "Ragunan", "083787737", dummyAsset);
//
//        when(branchRepository.findById(branchId)).thenReturn(Optional.of(new Branch(branchId, "123 (Update)", "Store 1", "Ragunan", "083787737", dummyAsset)));
//
//        when(branchRepository.save(dummyBranchToUpdate)).thenReturn(dummyBranchToUpdate);
//
//        Branch updateBranch = branchService.updateBranch(dummyBranchToUpdate);
//
//        verify(branchRepository, times(1)).findById(branchId);
//        verify(branchRepository, times(1)).save(dummyBranchToUpdate);
//
//        assertEquals(dummyBranchToUpdate.getBranchName(), updateBranch.getBranchName());
//    }

    @Test
    void deleteBranchById() {
        String branchId = "1";
        branchService.deleteBranchById(branchId);
        verify(branchRepository, times(1)).deleteById(branchId);
    }
}