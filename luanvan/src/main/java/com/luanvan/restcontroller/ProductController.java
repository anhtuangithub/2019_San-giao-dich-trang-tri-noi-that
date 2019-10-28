package com.luanvan.restcontroller;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luanvan.dto.request.CartDTO;
import com.luanvan.dto.request.ProductDTO;
import com.luanvan.dto.response.ProductAdminDTO;
import com.luanvan.dto.response.ProductDetailDTO;
import com.luanvan.dto.response.ProductPromotionDTO;
import com.luanvan.model.Image;
import com.luanvan.model.Product;
import com.luanvan.model.UnitPrice;
import com.luanvan.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

	private ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public List<ProductAdminDTO> findAllProduct(){
		return productService.findAllProduct();
	}
	
	
	@GetMapping("store")
	public List<ProductAdminDTO> findAllProductStore(Authentication auth){
		return productService.allPromotionOfProductStore(auth);
	}
	
	
	@GetMapping("/{id}")
	public Product findProductById(@PathVariable Long id){
		return productService.findProductById(id);
	}

	@GetMapping("/promotion-products")
	public List<ProductPromotionDTO> findAllPromotionOfProduct() {
		return productService.allPromotionOfProduct();
	}
	@GetMapping("/promotion-products/{id}")
	public ProductPromotionDTO findAllPromotionOfProductById(@PathVariable Long id) {
		return productService.PromotionOfProductById(id);
	}
	
	@GetMapping("/detail-product/{id}")
	public ProductDetailDTO findProductDetailById(@PathVariable Long id) {
		return productService.findProductDetailDTO(id);
	}
	
	@GetMapping("/categories/{id}")
	public int findProductByCategoryId(@PathVariable Long id){
			int i;	
			if(!productService.findProductByCategory(id).isEmpty())
			 	i = 1;
			else i = 0;
		 return i; 
	}
	
	//	related products
	@GetMapping("/related-product/{id}")
	public List<ProductPromotionDTO> findAllProductByCategoryId(@PathVariable Long id){
		 return productService.findRelatedProductByCategory(id); 
	}
	
//	//List Product by category parent
//	@GetMapping("/category-parent-product/{id}")
//	public List<ProductPromotionDTO> findAllProductByCategoryParent(@PathVariable Long id){
//		 return productService.ProductByCategoryParent(id); 
//	}
	
	
	@GetMapping("price/{id}")
	public List<UnitPrice> priceapply(@PathVariable Long id) {
		return productService.unitPriceApply(id);
	}
	
	@GetMapping("priceRoot/{id}")
	public UnitPrice priceRoot(@PathVariable Long id) {
		return productService.PriceRoot(id);
	}
	
	@GetMapping("random-home")
	public List<ProductPromotionDTO> randomHome() {
		return productService.listProductRandom();
	}
	
	@GetMapping("search/{tukhoa}")
	public Page<ProductPromotionDTO> search(@PathVariable String tukhoa){
		return productService.searchProduct(tukhoa,0);
	}
	
	@PostMapping("/cart")
	public List<ProductPromotionDTO> productWhereInIds(@RequestBody List<CartDTO> cart){
		return productService.findProductInId(cart);
	}
	
	
	@PostMapping
	public void save(@RequestBody ProductDTO productDTO) {
		productService.save(productDTO);
	}
	
	
	@PostMapping("/uploadFile")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> uploadFile(
			@RequestParam(value = "product") String product,
			@RequestParam(value = "unitPrice", required = false, defaultValue = "null") String unitPrice,
			@RequestParam(value = "priceRoot") String priceRoot,
			@RequestParam(value = "avatar") MultipartFile uploadfile,
			Authentication auth) throws IOException {
	  return productService.newProduct(product, unitPrice,priceRoot, uploadfile, auth);
	}
	
	@PutMapping("/uploadFile")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> updateFile(
			@RequestParam(value = "product") String product,
			@RequestParam(value = "unitPrice", required = false, defaultValue = "null") String unitPrice,
			@RequestParam(value = "priceRoot") String priceRoot,
			@RequestParam(value = "avatar") MultipartFile uploadfile,
			Authentication auth) throws IOException {
	  return productService.updateProduct(product, unitPrice,priceRoot,uploadfile,auth);
	}
	
	
	
	@PostMapping("/mutiple-image")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> uploadFile(
			@RequestParam(value = "product") int product,
			@RequestParam(value = "imgs") MultipartFile[] uploadfiles,
			Authentication auth) throws IOException {
	  return productService.uploadImage(product,uploadfiles,auth);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		productService.delete(id);
	}
	
	@GetMapping("hinh-cua-san-pham/{productid}")
	public List<Image> imageOfPro(@PathVariable Long productid){
		return productService.allImageofPro(productid);
	}
	
	@GetMapping("change-status/{id}")
	public ResponseEntity<?> changeStatus(@PathVariable Long id) {
		return productService.changeStatusProduct(id);
	}
}
