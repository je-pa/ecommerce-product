package com.ecommerceproduct.domain.store.repository;

import com.ecommerceproduct.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {

}
