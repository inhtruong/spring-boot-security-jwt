package com.ait.demospringsecurity.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ait.demospringsecurity.models.Student;



@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
