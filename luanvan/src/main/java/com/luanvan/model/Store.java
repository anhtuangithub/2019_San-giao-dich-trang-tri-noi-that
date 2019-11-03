package com.luanvan.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Vui lòng nhập tên Cửa Hàng")
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@NotBlank(message = "Vui lòng nhập số điện thoại")
	@Pattern(regexp = "(03[2-9]|05[6|8|9]|07[0|6-9]|08[1-9]|09[^5])+([0-9]{7})\\b", message = "Số điện thoại sai")
	@Column(name= "phone",  columnDefinition = "varchar(10)")
	private String phone;
	
	@Column(name= "introduce",  columnDefinition = "text")
	private String introduce;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "status", columnDefinition = "tinyInt(1) default 0")
	private int status;
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
	
	@JsonIgnore
	@OneToMany(mappedBy="stores")
    private List<Answer> answers;
	
	@JsonIgnore
	@OneToMany(mappedBy="stores")
    private List<Order> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy="stores")
    private List<Product> products;
	
	@JsonIgnore
	@OneToMany(mappedBy="store")
    private List<Promotion> promotions;
	
	
	@JsonIgnore
	@OneToMany(mappedBy="stores")
    private List<Member> members;
	
	
	@ManyToOne
    @JoinColumn(name="provinceid")
    private Province province;
	
	@ManyToOne
    @JoinColumn(name="districtid")
    private District district;
	
	@ManyToOne
    @JoinColumn(name="wardid")
    private Ward ward;
	
}
