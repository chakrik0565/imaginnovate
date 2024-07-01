package com.imaginnovate.interview.code.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.interview.code.dto.EmployeeTaxInfo;
import com.imaginnovate.interview.code.entities.Employee;
import com.imaginnovate.interview.code.exception.EmployeeNotFoundException;
import com.imaginnovate.interview.code.repository.EmployeeRepository;
import com.imaginnovate.interview.code.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public EmployeeTaxInfo calculateTax(Employee employee) {
		double yearlySalary = calculateYearlySalary(employee);
		double taxAmount = calculateTaxAmount(yearlySalary);
		double cessAmount = calculateCessAmount(yearlySalary);
		EmployeeTaxInfo taxInfo = new EmployeeTaxInfo();
		taxInfo.setEmployeeCode(employee.getId());
		taxInfo.setFirstName(employee.getFirstName());
		taxInfo.setLastName(employee.getLastName());
		taxInfo.setYearlySalary(yearlySalary);
		taxInfo.setTaxAmount(taxAmount);
		taxInfo.setCessAmount(cessAmount);
		return taxInfo;
	}

	private double calculateYearlySalary(Employee employee) {
		LocalDate doj = employee.getDoj();
		LocalDate now = LocalDate.now();
		LocalDate financialYearStart = LocalDate.now().withMonth(4).withDayOfMonth(1);
		LocalDate startDate = doj.isAfter(financialYearStart) ? doj : financialYearStart;
		int monthsWorked = calculateMonthsWorked(startDate, now);
		int daysWorked = now.getDayOfMonth();
		double dailySalary = employee.getSalary() / 30;// need to include logic
		return employee.getSalary() * monthsWorked + dailySalary * daysWorked;
	}

	private int calculateMonthsWorked(LocalDate start, LocalDate end) {
		int startYear = start.getYear();
		int startMonth = start.getMonthValue();
		int endYear = end.getYear();
		int endMonth = end.getMonthValue();

		return (endYear - startYear) * 12 + (endMonth - startMonth);
	}

	private double calculateTaxAmount(double yearlySalary) {
		if (yearlySalary <= 250000) {
			return 0;
		} else if (yearlySalary <= 500000) {
			return (yearlySalary - 250000) * 0.05;
		} else if (yearlySalary <= 1000000) {
			return 12500 + (yearlySalary - 500000) * 0.1;
		} else {
			return 62500 + (yearlySalary - 1000000) * 0.2;
		}
	}

	private double calculateCessAmount(double yearlySalary) {
		if (yearlySalary <= 2500000) {
			return 0;
		} else {
			return (yearlySalary - 2500000) * 0.02;
		}
	}

	@Override
	public Employee findById(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}
}
