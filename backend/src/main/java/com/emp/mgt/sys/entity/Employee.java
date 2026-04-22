package com.emp.mgt.sys.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employees")
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
	
	@Column(nullable =false)
	private String jobTitle;
	
	private String phone;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	@JsonBackReference
	private Department department;
	
	
}
