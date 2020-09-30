package com.learnspring.api.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.asm.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnspring.api.ws.service.AddressesService;
import com.learnspring.api.ws.service.UserService;
import com.learnspring.api.ws.shared.dto.AddressDTO;
import com.learnspring.api.ws.shared.dto.UserDto;
import com.learnspring.api.ws.ui.model.request.UserDetailsRequestModel;
import com.learnspring.api.ws.ui.model.response.AddressesRest;
import com.learnspring.api.ws.ui.model.response.OperationStatusModel;
import com.learnspring.api.ws.ui.model.response.RequestOperationName;
import com.learnspring.api.ws.ui.model.response.RequestOperationStatus;
import com.learnspring.api.ws.ui.model.response.UserResponse;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;;


@RestController
@RequestMapping("users") // http:localhost:8080/users
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired 
	AddressesService addressesService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse getUser(@PathVariable String id)throws Exception  {

		UserResponse returnValue = new UserResponse();
		UserDto userDto = userService.getUserByUserId(id);

//		BeanUtils.copyProperties(userDto, returnValue);
		
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(userDto, UserResponse.class);
		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		

		if (userDetails.getFirstName().isEmpty() || userDetails.getEmail().isEmpty()
				|| userDetails.getLastName().isEmpty() || userDetails.getPassword().isEmpty())
			throw new NullPointerException("the Object is null");
		
		UserResponse returnValue = new UserResponse();
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		returnValue =modelMapper.map(createdUser, UserResponse.class);

		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_ATOM_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserResponse returnValue = new UserResponse();

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = new UserDto();

		userDto =modelMapper.map(userDetails, UserDto.class);
		
//		BeanUtils.copyProperties(userDetails, userDto);

		UserDto createdUser = userService.updateUser(id, userDto);
		returnValue =modelMapper.map(createdUser, UserResponse.class);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}", 
			produces = { MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
	
		userService.deleteUser(id);
			
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping	(produces = { MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	
	public List<UserResponse> getUsers(@RequestParam(value="page", defaultValue="0") int page, 
			@RequestParam(value="limit", defaultValue="10") int limit)
	{
		List<UserResponse> returnValue = new ArrayList<>();
		List<UserDto> users =userService.getUsers(page, limit);
		
		for  (UserDto userDto : users)
		{
//			UserRest userModel = new UserRest();
//			BeanUtils.copyProperties(userDto, userModel);

			ModelMapper modelMapper = new ModelMapper();
			UserResponse userModel =modelMapper.map(userDto, UserResponse.class);
			returnValue.add(userModel);
		}
		return returnValue;
	}
	
	// detail address for specific users 
	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<AddressesRest> getUserAddresses(@PathVariable String id)throws Exception  {

		List<AddressesRest> returnValue = new ArrayList<>();
		List<AddressDTO> addressesDTO= addressesService.getAddresses(id);
		
		if (addressesDTO != null && !addressesDTO.isEmpty()) {
		java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
		
		returnValue = new ModelMapper().map(addressesDTO, listType);
		}
	return returnValue;
	}

	// specific detail address for specific users 
	@GetMapping(path = "/{id}/addresses/{addressId}", produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AddressesRest getUserAddress(@PathVariable String addressId)throws Exception  {

		AddressDTO addressesDTO= addressesService.getAddress(addressId);
		ModelMapper modelMapper  = new ModelMapper();
		AddressesRest addressesRestModel = modelMapper.map(addressesDTO, AddressesRest.class);
	
		return addressesRestModel;
	}

    
}
