package com.imaginnovate.interview.code.exception;

public class EmployeeNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5403920137257864396L;

	public EmployeeNotFoundException(Long id) {
        super("Employee not found with id: " + id);
    }
}

