package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.entity.Manager;
import com.finalproject.assetmanagement.model.request.ManagerRequest;
import com.finalproject.assetmanagement.model.response.ManagerResponse;
import com.finalproject.assetmanagement.repository.ManagerRepository;
import com.finalproject.assetmanagement.repository.TransactionRepository;
import com.finalproject.assetmanagement.service.implementation.ManagerServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ManagerServiceImplementationTest {
    private final ManagerRepository managerRepository = mock(ManagerRepository.class);
    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);

    private final ManagerService managerService = new ManagerServiceImplementation(managerRepository, transactionRepository);

    @Test
    void createNewManagerTest() {
        ManagerRequest dummyManagerRequest = new ManagerRequest();
        dummyManagerRequest.setId("123");
        dummyManagerRequest.setUsername("Suryani");

        // Membuat objek Manager berdasarkan dummyManagerRequest
        Manager dummyManager = new Manager();
        dummyManager.setId("123");
        dummyManager.setUsername("Suryani");

        when(managerRepository.save(any(Manager.class))).thenReturn(dummyManager);

        ManagerResponse createManager = managerService.createNewManager(dummyManagerRequest);

        verify(managerRepository, times(1)).save(any(Manager.class));

        assertEquals("123", createManager.getId());
        assertEquals("Suryani", createManager.getUsername());
    }


    @Test
    void getManagerById() {
        String managerId = "1";
        Manager dummyManager = new Manager();
        dummyManager.setId(managerId);
        dummyManager.setUsername("Farhan");

        when(managerRepository.findById(managerId)).thenReturn(Optional.of(dummyManager));

        Manager retriviedManager = managerService.getManagerById(managerId);

        verify(managerRepository, times(1)).findById(managerId);

        assertEquals(managerId, retriviedManager.getId());
        assertEquals("Farhan", retriviedManager.getUsername());
    }

//    @Test
//    void getAllManager() {
//        List<Manager> dummyManager = new ArrayList<>();
//        dummyManager.add(new Manager("1", "Suryani", "suryani007", "suryanips@gmail.com", "0813337"));
//        dummyManager.add(new Manager("2", "Farhan", "farhan123", "farhan@gmail.com", "08134345"));
//        dummyManager.add(new Manager("3", "Wildan", "wildan456", "wildan@gmail.com", "08987544"));
//
//        when(managerRepository.findAll()).thenReturn(dummyManager);
//
//        List<ManagerResponse> retriviedManager = managerService.getAllManager();
//
//        verify(managerRepository, times(1)).findAll();
//
//        assertEquals(dummyManager.size(), retriviedManager.size());
//
//        for (int i = 0; i < dummyManager.size(); i++) {
//            assertEquals(dummyManager.get(i).getId(), retriviedManager.get(i).getId());
//            assertEquals(dummyManager.get(i).getUsername(), retriviedManager.get(i).getUsername());
//        }
//    }

//    @Test
//    void updateManagerTest() {
//        String managerId = "1";
//        ManagerRequest dummyManagerToUpdate = new Manager(managerId, "suryani (Update)", "suryani007", "suryanips@gmail.com", "083787737");
//
//        when(managerRepository.findById(managerId)).thenReturn(Optional.of(new Manager(managerId, "suryani (Update)", "suryani007", "suryanips@gmail.com", "083787737")));
//
//        when(managerRepository.save(dummyManagerToUpdate)).thenReturn(dummyManagerToUpdate);
//
//        Manager updateManager = managerService.updateManager(dummyManagerToUpdate);
//
//        verify(managerRepository, times(1)).findById(managerId);
//        verify(managerRepository, times(1)).save(dummyManagerToUpdate);
//
//        assertEquals(dummyManagerToUpdate.getUsername(), updateManager.getUsername());
//   }

    @Test
    void deleteManagerByIdTest() {
        String managerId = "1";
        managerService.deleteManagerById(managerId);
        verify(managerRepository, times(1)).deleteById(managerId);
    }
}