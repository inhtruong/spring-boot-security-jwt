package com.ait.demospringsecurity.services.student;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.demospringsecurity.models.Student;
import com.ait.demospringsecurity.repositories.IStudentRepository;

@Service
public class StudentServiceImpl implements IStudentService{
	
	@Autowired
	private IStudentRepository studentRepository;

	@Override
	public Iterable<Student> findAll() {
		// TODO Auto-generated method stub
		return studentRepository.findAll();
	}

	@Override
	public Optional<Student> findById(long id) {
		// TODO Auto-generated method stub
		return studentRepository.findById(id);
	}

	@Override
	public Student save(Student t) {
		// TODO Auto-generated method stub
		return studentRepository.save(t);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		studentRepository.deleteById(id);
	}

	@Override
	public Optional<Student> findByUsername(String username) {
		// TODO Auto-generated method stub
		return studentRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		return studentRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return studentRepository.existsByEmail(email);
	}

}
