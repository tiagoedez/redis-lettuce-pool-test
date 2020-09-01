package com.example.redis.springbootredisdemo.controller;

import com.example.redis.springbootredisdemo.model.Employee;
import com.example.redis.springbootredisdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

//   @PostMapping("/employees")
//    public Employee saveEMployee(@RequestBody Employee employee){
//        employeeRepository.saveEmployee(employee);
//        return employee;
//    }
//    @GetMapping("/employees")
//    public List<Employee> findAll(){
//        return employeeRepository.findAll();
//    }
//    @GetMapping("/employees/{id}")
//    public Employee findById(@PathVariable("id") Integer id){
//        return employeeRepository.findById(id);
//    }
//
//    @PutMapping("/employees")
//    public void update(@RequestBody Employee employee){
//        employeeRepository.update(employee);
//
//    }
//    @DeleteMapping("/employees/{id}")
//    public  void delete(@PathVariable("id") Integer id){
//        employeeRepository.delete(id);
//    }
    
    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
    	employeeRepository.save(employee);
    	return employee;
    }
    @GetMapping("/employees")
    public List<Employee> findAll(){
      return (List<Employee>) employeeRepository.findAll();
    }
    @GetMapping("/employees/{id}")
    public Optional<Employee> findById(@PathVariable("id") Integer id){
      return employeeRepository.findById(id);
    }
    @PutMapping("/employees")
    public Employee update(@RequestBody Employee employee){
      employeeRepository.save(employee);
      return employee;
    }
    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable("id") Integer id){
      employeeRepository.deleteById(id);
    }
    
}