package com.luanvan.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="origin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Origin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	private String plug;
	
	@Column(name = "status", columnDefinition = "tinyInt(1) default 0")
	private int status;
	

	@JsonIgnore
	@OneToMany(mappedBy = "origins")
	private List<Product> products;
}
