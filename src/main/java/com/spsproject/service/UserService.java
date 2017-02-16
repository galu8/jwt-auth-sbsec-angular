package com.spsproject.service;

import java.util.Map;

import com.spsproject.domain.CustomUser;

public interface UserService {

	CustomUser register(CustomUser user);
	
	Map<String, Object> login (String username, String password);
}
