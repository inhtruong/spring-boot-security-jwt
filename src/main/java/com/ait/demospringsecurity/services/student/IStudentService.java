package com.ait.demospringsecurity.services.student;

import java.util.Optional;

import com.ait.demospringsecurity.models.Student;
import com.ait.demospringsecurity.services.IGeneralService;

public interface IStudentService extends IGeneralService<Student>{
	Optional<Student> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
