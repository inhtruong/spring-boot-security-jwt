package com.ait.demospringsecurity.services.user;

import java.util.Optional;

import com.ait.demospringsecurity.models.User;
import com.ait.demospringsecurity.services.IGeneralService;

public interface IUserService extends IGeneralService<User>{
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
