package com.luanvan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.luanvan.dto.request.OrderDTO;
import com.luanvan.dto.request.OrderGroupDTO;
import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.dto.response.ChartMonthByYearDTO;
import com.luanvan.dto.response.OrderAdminDTO;
import com.luanvan.dto.response.OrderDetailDTO;
import com.luanvan.dto.response.OrderGroupResponseDTO;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Order;
import com.luanvan.model.OrderDetail;
import com.luanvan.model.OrderGroup;
import com.luanvan.model.OrderStatus;
import com.luanvan.model.Promotion;
import com.luanvan.model.UnitPrice;
import com.luanvan.model.Users;
import com.luanvan.repo.OrderDetailRepository;
import com.luanvan.repo.OrderGroupRepository;
import com.luanvan.repo.OrderRepository;
import com.luanvan.repo.OrderStatusRepository;
import com.luanvan.repo.ProductRepository;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UnitPriceRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.OrderService;
import com.luanvan.service.SendGridMailService;

@Service
public class OrderServiceImpl implements OrderService{

	static final String URL_FORMMAIL = "http://localhost:8080/noithat246.vn/form-send-mail";
	
	private OrderRepository	orderRepository;
	private ProductRepository productRepository;
	private OrderDetailRepository orderDetailRepository;
	private UnitPriceRepository unitPriceRepository;
	private OrderStatusRepository orderStatusRepository;
	private UsersRepository userRepository;
	private StoreRepository storeRepository;
	private OrderGroupRepository orderGroupRepository;
	private SendGridMailService SendGridMailService;
	
