package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assembler.PaymentMethodAssembler;
import com.example.dto.PaymentMethodDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.PaymentMethod;
import com.example.repository.IPaymentMethodRepository;

@Service
public class PaymentMethodServiceImpl implements IPaymentMethodService {

	@Autowired
	PaymentMethodAssembler paymentMethodAssembler;

	@Autowired
	private IPaymentMethodRepository paymentMethodRepository;

	@Override
	public List<PaymentMethod> viewAll() {
		List<PaymentMethod> paymentMethod = paymentMethodRepository.findAll();
		return paymentMethod;
	}

	@Override
	public PaymentMethod viewOne(Long id) {
		PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payment Method", "Payment ID", id));
		return paymentMethod;
	}

	@Override
	public PaymentMethod create(PaymentMethodDto paymentMethodDto) {
		PaymentMethod paymentMethod = paymentMethodAssembler.createPaymentEntity(paymentMethodDto);
		paymentMethod.setCreatedAt(new Date());
		paymentMethodRepository.save(paymentMethod);
		return paymentMethod;
	}

	@Override
	public PaymentMethod update(PaymentMethodDto paymentMethodDto) {
		return null;
	}

	@Override
	public void delete(List<PaymentMethod> productList) {
		//created in controller
	}

}
