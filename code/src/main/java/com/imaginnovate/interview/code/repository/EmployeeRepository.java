package com.imaginnovate.interview.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imaginnovate.interview.code.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
