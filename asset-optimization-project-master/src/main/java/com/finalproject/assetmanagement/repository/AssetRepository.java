package com.finalproject.assetmanagement.repository;

import com.finalproject.assetmanagement.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
}
