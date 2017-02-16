package com.spsproject.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spsproject.domain.CustomUser;
import com.spsproject.repository.CustomUserRepository;
import com.spsproject.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping
public class HomeController {

	@Autowired
	private CustomUserRepository customUserRepository;
	
	//@Autowired
	//private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<CustomUser> createUser(@RequestBody CustomUser customUser) {
		
		return new ResponseEntity<CustomUser>(userService.register(customUser), HttpStatus.CREATED);
	}

	
	@RequestMapping("/user")
	public CustomUser user(Principal principal) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		return customUserRepository.findOneByUsername(loggedUsername);
	}


	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password,
			HttpServletResponse response) throws IOException {

		Map<String, Object> tokenMap = userService.login(username, password);

		if(tokenMap.get("token") != null){
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
		}

	}
}
