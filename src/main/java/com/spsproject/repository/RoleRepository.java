package com.spsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsproject.domain.Role;

public interface RoleRepository extends JpaRepository<Role,Long >{
	
	Role findByName(String name);
}
