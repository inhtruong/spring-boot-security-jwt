package com.ait.demospringsecurity.securities.securities.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ait.demospringsecurity.models.Student;
import com.ait.demospringsecurity.models.User;
import com.ait.demospringsecurity.repositories.IStudentRepository;
import com.ait.demospringsecurity.repositories.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IStudentRepository studentRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if (userRepository.findByUsername(username).isPresent()) {
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
			return UserDetailsImpl.build(user);
		} else {

			Student student = studentRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
			return StudentDetailsImpl.build(student);
		}
		

	}

}
