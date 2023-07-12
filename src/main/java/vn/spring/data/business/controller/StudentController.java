package vn.spring.data.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.LongArraySerializer;

import lombok.RequiredArgsConstructor;
import vn.spring.data.business.entity.Student;
import vn.spring.data.business.service.StudentService;

@Controller
@RequiredArgsConstructor
public class StudentController {
	private final StudentService studentService;

	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}

	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
	}

	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}

	@GetMapping("/students/edit/{id}")
	public String getStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,@ModelAttribute Student student) {
		Student stu = studentService.getStudentById(id);
		stu.setId(student.getId());
		stu.setFirstName(student.getFirstName());
		stu.setLastName(student.getLastName());
		stu.setEmail(student.getEmail());
		
		studentService.updateStudent(stu);
		return "redirect:/students";
	}
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
}
