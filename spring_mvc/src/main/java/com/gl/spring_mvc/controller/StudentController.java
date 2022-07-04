package com.gl.spring_mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.spring_mvc.exception.ResourceNotFoundException;
import com.gl.spring_mvc.model.Student;
import com.gl.spring_mvc.repository.StudentsRepository;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/crud")
public class StudentController {

	@Autowired
	private StudentsRepository studentsRepository;
	
	// http://localhost:8080/crud/students	
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return studentsRepository.findAll();
	}
	
	//create student rest api
	@PostMapping("/student")
	public Student createStudent(@RequestBody Student student) {
		return studentsRepository.save(student);
	}
	
	//get student id by rest api
	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		Student student = studentsRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("There is no student with id:  "+id));
		return ResponseEntity.ok(student);
	}
	
	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id,@RequestBody Student studentDetails) {
		Student student = studentsRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("There is no student with id:  "+id));
		
		student.setName(studentDetails.getName());
		student.setDepartment(studentDetails.getDepartment());
		student.setCountry(studentDetails.getCountry());
		
		Student updatedStudent = studentsRepository.save(student);
		
		return ResponseEntity.ok(updatedStudent);	
		}
	
	//delete student rest api
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteStudent(@PathVariable Long id){
		Student student = studentsRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("There is no student with id:  "+id));
		
		studentsRepository.delete(student);
		Map<String,Boolean> deleteResponse = new HashMap<>(); 
		
		deleteResponse.put("Deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(deleteResponse);	
	}
}