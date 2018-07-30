package com.example.assembler;

import java.util.ArrayList;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.PaymentMethodDto;
//import com.example.exception.ResourceNotFoundException;
import com.example.model.PaymentMethod;
//import com.example.model.Store;
//import com.example.repository.IStoreRepository;

@Component
public class PaymentMethodAssembler {
	/*@Autowired
	private IStoreRepository storeRepository;*/
	
	public List<PaymentMethod> createPaymentEntity(List<PaymentMethodDto> paymentMethodDtoList) {
		List<PaymentMethod> paymentMethods = new ArrayList<>();
		paymentMethodDtoList.forEach(paymentMethodDto -> {
			PaymentMethod paymentMethod = new PaymentMethod();
			paymentMethod.setName(paymentMethodDto.getName());
			paymentMethod.setCode(paymentMethodDto.getCode());
			/*Store store = storeRepository.findById(paymentMethodDto.getStoreId()).orElseThrow(
					() -> new ResourceNotFoundException("Store", "store id", paymentMethodDto.getStoreId()));
			List<Store> stores = new ArrayList<>();
			stores.add(store);
			paymentMethod.setStores(stores);*/

			paymentMethods.add(paymentMethod);
		});
		return paymentMethods;
	}
	
	public PaymentMethod createPaymentEntity(PaymentMethodDto paymentMethodDto) {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setName(paymentMethodDto.getName());
		paymentMethod.setCode(paymentMethodDto.getCode());
		/*Store store = storeRepository.findById(paymentMethodDto.getStoreId()).orElseThrow(
				() -> new ResourceNotFoundException("Store", "store id", paymentMethodDto.getStoreId()));
		List<Store> stores = new ArrayList<>();
		stores.add(store);
		paymentMethod.setStores(stores);*/
		return paymentMethod;
	}
}
