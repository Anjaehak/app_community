package com.company.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "certifies")
@Data
public class Certify {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String email;
	private String code;
	private String status;

}
