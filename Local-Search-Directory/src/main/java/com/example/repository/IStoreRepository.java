package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Store;

@Repository
public interface IStoreRepository extends JpaRepository<Store, Long> {

}
