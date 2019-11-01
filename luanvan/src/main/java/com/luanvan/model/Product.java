package com.luanvan.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="product")
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name;
	
	private String plug;
	
	@Column(name = "description", columnDefinition = "text" )
	private String description;
	
	@NotNull
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "avgstart",columnDefinition = "float default 0")
	private float avgstart;
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	@Column(name = "created_at",nullable = false, updatable = false)
	@CreatedDate
	private Date created_at;
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	@Column(name = "updated_at")
	@LastModifiedDate
	private Date updated_at;
	
	@Column(name = "status", columnDefinition = "tinyInt(1) default 0")
	private int status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "products")
	private List<Inventory> inventory;
	
	@JsonIgnore
	@OneToMany(mappedBy = "products")
	private List<Question> questions;
	
	@JsonIgnore
	@OneToMany(mappedBy="product_id")
    private List<Image> images;
	
	@JsonIgnore
	@OneToMany(mappedBy="product_id")
    private List<Image360> image360;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy="products")
    private List<Review> reviews;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<UnitPrice> unitPrices;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetail;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category categorys;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store stores;
	
	@ManyToOne
	@JoinColumn(name = "producer_id")
	private Producer producers;
	
	@ManyToOne
	@JoinColumn(name = "origin_id")
	private Origin origins;
	
	@ManyToOne
	@JoinColumn(name = "material_id")
	private Material materials;
	
	@ManyToMany
    @JoinTable(
            name = "product_color",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
    private Set<Color> colors;
	
	
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Promotion> promotions;
}
