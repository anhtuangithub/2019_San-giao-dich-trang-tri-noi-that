package com.luanvan.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "address")
	private String address;
	
	@Column(name= "phone",  columnDefinition = "varchar(10)", nullable = true)
	private String phone;
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	@Column(name = "created_at", updatable = false)
	@CreatedDate
	private Date created_at;
	

    @JsonIgnore
    @OneToMany(mappedBy = "orders")
    private List<OrderDetail> orderDetail;
    
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<Review> review;
	
	@ManyToOne
	@JoinColumn(name = "payment_id")
	private PaymentType paymentType;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "orderstatus_id")
	private OrderStatus orderStatus;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ordergroup_id")
	private OrderGroup orderGroup;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store stores;
	
	public int getTotal() {
		int total = 0;
       for(OrderDetail orderDetail: this.getOrderDetail()) {
    	   total += orderDetail.getTotalPrice();
       }
       return total;
    }
	
	
	
}
