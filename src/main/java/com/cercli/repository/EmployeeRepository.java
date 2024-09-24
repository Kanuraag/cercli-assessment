package com.cercli.repository;

import com.cercli.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface extends the revision history interace which provides the functionality
 * to record all the changes in the object.
 */
@Repository
public interface EmployeeRepository
		extends RevisionRepository<Employee, String, Integer>, CrudRepository<Employee, String> {

}
