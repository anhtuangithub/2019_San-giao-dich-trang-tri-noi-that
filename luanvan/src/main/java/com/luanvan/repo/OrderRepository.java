package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.dto.response.ChartMonthByYearDTO;
import com.luanvan.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByOrderStatusIdNotIn(Long id);
	List<Order> findByOrderStatusIdIn(Long id);
	
	List<Order> findByUserId(Long id);
	
	List<Order> findByStoresId(Long id);
	List<Order> findByOrderGroupId(Long id);
//////
//    Thông kê ở admin
//////
	@Query(value = "select sum((unit_price.price*(100 - ifnull(promotion.sale_off,0))/100)*order_detail.quantity) as tongtien, Year(orders.created_at) as nam, Month(orders.created_at) as thang " + 
			"from orders join order_detail on order_detail.order_id = orders.id join unit_price on unit_price.id = order_detail.price_id  left join promotion on promotion.id = order_detail.promotion_id"
			+ " where Year(orders.created_at) = :year And orders.orderstatus_id = :status"
			+ " group by Year(orders.created_at), Month(orders.created_at)",nativeQuery=true)
	List<ChartMonthByYearDTO> findByOrderStatusId(@Param("year") int year,@Param("status") Long statusid);
	
	
	@Query(value = "select sum((unit_price.price*(100 - ifnull(promotion.sale_off,0))/100)*order_detail.quantity) as tongtien, Year(orders.created_at) as nam,  Day(orders.created_at) as thang " + 
			"from orders join order_detail on order_detail.order_id = orders.id join unit_price on unit_price.id = order_detail.price_id left join promotion on promotion.id = order_detail.promotion_id"
			+ " where Year(orders.created_at) = :year And Month(orders.created_at) = :month And orders.orderstatus_id = :status"
			+ " group by Year(orders.created_at), Day(orders.created_at)",nativeQuery=true)
	List<ChartMonthByYearDTO> chartMonth(@Param("year") int year,@Param("month") int month ,@Param("status") Long statusid);
	
	@Query(value = "select sum((unit_price.price*(100 - ifnull(promotion.sale_off,0))/100)*order_detail.quantity)" + 
			"from orders join order_detail on order_detail.order_id = orders.id join unit_price on unit_price.id = order_detail.price_id left join promotion on promotion.id = order_detail.promotion_id"
			+ " where orders.orderstatus_id = :status",nativeQuery=true)
	float sumOrder(@Param("status") Long statusid);
	
	@Query(value = "select count(orders.orderstatus_id)" + 
			" from orders "
			+ " where orders.orderstatus_id = :status",nativeQuery=true)
	float donHangDaDuyet(@Param("status") Long statusid);
	
//////
//  Thống kê theo store
////
	
	@Query(value = "select sum((unit_price.price*(100 - ifnull(promotion.sale_off,0))/100)*order_detail.quantity) as tongtien, Year(orders.created_at) as nam, Month(orders.created_at) as thang " + 
			"from orders join order_detail on order_detail.order_id = orders.id join unit_price on unit_price.id = order_detail.price_id left join promotion on promotion.id = order_detail.promotion_id"
			+ " where Year(orders.created_at) = :year AND orders.store_id = :store  AND orders.orderstatus_id = :status"
			+ " group by Year(orders.created_at), Month(orders.created_at)",nativeQuery=true)
	List<ChartMonthByYearDTO> findByStoresIdOrderStatusId(@Param("year") int year,@Param("store") Long storeid,@Param("status") Long statusid);
	
	@Query(value = "select sum((unit_price.price*(100 - ifnull(promotion.sale_off,0))/100)*order_detail.quantity) as tongtien, Year(orders.created_at) as nam, Day(orders.created_at) as thang " + 
			"from orders join order_detail on order_detail.order_id = orders.id join unit_price on unit_price.id = order_detail.price_id left join promotion on promotion.id = order_detail.promotion_id"
			+ " where Year(orders.created_at) = :year AND Month(orders.created_at) = :month AND orders.store_id = :store  AND orders.orderstatus_id = :status"
			+ " group by Year(orders.created_at), Day(orders.created_at)",nativeQuery=true)
	List<ChartMonthByYearDTO> chartMonthByStoresId(@Param("year") int year,@Param("month") int month,@Param("store") Long storeid,@Param("status") Long statusid);
	
	@Query(value = "select sum((unit_price.price*(100 - ifnull(promotion.sale_off,0))/100)*order_detail.quantity)" + 
			"from orders join order_detail on order_detail.order_id = orders.id join unit_price on unit_price.id = order_detail.price_id left join promotion on promotion.id = order_detail.promotion_id"
			+ " where  orders.store_id = :store  AND orders.orderstatus_id = :status",nativeQuery=true)
	float sumOrderStoresId(@Param("store") Long storeid,@Param("status") Long statusid);
	
	@Query(value = "select count(orders.orderstatus_id)" + 
			" from orders "
			+ " where  orders.store_id = :store  AND orders.orderstatus_id = :status",nativeQuery=true)
	float donHangDaDuyetStore(@Param("store") Long storeid,@Param("status") Long statusid);
	
	
	@Query(value = "select count(orders.orderstatus_id) as count, orders.orderstatus_id as id from orders where orders.store_id = :store group by orders.orderstatus_id",nativeQuery=true)
	List<RoleUserDTO> thongKeTrangThaiDonHang(@Param("store") Long storeid);
	
	@Query(value = "select count(orders.orderstatus_id) as count, orders.orderstatus_id as id from orders group by orders.orderstatus_id",nativeQuery=true)
	List<RoleUserDTO> thongKeTTDHAdmin();
}
