package com.cercli.model;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.ZonedDateTime;

/**
 * EmployeeRecord class which acts as Input and Output to controller and service methods.
 * While creating and updating object id, createdAt and ModifiedAt are not required as they are
 * automatically derived.
 *
 * Validate that name cannot be blank
 * Validate email to be in proper format, otherwise raise an exception.
 * @param id
 * @param name
 * @param position
 * @param email
 * @param salary
 * @param createdAt
 * @param modifiedAt
 */

public record EmployeeRecord(String id, String name, String position, String email, Double salary,
		ZonedDateTime createdAt, ZonedDateTime modifiedAt) {

	public EmployeeRecord {
		if (name.isBlank())
			throw new IllegalArgumentException("Name cannot be blank");
		if (!EmailValidator.getInstance().isValid(email))
			throw new IllegalArgumentException("Email provided is not in valid format");

	}

}
