package com.spsproject.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.spsproject.domain.Role;
import com.spsproject.repository.CustomUserRepository;
import com.spsproject.repository.RoleRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping
public class HomeController {

	@Autowired
	private CustomUserRepository customUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	// TODO create a service where do the operations in these methods

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<CustomUser> createUser(@RequestBody CustomUser customUser) {

		if (customUserRepository.findOneByUsername(customUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		
		Role rol = roleRepository.findByName("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(rol);
		customUser.setRoles(roles);
		return new ResponseEntity<CustomUser>(customUserRepository.save(customUser), HttpStatus.CREATED);
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

		String token = null;
		CustomUser customUser = customUserRepository.findOneByUsername(username);
		Map<String, Object> tokenMap = new HashMap<String, Object>();

		if ((customUser != null) && (customUser.getPassword().equals(password))) {

			token = Jwts.builder().setSubject(username).claim("roles", customUser.getRoles()).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey").compact();

			tokenMap.put("token", token);
			tokenMap.put("user", customUser);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);

		} else {

			tokenMap.put("token", null);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);

		}

	}
}
