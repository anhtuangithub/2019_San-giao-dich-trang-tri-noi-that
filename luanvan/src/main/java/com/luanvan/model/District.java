package com.luanvan.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "district")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
	@Id
	@Column(name = "districtid")
	private String districtid;
	
	@Column(name = "name", columnDefinition = "nvarchar(255)")
	private String name;
	
	
	@Column(name="provinceid")
    private String provinceid;
	
	@JsonIgnore
	@OneToMany(mappedBy = "district")
	private List<Store> stores;
	
	
    


}
