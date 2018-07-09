package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Merchant;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {

}
