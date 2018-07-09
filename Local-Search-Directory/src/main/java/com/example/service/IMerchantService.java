package com.example.service;

import java.util.List;
import com.example.dto.MerchantDto;
import com.example.model.Merchant;

public interface IMerchantService {

	String create(MerchantDto merchantDto);

	List<Merchant> viewAll();

	Merchant viewOne(Long id);

}
