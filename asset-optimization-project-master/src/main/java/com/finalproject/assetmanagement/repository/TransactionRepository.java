package com.finalproject.assetmanagement.repository;

import com.finalproject.assetmanagement.entity.Asset;
import com.finalproject.assetmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
