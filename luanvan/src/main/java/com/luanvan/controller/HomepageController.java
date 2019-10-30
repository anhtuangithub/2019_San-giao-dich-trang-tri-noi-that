package com.luanvan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luanvan.repo.CategoryRepository;
import com.luanvan.repo.MaterialRepository;
import com.luanvan.repo.MemberTypeRepository;
import com.luanvan.repo.OriginRepository;
import com.luanvan.repo.ProducerRepository;
import com.luanvan.restcontroller.CategoryController;
import com.luanvan.service.ISecurityUserService;
import com.luanvan.service.OrderService;
import com.luanvan.service.ProductService;

@Controller
@RequestMapping("noithat246.vn")
public class HomepageController {

	@Autowired
	private  MemberTypeRepository memberTypeRepository;
	
	@Autowired
	private  OriginRepository OriginRepository;
	
	@Autowired
	private  ProducerRepository ProducerRepository;
	
	@Autowired
	private  MaterialRepository MaterialRepository;
	
	@Autowired
	private CategoryController CategoryController;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductService productSevice;
	
	@Autowired
	private OrderService  orderService;
	
	@Autowired
	private ISecurityUserService securityUserService;
	
	@GetMapping()
	public String index() {
		return "homepage/index";
	}
	
	@GetMapping("chi-tiet-san-pham/{id}")
	public String productDetail() {
		return "homepage/product-detail";
	}
	
	@GetMapping("gio-hang")
	public String cart() {
		return "homepage/cart";
	}
	
	@GetMapping("dang-nhap")
	public String logIn() {
		return "homepage/login";
	}
	
	
	@GetMapping("dktv")
	public String submitAccount(Model model) {
		 model.addAttribute("membertypes", memberTypeRepository.findAll());
		return "homepage/dang-ki-thanh-vien";
	}
	
	@GetMapping("dktv/thong-tin")
	public String dktv(Model model, @RequestParam("the-thanh-vien") String tenthe, @RequestParam("id") Long id) {
		
		return "homepage/form-dang-ki";
	}
	
	
	@GetMapping("tai-khoan/quan-li-tai-khoan")
	public String qltk() {
		return "homepage/account-info";
	}
	
	@GetMapping("tai-khoan/order-detail-{id}")
	public String orderDetail() {
		return "homepage/account-detail-order";
	}
	
	@GetMapping("tai-khoan/tat-ca-order")
	public String allOrder(@RequestParam(value ="page", required = false, defaultValue = "0") int page) {
		return "homepage/account-allorder";
	}
	
	@GetMapping("tai-khoan/thong-tin-ca-nhan")
	public String manageInfo() {
		return "homepage/account-manage-info";
	}
	
	@GetMapping("tai-khoan/danh-gia-cua-toi")
	public String accountReview(@RequestParam(value ="page", required = false, defaultValue = "0") int page) {
		return "homepage/account-review";
	}
	
	@GetMapping("tai-khoan/cau-hoi-cua-toi")
	public String accountQuestion(@RequestParam(value ="page", required = false, defaultValue = "0") int page) {
		return "homepage/account-question";
	}
	
	@GetMapping("tai-khoan/thong-tin-cua-hang")
	public String accountStore() {
		return "homepage/account-store";
	}
	
	@GetMapping("tai-khoan/thong-tin-goi-dang-ki")
	public String goiDangKiStore() {
		return "homepage/account-history-member";
	}
	
