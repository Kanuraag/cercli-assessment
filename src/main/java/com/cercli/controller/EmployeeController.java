package com.cercli.controller;

import com.cercli.model.EmployeeRecord;
import com.cercli.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/rest/cercli/api/v1")
@Slf4j
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * A Post operation method to perform addition of employee in DB
	 * @param employeeRecord
	 * @param timeZone
	 * @return
	 */
	@PostMapping("/employee")
	public ResponseEntity<EmployeeRecord> addEmployee(@RequestBody EmployeeRecord employeeRecord, TimeZone timeZone) {
		ResponseEntity response = null;
		try {
			response = ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(employeeRecord, timeZone));
		}catch(Exception e){
			log.error(e.getMessage());
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return response;
	}

	/**
	 * A PUT operation to update employee object if provided id is found otherwise return 404
	 * @param employeeId
	 * @param employeeRecord
	 * @param timeZone
	 * @return
	 */

	@PutMapping("/employee/{id}")
	public ResponseEntity<EmployeeRecord> updateEmployee(@PathVariable("id") String employeeId, @RequestBody EmployeeRecord employeeRecord,
			TimeZone timeZone) {
		ResponseEntity response = null;
		try {
			response = ResponseEntity.ok(employeeService.updateEmployee(employeeRecord, employeeId, timeZone));
		}catch(Exception e){
			log.error(e.getMessage());
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return response;
	}

	/**
	 * A GET operation to find employee record if found otherwise return 404
	 * @param employeeId
	 * @param timeZone
	 * @return
	 */

	@GetMapping("/employee/{id}")
	public ResponseEntity<EmployeeRecord> getEmployee(@PathVariable("id") String employeeId, TimeZone timeZone) {
		ResponseEntity response = null;
		try {
			return ResponseEntity.ok(employeeService.getEmployeeById(employeeId, timeZone));
		}catch(Exception e){
			log.error(e.getMessage());
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return response;
	}

	/**
	 * An operation to provide list of all employees
	 * @param timeZone
	 * @return
	 */
	@GetMapping("/employee")
	public ResponseEntity<List<EmployeeRecord>> getEmployees(TimeZone timeZone) {
		return ResponseEntity.ok(employeeService.getAllEmployees(timeZone));
	}

}
