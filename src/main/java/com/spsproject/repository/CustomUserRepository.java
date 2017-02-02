package com.spsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsproject.domain.CustomUser;


public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

	CustomUser findOneByUsername(String username);

}
