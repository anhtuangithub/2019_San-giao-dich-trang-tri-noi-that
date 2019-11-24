package com.luanvan.restcontroller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luanvan.dto.request.OrderDTO;
import com.luanvan.dto.request.OrderGroupDTO;
import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.dto.response.ChartMonthByYearDTO;
import com.luanvan.dto.response.Item;
import com.luanvan.dto.response.ListExchange;
import com.luanvan.dto.response.OrderAdminDTO;
import com.luanvan.dto.response.OrderDetailDTO;
import com.luanvan.dto.response.OrderGroupResponseDTO;
import com.luanvan.model.Order;
import com.luanvan.model.OrderDetail;
import com.luanvan.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {
	
	private OrderService orderService;
	
	static final String dongABank = "http://dongabank.com.vn/exchange/export";
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	@GetMapping("{id}")
	public OrderAdminDTO findOrderById(@PathVariable Long id) {
		return orderService.findOrderById(id);
	}
	
	@GetMapping
	public List<Order> findAllOrder(){
		return orderService.findAllOrder();
	}
	
	
	@GetMapping("by-store")
	public List<OrderAdminDTO> orderByStore(Authentication auth){
		return orderService.orderByStore(auth);
	}
	
	// List order trừ trạng thái bị hủy
	@GetMapping("waiting")
	public List<Order> findOrderNotInId(){
		return orderService.findOrderNotInId((long)7);
	}
	
	@GetMapping("details/{id}")
	public List<OrderDetail> listOrderDetailByProduct(@PathVariable Long id){
		return orderService.listOrderDetailByProduct(id);
	}
	
	@GetMapping("list-detail-order/{id}")
	public List<OrderDetailDTO> listOrderDetailByOrder(@PathVariable Long id){
		return orderService.listOrderDetailByOrder(id);
	}
	//Order get limit 5
	@GetMapping("account-recent")
	public List<OrderGroupResponseDTO> listOrderRecentAccount(Authentication auth){
		return orderService.orderOfCustomer(auth);
	}
	
	//Order detail account
	@GetMapping("account-order-detail/{id}")
	public OrderGroupResponseDTO OrderDetailAccount(@PathVariable Long id,Authentication auth) throws Exception{
		return orderService.orderDetailOfCustomer(auth,id);
	}
	//All order Account
	@GetMapping("account-all-order")
	public Page<OrderGroupResponseDTO> AllOrderAccount(Authentication auth,@RequestParam(value ="page", required = false, defaultValue = "0") int page){
		return orderService.allOrderOfCustomer(auth,page);
	}
	
	@GetMapping("thong-ke-nam")
	public List<ChartMonthByYearDTO> ChartByAdmin(@RequestParam(value="nam") int year){
		return orderService.chartByMonthOfYearAdmin(year);
	}
	
	@GetMapping("thong-ke-thang")
	public List<ChartMonthByYearDTO> ChartMonthAdmin(@RequestParam(value="nam") int year,@RequestParam(value="thang") int month ){
		return orderService.chartMonthAdmin(year,month);
	}
	
	@GetMapping("tong-doanh-thu")
	public float sumOrderAdmin(){
		return orderService.sumOrderAdmin();
	}
	
	@GetMapping("thong-ke-trang-thai-store")
	public List<RoleUserDTO> thongKeTrangThaiStore(Authentication auth){
		return orderService.thongKeTrangThaiStore(auth);
	}
	
	@GetMapping("thong-ke-trang-thai-admin")
	public List<RoleUserDTO> thongKeTrangThaiAdmin(){
		return orderService.thongKeTrangThaiAdmin();
	}
	
	@GetMapping("don-hang-da-duyet")
	public float donHangDuyetAdmin(){
		return orderService.donHangDaDuyet();
	}
	
	@GetMapping("thong-ke-nam/store")
	public List<ChartMonthByYearDTO> ChartByStore(@RequestParam(value="nam") int year,Authentication auth){
		return orderService.chartByMonthOfYearByStore(year, auth);
	}
	
	@GetMapping("thong-ke-thang/store")
	public List<ChartMonthByYearDTO> ChartMonthByStore(@RequestParam(value="nam") int year, @RequestParam(value="thang") int month, Authentication auth){
		return orderService.chartMonthStore(year, month, auth);
	}
	
	@GetMapping("tong-doanh-thu-store")
	public float sumOrderStore(Authentication auth){
		return orderService.sumOrderStore(auth);
	}
	
	@GetMapping("don-hang-da-duyet-store")
	public float donHangDaDuyetStore(Authentication auth){
		return orderService.donHangDaDuyetStore(auth);
	}
	
	@PostMapping
	public Map<String,String> create(@RequestBody List<OrderDTO> orderDTO, Authentication auth) throws Exception{
		
		return orderService.saveOrder(orderDTO, auth);
	}
	
	@PostMapping("update-status/{id}/status/{status}")
	public Map<String, String> changeStatus(@PathVariable Long id,@PathVariable Long status){
		return orderService.updateStatus(id, status);
	}
	
	@PostMapping("orderGroup")
	public Map<String,String>  createGroup(@RequestBody OrderGroupDTO orderGroupDTO,Authentication auth) throws Exception{
		
		return orderService.saveOrderVersion2(orderGroupDTO, auth);
	}
	
	
	@DeleteMapping("huy-don-hang/{id}")
	public void huyDonHang(@PathVariable Long id, Authentication auth) throws Exception  {
		orderService.huyDonHang(id, auth);
	} 
	
	@GetMapping("exchange")
	public Item exchange() throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(dongABank, String.class);
		response = response.replace( "(" , "").replace( ")", "");
		ObjectMapper mapper = new ObjectMapper();
		ListExchange listConverted = mapper.readValue(response,ListExchange.class);
		
		for (Item item : listConverted.getItems()) {
			if(item.getType().equals("USD")) {
				return item;
			}
		}
		return null;
	}
	
	@GetMapping("ti-xoa")
	public OrderGroupResponseDTO tiXoa(@RequestParam("order") Long id) {
		return orderService.orderGroupPageEmail(id);
	}
}
