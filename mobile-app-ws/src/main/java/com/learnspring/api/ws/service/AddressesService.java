package com.learnspring.api.ws.service;

import java.util.List;

import com.learnspring.api.ws.shared.dto.AddressDTO;

public interface AddressesService {
	List<AddressDTO> getAddresses(String userId);

	AddressDTO getAddress(String addressId);
}
