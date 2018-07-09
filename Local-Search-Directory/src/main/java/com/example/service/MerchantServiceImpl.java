package com.example.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.assembler.MerchantAssembler;
import com.example.controller.PasswordGenerator;
import com.example.dto.MerchantDto;
import com.example.model.Merchant;
import com.example.model.User;
import com.example.repository.IMerchantRepository;

@Service
public class MerchantServiceImpl implements IMerchantService {

	@Autowired
	private IMerchantRepository merchantRepository;

	@Autowired
	private MerchantAssembler merchantAssembler;

	@Autowired
	private IUserService userService;

	@Override
	public String create(@Valid @RequestBody MerchantDto merchantDto) {
		Merchant merchant = merchantAssembler.createMerchantEntity(merchantDto);
		merchant.setCreated_at(new java.util.Date());
		User user = new User();
		user.setUsername(merchant.getMail_id());
		PasswordGenerator pass = new PasswordGenerator();
		String password = pass.generatePassword();
		user.setPassword(password);
		user.setMerchant(merchant);
		userService.save(user);
		merchantRepository.save(merchant);
		return password;
	}

	@Override
	public List<Merchant> viewAll() {
		List<Merchant> merchant = merchantRepository.findAll();
		return merchant;
	}

	@Override
	public Merchant viewOne(Long id) {
		Merchant merchant = merchantRepository.getOne(id);
		return merchant;
	}

}
