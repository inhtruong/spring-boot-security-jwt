package com.ait.demospringsecurity.services.role;

import java.util.Optional;

import com.ait.demospringsecurity.models.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ait.demospringsecurity.models.Role;
import com.ait.demospringsecurity.repositories.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public Iterable<Role> findAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findById(long id) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id);
	}

	@Override
	public Role save(Role t) {
		// TODO Auto-generated method stub
		return roleRepository.save(t);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		roleRepository.deleteById(id);
	}

	@Override
	public Optional<Role> findByName(ERole name) {
		// TODO Auto-generated method stub
		return roleRepository.findByName(name);
	}

}
