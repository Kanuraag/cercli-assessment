package com.cercli.util;

import com.cercli.model.Employee;
import com.cercli.model.EmployeeRecord;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**
 * Utility class to represent date time in local zone as per the user
 */
public class EmployeeHelper {

	public static ZonedDateTime getLocalDateTime(OffsetDateTime dateTime, TimeZone timeZone) {
		ZonedDateTime localZonedDateTime = dateTime.atZoneSameInstant(timeZone.toZoneId());

		return localZonedDateTime;
	}

	public static EmployeeRecord parseEmployee(Employee employee, TimeZone timeZone, int version) {

		return new EmployeeRecord(employee.getEmployee_id(), employee.getName(), employee.getPosition(),
				employee.getEmail(), employee.getSalary(), getLocalDateTime(employee.getCreatedAt(), timeZone),
				getLocalDateTime(employee.getModifiedAt(), timeZone), version);
	}

}
