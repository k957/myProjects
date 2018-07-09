package com.example.service;

import java.util.List;

import com.example.dto.PaymentMethodDto;
import com.example.model.PaymentMethod;

public interface IPaymentMethodService {
	List<PaymentMethod> viewAll();

	PaymentMethod viewOne(Long id);

	PaymentMethod update(PaymentMethodDto paymentMethodDto);

	void delete(List<PaymentMethod> productList);

	PaymentMethod create(PaymentMethodDto paymentMethodDto);
}
