package com.example.demo.student.management;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.student.Student;

@RestController
@RequestMapping("/management/api/v1")
public class StudentManagementController {

	private final List<Student> STUDENTS = Arrays.asList(new Student(1, 
			"John"), new Student(2,"Billy"), new Student(3,"Alex"));

	@GetMapping(path= "/all")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_STUDENT')")
	public List<Student> getAllStudents(){
		return STUDENTS;
	}
	
	@PostMapping
	public void registerStudent(@RequestBody Student student) {
		System.out.println("register");
	}
	
	@DeleteMapping(path= "{id}")
	@PreAuthorize("hasAuthority('student:read')")
	public void deleteStudent(@PathVariable("id") Integer id) {
		System.out.println("delete");
	}
	
	@PutMapping(path="{id}")
	public void updateStudent(@PathVariable("id") Integer id, @RequestBody Student student) {
		System.out.println("update");
	}
	
}
