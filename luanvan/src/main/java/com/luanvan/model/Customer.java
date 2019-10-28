package com.luanvan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Vui lòng nhập tên")
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "sex")
	private String sex;
	
	@Pattern(regexp = "(03[2-9]|05[6|8|9]|07[0|6-9]|08[1-9]|09[^5])+([0-9]{7})\\b", message = "Số điện thoại sai")
	@Column(name= "phone",  columnDefinition = "varchar(10)", nullable = true)
	private String phone;
	
	@Column(name = "birthday")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date birthday = new Date();
	
	@OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
	

	
}
