package vn.spring.data.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.spring.data.business.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
