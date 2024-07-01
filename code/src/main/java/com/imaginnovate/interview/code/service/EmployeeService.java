package com.imaginnovate.interview.code.service;

import com.imaginnovate.interview.code.dto.EmployeeTaxInfo;
import com.imaginnovate.interview.code.entities.Employee;

public interface EmployeeService {
    public Employee saveEmployee(Employee employee);
    public EmployeeTaxInfo calculateTax(Employee employee) ;
	public Employee findById(Long id);

}
