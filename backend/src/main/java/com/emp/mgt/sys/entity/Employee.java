package com.emp.mgt.sys.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable =false)
	private String firstName;
	
	@Column(nullable =false)
	private String lastName;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@Column(nullable = false)
	private String department;
	
	@Column(nullable =false)
	private String jobTitle;
	
	private String phone;
	
}
