package com.luanvan.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.luanvan.dto.request.OrderDTO;
import com.luanvan.dto.request.OrderGroupDTO;
import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.dto.response.ChartMonthByYearDTO;
import com.luanvan.dto.response.OrderAdminDTO;
import com.luanvan.dto.response.OrderDetailDTO;
import com.luanvan.dto.response.OrderGroupResponseDTO;
import com.luanvan.model.Order;
import com.luanvan.model.OrderDetail;
import com.luanvan.model.Promotion;
import com.luanvan.model.UnitPrice;

public interface OrderService {

	//Create order
	Order create(Order order);
	
	//Update order
	void update(Order order);
	
	//Find order by id
	OrderAdminDTO findOrderById(Long id);
	
	// Find All Order
	List<Order> findAllOrder();
	
	//List All Order Not in id 7 (Đơn hàng bị hủy)
	List<Order> findOrderNotInId(Long id);
	
	//Check save order
	boolean checkSaveOrder(List<OrderDTO> orderDTO);
	
	// Get
	Map<String,String> saveOrder(List<OrderDTO> orderDTO, Authentication auth) throws Exception;
	
	UnitPrice UnitPriceApply(List<UnitPrice> price);
	
	UnitPrice UnitPrice(List<UnitPrice> price);
	
	List<OrderDetail> listOrderDetailByProduct(Long id);
	
	int countQuantityInOrderByProduct(List<OrderDetail> orderDetail);
	
	Promotion promotionApply(List<Promotion> promotion);
	
	List<OrderDetailDTO> listOrderDetailByOrder(Long id);
	
	Map<String,String> updateStatus(Long id, Long statusId);
	
//	List<InfoOrderRecentDTO> listOrderRecentCustomer(Authentication auth);
//	
//	InfoOrderRecentDTO OrderDetailCustomer(Long id,Authentication auth) throws Exception;
	
	List<OrderAdminDTO> orderByStore(Authentication auth);
	
	//Thông tin trong file account
	List<OrderGroupResponseDTO> orderOfCustomer(Authentication auth);
	Page<OrderGroupResponseDTO> allOrderOfCustomer(Authentication auth, int page);
	
	OrderGroupResponseDTO orderDetailOfCustomer(Authentication auth,Long id) throws Exception;
	
	Map<String,String> saveOrderVersion2(OrderGroupDTO orderGroupDTO,Authentication auth) throws Exception;

	

	List<ChartMonthByYearDTO> chartByMonthOfYearAdmin(int Year);
	
	List<ChartMonthByYearDTO> chartMonthAdmin(int Year, int month);
	
	float sumOrderAdmin();
	float donHangDaDuyet();
	
	List<ChartMonthByYearDTO> chartByMonthOfYearByStore(int year,Authentication auth);
	
	List<ChartMonthByYearDTO> chartMonthStore(int Year, int month, Authentication auth);
	
	float sumOrderStore(Authentication auth);
	float donHangDaDuyetStore(Authentication auth);
	
	List<RoleUserDTO> thongKeTrangThaiStore(Authentication auth);
	
	void huyDonHang(Long id, Authentication auth) throws Exception;
	
	OrderGroupResponseDTO orderGroupPageEmail(Long id) ;
}
