package com.learnspring.api.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnspring.api.ws.io.entity.AddressEntity;
import com.learnspring.api.ws.io.entity.UserEntity;
import com.learnspring.api.ws.io.repository.AddressRepository;
import com.learnspring.api.ws.io.repository.UserRepository;
import com.learnspring.api.ws.service.AddressesService;
import com.learnspring.api.ws.shared.dto.AddressDTO;

@Service
public class AddressServiceImpl implements AddressesService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository; 
	
	@Override
	public List<AddressDTO> getAddresses(String userId) {
		List<AddressDTO> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		for (AddressEntity addressEntity : addresses)
		{
			returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
		}
		
		return returnValue;
	}

	@Override
	public AddressDTO getAddress(String addressId) {
		AddressDTO returnValue = null;
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		if(addressEntity != null)
		{
			returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
		}
		return returnValue;
	}

}
