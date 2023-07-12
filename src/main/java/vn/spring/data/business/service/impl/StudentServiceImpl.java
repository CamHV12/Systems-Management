package vn.spring.data.business.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import vn.spring.data.business.entity.Student;
import vn.spring.data.business.repository.StudentRepository;
import vn.spring.data.business.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student student) {
		return studentRepository.save(student);
	}
	@Override
	public void deleteStudentById(Long id) {
		studentRepository.deleteById(id);
	}
}
