package com.example.service;

import java.util.List;

import com.example.dto.StoreDto;
import com.example.model.Store;

public interface IStoreService {
	Store create(StoreDto StoreDto);

	List<Store> viewAll();

	Store viewOne(Long id);

	Store update(Store StoreDto);

	void delete(List<Store> StoreList);
}
