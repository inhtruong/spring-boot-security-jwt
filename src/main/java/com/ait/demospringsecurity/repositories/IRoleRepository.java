package com.ait.demospringsecurity.repositories;

import java.util.Optional;

import com.ait.demospringsecurity.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ait.demospringsecurity.models.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);

}
