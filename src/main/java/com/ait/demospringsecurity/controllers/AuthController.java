package com.ait.demospringsecurity.controllers;

import javax.validation.Valid;

import com.ait.demospringsecurity.models.ERole;
import com.ait.demospringsecurity.securities.securities.service.StudentDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.demospringsecurity.models.Role;
import com.ait.demospringsecurity.models.Student;
import com.ait.demospringsecurity.models.User;
import com.ait.demospringsecurity.securities.payloads.request.LoginRequest;
import com.ait.demospringsecurity.securities.payloads.request.SignupRequest;
import com.ait.demospringsecurity.securities.payloads.response.JwtResponse;
import com.ait.demospringsecurity.securities.payloads.response.MessageResponse;
import com.ait.demospringsecurity.securities.securities.jwt.JwtUtils;
import com.ait.demospringsecurity.securities.securities.service.UserDetailsImpl;
import com.ait.demospringsecurity.services.role.IRoleService;
import com.ait.demospringsecurity.services.student.IStudentService;
import com.ait.demospringsecurity.services.user.IUserService;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IStudentService studentService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;
	
//	@PostMapping("/signin")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		User currentUser = userService.findByUsername(loginRequest.getUsername()).get();
//		System.out.println(currentUser.getUsername());
//
//		JwtResponse jwtResponse =new JwtResponse(
//				jwt,
//				currentUser.getId(),
//				userDetails.getUsername(),
////				userDetails.getEmail(),
//				userDetails.getAuthorities()
//
//		);
//		ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
//				.httpOnly(false)
//				.secure(false)
//				.path("/")
//				.maxAge(60 * 1000)
//
//				.domain("localhost")
//				.build();
//		return ResponseEntity
//				.ok()
//				.header(HttpHeaders.SET_COOKIE, springCookie.toString())
//				.body(jwtResponse);
//
//	}
	
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//		if (userService.existsByUsername(signUpRequest.getUsername())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Username is already taken!"));
//		}
//
//		if (userService.existsByEmail(signUpRequest.getEmail())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Email is already in use!"));
//		}
//
//		// Create new user's account
//		User user = new User(signUpRequest.getUsername(),
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword()));
//
//		String strRole = signUpRequest.getRole();
//
//		switch (strRole) {
//			case "admin":
//				Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				user.setRole(adminRole);
//
//				break;
//			case "mod":
//				Role modRole = roleService.findByName(ERole.ROLE_MODERATOR)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				user.setRole(modRole);
//
//				break;
//			default:
//				Role userRole = roleService.findByName(ERole.ROLE_USER)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				user.setRole(userRole);
//		}
//
//
//		userService.save(user);
//
//		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Optional<User> currentUser = userService.findByUsername(loginRequest.getUsername());
		JwtResponse jwtResponse =new JwtResponse();

		if (currentUser.isPresent()) {
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			String jwt = jwtUtils.generateJwtToken(user, null);
			jwtResponse.setAccessToken(jwt);
			jwtResponse.setId(currentUser.get().getId());
			jwtResponse.setUsername(user.getUsername());
			jwtResponse.setRole(user.getAuthorities());

			ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
					.httpOnly(false)
					.secure(false)
					.path("/")
					.maxAge(60 * 1000)
					.domain("localhost")
					.build();

			return ResponseEntity
					.ok()
					.header(HttpHeaders.SET_COOKIE, springCookie.toString())
					.body(jwtResponse);
		} else {

			StudentDetailsImpl student = (StudentDetailsImpl) authentication.getPrincipal();
			String jwt = jwtUtils.generateJwtToken(null, student);
			Optional<Student> currentStu = studentService.findByUsername(loginRequest.getUsername());

			jwtResponse.setAccessToken(jwt);
			jwtResponse.setId(currentStu.get().getId());
			jwtResponse.setUsername(student.getUsername());
			jwtResponse.setRole(student.getAuthorities());

			ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
					.httpOnly(false)
					.secure(false)
					.path("/")
					.maxAge(60 * 1000)
					.domain("localhost")
					.build();

			return ResponseEntity
					.ok()
					.header(HttpHeaders.SET_COOKIE, springCookie.toString())
					.body(jwtResponse);
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.existsByUsername(signUpRequest.getUsername()) || studentService.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userService.existsByEmail(signUpRequest.getEmail()) || studentService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		String strRole = signUpRequest.getRole();
		System.out.println(strRole);
		
		if (strRole.equals("student")) {
			Student student = new Student(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));
			
			Role studentRole = roleService.findByName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			
			student.setRole(studentRole);
			
			studentService.save(student);
			
			return ResponseEntity.ok(new MessageResponse("Student registered successfully!"));
		} else {
			User user = new User(signUpRequest.getUsername(), 
					 signUpRequest.getEmail(),
					 encoder.encode(signUpRequest.getPassword()));

			switch (strRole) {
				case "admin":
					Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					user.setRole(adminRole);
			
					break;
				case "mod":
					Role modRole = roleService.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					user.setRole(modRole);
			
					break;
				default:
					Role userRole = roleService.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					user.setRole(userRole);
			}
			
			userService.save(user);
			
			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
		
		

		
	}
}
