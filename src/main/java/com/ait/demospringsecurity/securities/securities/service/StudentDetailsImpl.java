package com.ait.demospringsecurity.securities.securities.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ait.demospringsecurity.models.Student;

public class StudentDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final Long id;

	private final String username;

	private final String email;


	private final String password;

	private Collection<? extends GrantedAuthority> roles;

	public StudentDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}


	public static StudentDetailsImpl build(Student student) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(student.getRole().getName().name());
		authorities.add(authority);

		return new StudentDetailsImpl(
				student.getId(), 
				student.getUsername(), 
				student.getEmail(),
				student.getPassword(), 
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		StudentDetailsImpl student = (StudentDetailsImpl) o;
		return Objects.equals(id, student.id);
	}

}