	@Autowired
	public OrderServiceImpl(OrderRepository	orderRepository,
			ProductRepository productRepository,
			OrderDetailRepository orderDetailRepository,
			UnitPriceRepository unitPriceRepository,
			OrderStatusRepository orderStatusRepository,
			UsersRepository userRepository,
			StoreRepository storeRepository,
			OrderGroupRepository orderGroupRepository,
			SendGridMailService SendGridMailService) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.orderDetailRepository = orderDetailRepository;
		this.unitPriceRepository = unitPriceRepository;
		this.orderStatusRepository = orderStatusRepository;
		this.userRepository = userRepository;
		this.storeRepository  = storeRepository;
		this.orderGroupRepository = orderGroupRepository;
		this.SendGridMailService =  SendGridMailService;
	}

	@Override
	public Order create(Order order) {
		Date today = new Date();
		order.setCreated_at(today);;
		return orderRepository.save(order);
	}
	
	@Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }
	
	@Override
	public OrderAdminDTO findOrderById(Long id) {
		Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
		ModelMapper mapper = new ModelMapper();
		OrderAdminDTO orderDTO = mapper.map(order,OrderAdminDTO.class);
		return orderDTO;
	}
	
	@Override
	public boolean checkSaveOrder(List<OrderDTO> orderDTO) {
		ModelMapper modelMapper = new ModelMapper();
		List<Order> orders = modelMapper.map(orderDTO,new TypeToken<List<Order>>(){}.getType());
		boolean saveOrder = true;
		for(Order order : orders) {
			for(OrderDetail detail : order.getOrderDetail()) {
				int quantity = productRepository.findById(detail.getProduct().getId()).get().getQuantity();
				int quantityOrdered = countQuantityInOrderByProduct(orderDetailRepository.findByProductId(detail.getProduct().getId()));
				int checked = quantity - (quantityOrdered + detail.getQuantity());
				if(checked < 0 ) {
					saveOrder = false;
				}
			}
		}
		return saveOrder;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public synchronized Map<String,String> saveOrder(List<OrderDTO> orderDTO, Authentication auth) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		ModelMapper modelMapper = new ModelMapper();
		List<Order> orders = modelMapper.map(orderDTO,new TypeToken<List<Order>>(){}.getType());
		
		for(Order order : orders) {
			
		    // Set user login
		    Users user = userRepository.findByEmail(auth.getName());
		    order.setUser(user);
			//Set status 1
			order.setOrderStatus(orderStatusRepository.getOne((long)1));
			Order ordered = orderRepository.save(order);
			
			for( OrderDetail detail : order.getOrderDetail()){
				detail.getId().setOrderId(ordered.getId());
				List<UnitPrice> price = unitPriceRepository.findByProductIdOrderByRootAsc(detail.getProduct().getId());
				detail.setUnitPrice(UnitPriceApply(price));
				// lấy số lượng đã mua hiện tại
				int quantityOrdered = countQuantityInOrderByProduct(orderDetailRepository.findByProductId(detail.getProduct().getId()));
				// lấy số lượng gốc
				int quantity = productRepository.findById(detail.getProduct().getId()).get().getQuantity();
				//so sánh
				int checked = quantity - (quantityOrdered + detail.getQuantity());
				if(checked < 0 ) {
					throw new Exception("Có lỗi trong quá trình đặt hàng");
				}
				orderDetailRepository.save(detail);
				map.put("Success","Thanh toán thành công");
			}
		}
		return map;

	}

	@Override
	public UnitPrice UnitPriceApply(List<UnitPrice> price) {
		Long id = (long)0;
		Date today = new Date();
		UnitPrice pr = UnitPrice(price);
		for(UnitPrice unitPrice:price) {
			if(unitPrice.getRoot() != 1) {
				if(unitPrice.getStart_time().before(today)
					&& unitPrice.getEnd_time().after(today)
					&& id < unitPrice.getId() ) 
				{
					id = unitPrice.getId();
					pr = unitPrice;
				}		
			}
		}
		return pr;
	}

	@Override
	public List<Order> findAllOrder() {
		return orderRepository.findAll();
	}

	@Override
	public UnitPrice UnitPrice(List<UnitPrice> price) {
		Long id = (long)0;
		UnitPrice pr = new UnitPrice();
		for(UnitPrice unitPrice:price) {
			if(unitPrice.getId() > id
				&& unitPrice.getRoot() == 1 ) 
			{
				id = unitPrice.getId();
				pr = unitPrice;
			}		
		}
		return pr;
	}

	@Override
	public List<OrderDetail> listOrderDetailByProduct(Long id) {
		return orderDetailRepository.findByProductId(id);
	}

	@Override
	public int countQuantityInOrderByProduct(List<OrderDetail> orderDetails) {
		int dem = 0;
		for(OrderDetail orderDetail: orderDetails) {
			if(orderDetail.getOrders().getOrderStatus().getId()  <= 5) {
				dem += orderDetail.getQuantity();
			}
		}
		return dem;
	}

	@Override
	public Promotion promotionApply(List<Promotion> promotions) {
			int max = 0;
			Promotion promotionActive = new Promotion();
			Date today = new Date();
				for(Promotion promotion:promotions) {
					if(promotion.getStart_time().before(today)
						&& promotion.getEnd_time().after(today)
						&& max < promotion.getSale_off()) 
					{
						promotionActive = promotion;
						max = promotion.getSale_off();
					}
							
				}
				
		return promotionActive;
	}

	@Override
	public List<OrderDetailDTO> listOrderDetailByOrder(Long id) {
		List<OrderDetail> orderDetails = orderDetailRepository.findByOrdersId(id);
		ModelMapper modelMapper = new ModelMapper();
		List<OrderDetailDTO> orders = modelMapper.map(orderDetails,new TypeToken<List<OrderDetailDTO>>(){}.getType());
		return orders;
	}

	@Override
	public Map<String, String> updateStatus(Long id, Long statusId) {
		Map<String, String> map = new HashMap<String, String>();
		Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
		if( statusId == 2  && check(order.getOrderGroup().getId()) == false) {
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(URL_FORMMAIL+"?order="+order.getOrderGroup().getId(), String.class);
			SendGridMailService.sendHTML(order.getUser().getEmail().toString(), "Xác nhận đơn hàng của bạn Nội Thất 246", result);
		}
		OrderStatus orderStatus = orderStatusRepository.findById(statusId).get();
		order.setOrderStatus(orderStatus);
		orderRepository.save(order);
		map.put("Success", "Cập nhật trạng thái thành công");	
		return map;
	}
	
	public boolean check(Long id) {
		List<Order> orders = orderRepository.findByOrderGroupId(id);
		for(Order order : orders) {
			if(order.getOrderStatus().getId() > 1)
				return true;
		}
		return false;
	}

	@Override
	public List<Order> findOrderNotInId(Long id) {
		return orderRepository.findByOrderStatusIdNotIn(id);
	}

