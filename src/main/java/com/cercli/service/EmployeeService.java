package com.cercli.service;

import com.cercli.model.Employee;
import com.cercli.model.EmployeeRecord;
import com.cercli.repository.EmployeeRepository;
import com.cercli.util.EmployeeHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Service class to define all the business logic for operations on employee object
 *
 * Annotation Used:
 *
 * @Service: Denotes a service layer object in Spring Boot and helps Spring to identify
 * and load it in context
 */

@Service
@Slf4j
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/**
	 * Add Employee method to add an employee object
	 * @param employeeRecord
	 * @param timeZone
	 * @return EmployeeRecord object after transformation
	 */
	public EmployeeRecord addEmployee(EmployeeRecord employeeRecord, TimeZone timeZone) throws Exception {

		Employee employee = new Employee().builder()
			.name(employeeRecord.name())
			.position(employeeRecord.position())
			.email(employeeRecord.email())
			.salary(employeeRecord.salary())
			.createdAt(OffsetDateTime.now(ZoneId.of("UTC")))
			.modifiedAt(OffsetDateTime.now(ZoneId.of("UTC")))
			.build();
		try {
			employee = employeeRepository.save(employee);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		EmployeeRecord response = EmployeeHelper.parseEmployee(employee, timeZone, 1);

		log.info(response.toString());

		return response;
	}

	/**
	 * Update operation on employee object if found otherwise return exception It updates
	 * the date as per UTC format
	 * @param employeeRecord
	 * @param employeeId
	 * @param timeZone
	 * @return
	 */

	public EmployeeRecord updateEmployee(EmployeeRecord employeeRecord, String employeeId, TimeZone timeZone) {

		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

		if (optionalEmployee.isEmpty())
			throw new RuntimeException("Employee is not present");

		int revision = employeeRepository.findLastChangeRevision(optionalEmployee.get().getEmployee_id())
			.get()
			.getRevisionNumber()
			.get();

		Employee employee = optionalEmployee.get();
		employee.setName(employeeRecord.name());
		employee.setEmail(employeeRecord.email());
		employee.setPosition(employeeRecord.position());
		employee.setSalary(employeeRecord.salary());
		employee.setModifiedAt(OffsetDateTime.now(ZoneId.of("UTC")));

		employee = employeeRepository.save(employee);

		EmployeeRecord response = EmployeeHelper.parseEmployee(employee, timeZone, revision);

		log.info(response.toString());

		return response;
	}

	/**
	 * Get operation to fnd employee object by id, throws exception if not found
	 * @param employeeId
	 * @param timeZone
	 * @return
	 */
	public EmployeeRecord getEmployeeById(String employeeId, TimeZone timeZone) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

		if (optionalEmployee.isEmpty())
			throw new RuntimeException("Employee is not present");

		int revision = employeeRepository.findLastChangeRevision(optionalEmployee.get().getEmployee_id())
			.get()
			.getRevisionNumber()
			.get();

		EmployeeRecord response = EmployeeHelper.parseEmployee(optionalEmployee.get(), timeZone, revision);

		log.info(response.toString());

		return response;
	}

	/**
	 * Returns list of all employees
	 * @param timeZone
	 * @return
	 */
	public List<EmployeeRecord> getAllEmployees(TimeZone timeZone) {
		Iterable<Employee> employees = employeeRepository.findAll();

		List<EmployeeRecord> employeeRecordList = new ArrayList<>();

		employees.forEach(emp -> {
			EmployeeRecord employeeRecord = EmployeeHelper.parseEmployee(emp, timeZone,
					employeeRepository.findLastChangeRevision(emp.getEmployee_id()).get().getRevisionNumber().get());
			log.info(employeeRecord.toString());
			employeeRecordList.add(employeeRecord);
		});

		return employeeRecordList;
	}

	/**
	 * Get all the revision history of an employee
	 * @param employeeId
	 * @param timeZone
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeRecord> getAllRevision(String employeeId, TimeZone timeZone) throws Exception {

		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

		if (optionalEmployee.isEmpty())
			throw new RuntimeException("Employee is not present");

		List<EmployeeRecord> result = new ArrayList<>();
		employeeRepository.findRevisions(employeeId)
			.forEach(rev -> result
				.add(EmployeeHelper.parseEmployee(rev.getEntity(), timeZone, rev.getRevisionNumber().get())));

		return result;
	}

}
