package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assembler.StoreAssembler;
import com.example.dto.StoreDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Store;
import com.example.repository.IStoreRepository;

@Service
public class StoreServiceImpl implements IStoreService {
	@Autowired
	private StoreAssembler storeAssembler;

	@Autowired
	private IStoreRepository storeRepository;

	@Override
	public Store create(StoreDto storeDto) {
		Store store = storeAssembler.createStoreEntity(storeDto);
		storeRepository.save(store);
		return store;
	}

	@Override
	public List<Store> viewAll() {

		return storeRepository.findAll();
	}

	@Override
	public Store viewOne(Long id) {

		return storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store", "Store_id", id));
	}

	@Override
	public Store update(Store StoreDto) {
		return null;
	}

	@Override
	public void delete(List<Store> StoreList) {

	}

}
