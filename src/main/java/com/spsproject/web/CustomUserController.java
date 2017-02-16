package com.spsproject.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spsproject.domain.CustomUser;
import com.spsproject.repository.CustomUserRepository;


@RestController
@RequestMapping(value = "/api")
public class CustomUserController {

	@Autowired
	private CustomUserRepository customUserRepository;

	// TODO create a service where do the operations in these methods

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<CustomUser> users() {
		return customUserRepository.findAll();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<CustomUser> userById(@PathVariable Long id) {

		CustomUser customUser = customUserRepository.findOne(id);

		if (customUser == null) {
			return new ResponseEntity<CustomUser>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<CustomUser>(customUser, HttpStatus.OK);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CustomUser> deleteUser(@PathVariable Long id) {

		CustomUser customUser = customUserRepository.findOne(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();

		if (customUser == null) {

			return new ResponseEntity<CustomUser>(HttpStatus.NO_CONTENT);

		} else if (customUser.getUsername().equalsIgnoreCase(loggedUsername)) {

			throw new RuntimeException("An Admin cannot delete his account");

		} else {

			customUserRepository.delete(customUser);
			return new ResponseEntity<CustomUser>(customUser, HttpStatus.OK);

		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<CustomUser> createUser(@RequestBody CustomUser appUser) {
		if (customUserRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		return new ResponseEntity<CustomUser>(customUserRepository.save(appUser), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public CustomUser updateUser(@RequestBody CustomUser customUser) {

		if ((customUserRepository.findOneByUsername(customUser.getUsername()) != null) && (customUserRepository
				.findOneByUsername(customUser.getUsername()).getUserId() != customUser.getUserId())) {
			throw new RuntimeException("Username already exist");
		}
		return customUserRepository.save(customUser);
	}
}
