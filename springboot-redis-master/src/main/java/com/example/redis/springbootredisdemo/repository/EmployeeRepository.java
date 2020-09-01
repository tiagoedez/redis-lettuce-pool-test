package com.example.redis.springbootredisdemo.repository;

import com.example.redis.springbootredisdemo.model.Employee;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

	
//    private HashOperations hashOperations;
//    private RedisTemplate redisTemplate;
//
//    public EmployeeRepository(RedisTemplate redisTemplate) {
//        this.hashOperations=redisTemplate.opsForHash();
//        this.redisTemplate = redisTemplate;
//    }
//
//    public void saveEmployee(Employee employee){
//        hashOperations.put("EMPLOYEE",employee.getId(),employee);
//    }
//
//    public List<Employee> findAll(){
//       return hashOperations.values("EMPLOYEE");
//    }
//
//    public Employee findById(Integer id){
//        return (Employee) hashOperations.get("EMPLOYEE",id);
//
//    }
//    public void update(Employee employee){
//        saveEmployee(employee);
//    }
//    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE",id);
//    }

}
