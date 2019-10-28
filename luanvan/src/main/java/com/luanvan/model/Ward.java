package com.luanvan.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "ward")
@Data
public class Ward {
	@Id
	@Column(name = "wardid")
	private String wardid;
	
	@Column(name = "name", columnDefinition = "nvarchar(255)")
	private String name;
	
	@Column(name = "districtid")
    private String districtid;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ward")
	private List<Store> stores;
	
	


}
