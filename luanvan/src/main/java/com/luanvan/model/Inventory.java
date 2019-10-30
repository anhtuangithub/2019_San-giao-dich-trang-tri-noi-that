package com.luanvan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "inventory")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private int quantity;
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	@Column(name = "created_at", updatable = false)
	@CreatedDate
	private Date created_at;
	
	@Column(name = "date_receipt")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date date_receipt = new Date();
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product products;
}
