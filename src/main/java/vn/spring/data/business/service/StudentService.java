package vn.spring.data.business.service;

import java.util.List;

import vn.spring.data.business.entity.Student;

public interface StudentService {

	List<Student> getAllStudents();

	Student saveStudent(Student student);

	Student updateStudent(Student student);

	Student getStudentById(Long id);

	void deleteStudentById(Long id);

}
