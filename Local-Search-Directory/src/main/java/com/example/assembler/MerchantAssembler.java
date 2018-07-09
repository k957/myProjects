package com.example.assembler;

import org.springframework.stereotype.Component;

import com.example.dto.MerchantDto;
import com.example.model.Merchant;

@Component
public class MerchantAssembler {

	public Merchant createMerchantEntity(MerchantDto merchantDto) {
		Merchant merchant = new Merchant();
		merchant.setName(merchantDto.getName());
		merchant.setDisplay_name(merchantDto.getDisplay_name());
		merchant.setCreated_at(merchantDto.getCreated_at());
		merchant.setMail_id(merchantDto.getMail_id());
		merchant.setMobile_no(merchantDto.getMobile_no());
		return merchant;
	}
}