	@GetMapping("search")
	public String search(@RequestParam(value ="tukhoa", required = false, defaultValue = "") String search,
						@RequestParam(value ="category", required = false, defaultValue = "%%") String categoryId,
						@RequestParam(value ="star", required = false, defaultValue = "0") float star,
						@RequestParam(value ="producer", required = false, defaultValue = "%%") String producerid,
						@RequestParam(value ="material", required = false, defaultValue = "%%") String materialId,
						@RequestParam(value ="origin", required = false, defaultValue = "%%") String originId,
						@RequestParam(value ="page", required = false, defaultValue = "0") int page,
						@RequestParam(value ="filter", required = false, defaultValue = "") String filter,
						Model model) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		model.addAttribute("products", 
			productSevice.searchProductAdvanced
			(search, categoryId, star, producerid, materialId, originId, pageminus,filter));
		 model.addAttribute("danhmuc", categoryRepository.findByParentIdNotIn((long)0));
		 model.addAttribute("tukhoa", search);
		 model.addAttribute("materials", MaterialRepository.findAll());
		 model.addAttribute("producers", ProducerRepository.findAll());
		 model.addAttribute("origins", OriginRepository.findAll());
		 model.addAttribute("topseller", productSevice.topSeller());
		 return "homepage/search-product";
	}
	
	@GetMapping("{danhmuc}")
	public String listProductByCategory(
			@RequestParam(value="id") Long id,
			@RequestParam(value ="tukhoa", required = false, defaultValue = "") String search,
			@RequestParam(value ="id", required = false, defaultValue = "%%") String categoryId,
			@RequestParam(value ="star", required = false, defaultValue = "0") float star,
			@RequestParam(value ="producer", required = false, defaultValue = "%%") String producerid,
			@RequestParam(value ="material", required = false, defaultValue = "%%") String materialId,
			@RequestParam(value ="origin", required = false, defaultValue = "%%") String originId,
			@RequestParam(value ="page", required = false, defaultValue = "0") int page,
			@RequestParam(value ="filter", required = false, defaultValue = "") String filter,
			Model model){
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		model.addAttribute("products", 
				productSevice.searchProductAdvanced
				(search, categoryId, star, producerid, materialId, originId, pageminus,filter)); 
		model.addAttribute("danhmuchientai", categoryRepository.findById(id).get());
		model.addAttribute("materials", MaterialRepository.findAll());
		model.addAttribute("producers", ProducerRepository.findAll());
		model.addAttribute("origins", OriginRepository.findAll());
		model.addAttribute("topseller", productSevice.topSeller());
		return "homepage/list-product-category";
	}
	@GetMapping("danh-muc")
	public String listProductByCategoryParent(
			@RequestParam(value="danhmuc") String danhmuc,
			@RequestParam(value="id") Long id,
			@RequestParam(value ="category", required = false, defaultValue = "%%") String categoryId,
			@RequestParam(value ="tukhoa", required = false, defaultValue = "") String search,
			@RequestParam(value ="star", required = false, defaultValue = "0") float star,
			@RequestParam(value ="producer", required = false, defaultValue = "%%") String producerId,
			@RequestParam(value ="material", required = false, defaultValue = "%%") String materialId,
			@RequestParam(value ="origin", required = false, defaultValue = "%%") String originId,
			@RequestParam(value ="page", required = false, defaultValue = "0") int page,
			@RequestParam(value ="filter", required = false, defaultValue = "") String filter,
			Model model){
		
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		
		model.addAttribute("products", 
				productSevice.ProductByCategoryParent
				(id, producerId, materialId, originId, star, pageminus, filter,categoryId, search));
		model.addAttribute("danhmuc", CategoryController.findCategoryByParentId(id));
		model.addAttribute("danhmuchientai", categoryRepository.findById(id).get());
		model.addAttribute("materials", MaterialRepository.findAll());
		model.addAttribute("producers", ProducerRepository.findAll());
		model.addAttribute("origins", OriginRepository.findAll());
		model.addAttribute("topseller", productSevice.topSeller());
		return "homepage/list-product-category-parent";
	}
	
	@GetMapping("store/{name}")
	public String PageProductOfStore(
						@RequestParam(value ="category", required = false, defaultValue = "%%") String categoryId,
						@RequestParam(value ="star", required = false, defaultValue = "0") float star,
						@RequestParam(value ="producer", required = false, defaultValue = "%%") String producerid,
						@RequestParam(value ="material", required = false, defaultValue = "%%") String materialId,
						@RequestParam(value ="origin", required = false, defaultValue = "%%") String originId,
						@RequestParam(value ="page", required = false, defaultValue = "0") int page,
						@RequestParam(value ="store") Long storeId,
						@RequestParam(value ="filter", required = false, defaultValue = "") String filter,
						Model model) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		model.addAttribute("products", 
			productSevice.pageProductOfStore
			(categoryId, star, producerid, materialId, originId, storeId, pageminus,filter));
		 model.addAttribute("danhmuc", categoryRepository.findByParentIdNotIn((long)0));
		 model.addAttribute("materials", MaterialRepository.findAll());
		 model.addAttribute("producers", ProducerRepository.findAll());
		 model.addAttribute("origins", OriginRepository.findAll());
		 model.addAttribute("topseller", productSevice.topSeller());
		 return "homepage/product-of-store";
	}
	
	
	@GetMapping("best-seller")
	public String PageBestSeller(
						@RequestParam(value ="best") String best,
						@RequestParam(value ="category", required = false, defaultValue = "%%") String categoryId,
						@RequestParam(value ="star", required = false, defaultValue = "0") float star,
						@RequestParam(value ="producer", required = false, defaultValue = "%%") String producerid,
						@RequestParam(value ="material", required = false, defaultValue = "%%") String materialId,
						@RequestParam(value ="origin", required = false, defaultValue = "%%") String originId,
						@RequestParam(value ="page", required = false, defaultValue = "0") int page,
						@RequestParam(value ="filter", required = false, defaultValue = "") String filter,
						Model model) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		model.addAttribute("products", 
			productSevice.bestSeller
			(categoryId, star, producerid, materialId, originId, pageminus,filter));
		 model.addAttribute("danhmuc", categoryRepository.findByParentIdNotIn((long)0));
		 model.addAttribute("materials", MaterialRepository.findAll());
		 model.addAttribute("producers", ProducerRepository.findAll());
		 model.addAttribute("origins", OriginRepository.findAll());
		 model.addAttribute("topseller", productSevice.topSeller());
		 return "homepage/page-best-seller";
	}
	
	
	@GetMapping("deal-hot")
	public String listAllPromotionProduct(
			@RequestParam(value="tab") String tab,
			@RequestParam(value ="category", required = false, defaultValue = "%%") String categoryId,
			@RequestParam(value ="page", required = false, defaultValue = "0") int page,
			Model model) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		model.addAttribute("products", productSevice.listAllPromotionHasProduct(categoryId,pageminus));
		model.addAttribute("categorys", categoryRepository.findByParentIdNotIn((long)0));
		model.addAttribute("topseller", productSevice.topSeller());
		return "homepage/list-product-promotion";
	}
	
	@GetMapping("co-the-quan-tam")
	public String productRandom(
						@RequestParam(value="quantam") String quantam,
						@RequestParam(value ="tukhoa", required = false, defaultValue = "") String search,
						@RequestParam(value ="category", required = false, defaultValue = "%%") String categoryId,
						@RequestParam(value ="star", required = false, defaultValue = "0") float star,
						@RequestParam(value ="producer", required = false, defaultValue = "%%") String producerid,
						@RequestParam(value ="material", required = false, defaultValue = "%%") String materialId,
						@RequestParam(value ="origin", required = false, defaultValue = "%%") String originId,
						@RequestParam(value ="page", required = false, defaultValue = "0") int page,
						@RequestParam(value ="filter", required = false, defaultValue = "") String filter,
						Model model) {
		int pageminus = 0;
		if(page>=1) {
			pageminus = page-1;
		}
		model.addAttribute("products", 
			productSevice.pageRandomProduct
			(search, categoryId, star, producerid, materialId, originId, pageminus,filter));
		 model.addAttribute("danhmuc", categoryRepository.findByParentIdNotIn((long)0));
		 model.addAttribute("tukhoa", search);
		 model.addAttribute("materials", MaterialRepository.findAll());
		 model.addAttribute("producers", ProducerRepository.findAll());
		 model.addAttribute("origins", OriginRepository.findAll());
		 model.addAttribute("topseller", productSevice.topSeller());
		 return "homepage/page-product-random";
	}
	
	@GetMapping("form-send-mail")
	public String formSendMail(@RequestParam("order") Long id,Model model) {
		 model.addAttribute("order",orderService.orderGroupPageEmail(id));
		return "homepage/form-mail-seller";
	}
	
	@GetMapping("user/changePassword")
	public String showChangePasswordPage(Model model, 
	  @RequestParam("email") String email, @RequestParam("token") String token) {
	    String result = securityUserService.validatePasswordResetToken(email, token);
	    if (result != null) {
	        model.addAttribute("error", result);
	        return "homepage/login";
	    }
	    else {
	    	model.addAttribute("success", "xin chao");
	    	return "homepage/update-password";
	    }
	    
	}
	
}