//	@Override
//	public List<InfoOrderRecentDTO> listOrderRecentCustomer(Authentication auth) {
//		Long userid = userRepository.findByEmail(auth.getName()).getId();
//		List<Order> orders = orderRepository.findByUserId(userid);
//		ModelMapper modelMapper = new ModelMapper();
//		List<InfoOrderRecentDTO> orderDTO = modelMapper.map(orders,new TypeToken<List<InfoOrderRecentDTO>>(){}.getType());
//		return orderDTO.stream()
//				.limit(5).collect(Collectors.toList());
//	}
//
//	@Override
//	public InfoOrderRecentDTO OrderDetailCustomer(Long id, Authentication auth) throws Exception {
//		Long userid = userRepository.findByEmail(auth.getName()).getId();
//		Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
//		if(order.getUser().getId()!= userid) {
//			throw new Exception("Không tồn tại đơn hàng này");
//		}
//		ModelMapper mapper = new ModelMapper();
//		InfoOrderRecentDTO orderDTO = mapper.map(order,InfoOrderRecentDTO.class);
//		return orderDTO;
//	}

	
	//List Order By Store
	@Override
	public List<OrderAdminDTO> orderByStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		List<Order> orders = orderRepository.findByStoresId(storeid);
		ModelMapper modelMapper = new ModelMapper();
		List<OrderAdminDTO> orderDTO = modelMapper.map(orders,new TypeToken<List<OrderAdminDTO>>(){}.getType());
		return orderDTO;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public synchronized Map<String,String> saveOrderVersion2(OrderGroupDTO orderGroupDTO, Authentication auth) throws Exception {
		
	    Map<String, String> map = new HashMap<String, String>();
	    // Set user login
	    Users user = userRepository.findByEmail(auth.getName());
	    
		ModelMapper modelMapper = new ModelMapper();
		OrderGroup orderGroup = modelMapper.map(orderGroupDTO.getOrderGroup(),OrderGroup.class);
		orderGroup.setUser(user);
		OrderGroup orderGrouped = orderGroupRepository.save(orderGroup);

		List<Order> orders = modelMapper.map(orderGroupDTO.getOrderDTO(),new TypeToken<List<Order>>(){}.getType());
		
		for(Order order : orders) {
		    order.setUser(user);
			//Set status 1
		    if(order.getPaymentType().getId() != 1) {
		    	order.setOrderStatus(orderStatusRepository.getOne((long)4));
		    }
		    else {
		    	order.setOrderStatus(orderStatusRepository.getOne((long)1));
		    }
			order.setOrderGroup(orderGrouped);
			Order ordered = orderRepository.save(order);
			
			for( OrderDetail detail : order.getOrderDetail()){
				detail.getId().setOrderId(ordered.getId());
				List<UnitPrice> price = unitPriceRepository.findByProductIdOrderByRootAsc(detail.getProduct().getId());
				detail.setUnitPrice(UnitPriceApply(price));
				// lấy số lượng đã mua hiện tại
				int quantityOrdered = countQuantityInOrderByProduct(orderDetailRepository.findByProductId(detail.getProduct().getId()));
				// lấy số lượng gốc
				int quantity = productRepository.findById(detail.getProduct().getId()).get().getQuantity();
				//so sánh
				int checked = quantity - (quantityOrdered + detail.getQuantity());
				if(checked < 0 ) {
					throw new Exception("Có lỗi trong quá trình đặt hàng");
				}
				orderDetailRepository.save(detail);
				map.put("Success","Thanh toán thành công");
				
			}
		}
		return map;

	}

	@Override
	public List<OrderGroupResponseDTO> orderOfCustomer(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		List<OrderGroup> orders = orderGroupRepository.findByUserIdOrderByIdDesc(userid);
		ModelMapper modelMapper = new ModelMapper();
		List<OrderGroupResponseDTO> orderDTO = modelMapper.map(orders,new TypeToken<List<OrderGroupResponseDTO>>(){}.getType());
		return orderDTO.stream()
				.limit(5).collect(Collectors.toList());
	}

	@Override
	public OrderGroupResponseDTO orderDetailOfCustomer(Authentication auth, Long id) throws Exception {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		OrderGroup orderGroup = orderGroupRepository.findById(id).orElseThrow(NotFoundException::new);
		if(orderGroup.getUser().getId()!= userid) {
			throw new Exception("Không tồn tại đơn hàng này");
		}
		ModelMapper mapper = new ModelMapper();
		OrderGroupResponseDTO orderDTO = mapper.map(orderGroup,OrderGroupResponseDTO.class);
		
		return orderDTO;
	}
	
	@Override
	public Page<OrderGroupResponseDTO> allOrderOfCustomer(Authentication auth, int page) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		Pageable sorted =  PageRequest.of(pageminus, 5);
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Page<OrderGroup> orders = orderGroupRepository.findByUserIdOrderByIdDesc(userid,sorted);
		Page<OrderGroupResponseDTO> orderDTOS = orders.map(new Function<OrderGroup, OrderGroupResponseDTO>() {
		    @Override
		    public OrderGroupResponseDTO apply(OrderGroup entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	OrderGroupResponseDTO orderDTO = mapper.map(entity,OrderGroupResponseDTO.class);
		        return orderDTO;
		    }
		});
		return orderDTOS;
	}

	@Override
	public List<ChartMonthByYearDTO> chartByMonthOfYearAdmin(int year) {
		return orderRepository.findByOrderStatusId(year,(long)5);
	}

	@Override
	public List<ChartMonthByYearDTO> chartByMonthOfYearByStore(int year, Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return orderRepository.findByStoresIdOrderStatusId(year, storeid, (long)5);
	}

	@Override
	public List<ChartMonthByYearDTO> chartMonthAdmin(int year, int month) {
		return orderRepository.chartMonth(year, month, (long)5);
	}

	@Override
	public List<ChartMonthByYearDTO> chartMonthStore(int year, int month, Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return orderRepository.chartMonthByStoresId(year, month, storeid, (long)5);
	}

	@Override
	public float sumOrderStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return orderRepository.sumOrderStoresId(storeid, (long)5);
	}

	@Override
	public float sumOrderAdmin() {
		return orderRepository.sumOrder((long)5);
	}

	@Override
	public float donHangDaDuyet() {
		return orderRepository.donHangDaDuyet((long)5);
	}

	@Override
	public float donHangDaDuyetStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return orderRepository.donHangDaDuyetStore(storeid, (long)5);
	}

	@Override
	public List<RoleUserDTO> thongKeTrangThaiStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return orderRepository.thongKeTrangThaiDonHang(storeid);
	}

	@Override
	@Transactional
	public void huyDonHang(Long id, Authentication auth) throws Exception {
		
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		
		OrderGroup orderGroup = orderGroupRepository.getOne(id);
		if(orderGroup.getUser().getId() == userid) {
			List<Order> orders = orderRepository.findByOrderGroupId(orderGroup.getId());
			OrderStatus orderStatus = orderStatusRepository.getOne((long)6);
			orders.stream().forEach(order->{
				order.setOrderStatus(orderStatus);
			});
			orderRepository.saveAll(orders);
		}
		else throw new Exception("Có lỗi trong quá trình đặt hàng");
		
	}

	@Override
	public OrderGroupResponseDTO orderGroupPageEmail(Long id) {
		OrderGroup orderGroup = orderGroupRepository.findById(id).orElseThrow(NotFoundException::new);
		ModelMapper mapper = new ModelMapper();
		OrderGroupResponseDTO orderDTO = mapper.map(orderGroup,OrderGroupResponseDTO.class);
		
		return orderDTO;
	}
	
	
	
	
	
}
