package com.spsproject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spsproject.domain.CustomUser;
import com.spsproject.domain.Role;
import com.spsproject.repository.CustomUserRepository;
import com.spsproject.repository.RoleRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private CustomUserRepository userRepository;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Override
	public CustomUser register(CustomUser user) {
		
		if (userRepository.findOneByUsername(user.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		
		Role rol = roleRepository.findByName("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		user.setRoles(roles);
		
		return userRepository.save(user);
	}	
	
	@Override
	public Map<String, Object> login(String username, String password) {

		String token = null;
		CustomUser customUser = userRepository.findOneByUsername(username);
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		
		if ((customUser != null) && (customUser.getPassword().equals(password))) {


			Map<String, Object> claims = new HashMap<>();
			claims.put("sub", username);
			claims.put("roles",customUser.getRoles());
			
			token = Jwts.builder()
					.setClaims(claims)
	                .signWith(SignatureAlgorithm.HS512, "secretkey")
	                .compact();

			tokenMap.put("token", token);
			tokenMap.put("user", customUser);
		} else {
			tokenMap.put("token", null);
		}
		
		return tokenMap;
	}
	

	
}
