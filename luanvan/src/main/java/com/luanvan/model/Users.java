package com.luanvan.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String email;
	
	private String password;
	
	@JsonIgnore
	@OneToOne(mappedBy = "users")
    private Store store;
	
	
	@JsonIgnore
	@OneToOne(mappedBy = "users")
    private Customer customer;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
    private Set<Order> order;

	@JsonIgnore
	@OneToMany(mappedBy="user")
    private Set<OrderGroup> orderGroup;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
    private Set<Review> reviews;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
    private Set<Question> questions;
	
	@ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	@NotNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
    private Set<Role> roles;
	
	
}
