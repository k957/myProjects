package com.example.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.StoreDto;
import com.example.model.Merchant;
import com.example.model.PaymentMethod;
import com.example.model.Store;
import com.example.repository.IMerchantRepository;

@Component
public class StoreAssembler {
	@Autowired
	private IMerchantRepository merchantRepository;
	
	public List<Store> createStoreEntity(List<StoreDto> storeDtoList) {
		List<Store> storeList = new ArrayList<>();
		storeDtoList.forEach(storeDto -> {
			Store store = new Store();
			Merchant merchant = merchantRepository.getOne(storeDto.getMerchant_id());

			List<PaymentMethod> paymentMethods = (storeDto.getPaymentMethodId());
			store.setPaymentMethods(paymentMethods);
			System.out.println(paymentMethods);
			store.setMerchant(merchant);
			store.setAddress(storeDto.getAddress());
			store.setDescription(storeDto.getDescription());
			store.setName(storeDto.getName());
			store.setPhone(storeDto.getPhone());
			store.setPostal_code(storeDto.getPostal_code());
			store.setLatitude(storeDto.getLatitude());
			store.setLongitude(storeDto.getLongitude());
			store.setOpening_hours(storeDto.getOpening_hours());

			storeList.add(store);
		});
		return storeList;
	}
	
	public Store createStoreEntity(StoreDto storeDto) {
		Store store = new Store();
		Merchant merchant = merchantRepository.getOne(storeDto.getMerchant_id());

		List<PaymentMethod> paymentMethods = (storeDto.getPaymentMethodId());
		store.setPaymentMethods(paymentMethods);
		System.out.println(paymentMethods);
		store.setMerchant(merchant);
		store.setAddress(storeDto.getAddress());
		store.setDescription(storeDto.getDescription());
		store.setName(storeDto.getName());
		store.setPhone(storeDto.getPhone());
		store.setPostal_code(storeDto.getPostal_code());
		store.setLatitude(storeDto.getLatitude());
		store.setLongitude(storeDto.getLongitude());
		store.setOpening_hours(storeDto.getOpening_hours());
		return store;
	}

}
