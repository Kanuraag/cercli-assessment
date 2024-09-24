package com.cercli.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;

import java.time.OffsetDateTime;

/**
 * Entity class representing table employee Attributes employee_id type UUID Primary Key
 * name type String email type String position type String salary type Double created_at
 * Timestamp with Time Zone modified_at Timestamp with Time zone composite unique key of
 * name and email
 *
 * Annotations Used
 *
 * @Entity : Denotes table in JPA
 * @Table: Define which table in DB it assosciates or represents @NoArgsConstructor;
 * Creates one no argument constructor
 * @AllArgsConstructor: Creates constructor with all arguments
 * @Data: Lombok library annotation to generate getter and setter
 * @Builder: Use to define builder pattern so that creation of object becomes easy
 * @ToString annotation to override the default toString function
 */
@Entity
@Table(name = "employee",
		uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndEmail", columnNames = { "name", "email" }) })
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@Audited
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String employee_id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "position", nullable = false)
	private String position;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "salary", nullable = false)
	private double salary;

	@Column(name = "created_at", nullable = false,
			columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")
	private OffsetDateTime createdAt;

	@Column(name = "modified_at", nullable = false,
			columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")
	private OffsetDateTime modifiedAt;

	@Transient
	private RevisionMetadata<Integer> editVersion;

}
