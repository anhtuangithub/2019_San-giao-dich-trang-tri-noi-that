package com.luanvan.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.luanvan.dto.request.CartDTO;
import com.luanvan.dto.request.ProductDTO;
import com.luanvan.dto.response.ProductAdminDTO;
import com.luanvan.dto.response.ProductDetailDTO;
import com.luanvan.dto.response.ProductPromotionDTO;
import com.luanvan.model.Image;
import com.luanvan.model.Product;
import com.luanvan.model.UnitPrice;

public interface ProductService {
	
	//List Product
	List<ProductAdminDTO> findAllProduct();
	
	// Save and update Product
	Product save(ProductDTO productDTO);
	
	//Delete Product
	void delete(Long id);
	
	//Find Product by ID
	Product findProductById(Long id);
	
	//List product by category id (Check delete category)
	List<Product> findProductByCategory(Long id);
	
	//List product with promotion
	List<ProductPromotionDTO> allPromotionOfProduct();
	
	// Max promotion of product
	ProductPromotionDTO PromotionOfProductById(Long id);
	
	//List All Product by category parent id (DTO)
	Page<ProductPromotionDTO> ProductByCategoryParent(Long id,String producerid,String materialId,String originId, float star, int page,String filter, String plug, String category);
	
	//List All Product by category id (DTO)
	//Page<ProductPromotionDTO> findAllProductByCategory(Long id,float star,String producerid,String materialId,String originId,int page, String filter);
	
	//Detail Product
	ProductDetailDTO findProductDetailDTO(Long id);
	
	// List Product where in list id in cart
	List<ProductPromotionDTO> findProductInId(List<CartDTO> cartDTO);
	
	//Add product
	ResponseEntity<?> newProduct(String product,String unitPrice,String priceRoot, MultipartFile uploadfile, Authentication auth);
	
	//update product
	ResponseEntity<?> updateProduct(String product,String unitPrice,String priceRoot ,MultipartFile uploadfile,Authentication auth);
	
	//Get price Apply for product by id
	List<UnitPrice> unitPriceApply(Long id);
	
	//Get price root for product by id
	UnitPrice PriceRoot(Long id);
	
	//List product random
	List<ProductPromotionDTO> listProductRandom();
	
	Page<ProductPromotionDTO> searchProduct(String plug,int page);
	
	Page<ProductPromotionDTO> searchProductAdvanced(
													String plug,
													String categoryId,
													float star, 
													String producerId,
													String materialId,
													String originId,
													int page,
													String filter
													);
	
	//Add product
	ResponseEntity<?> uploadImage(int product,MultipartFile[] uploadfiles, Authentication auth);
	
	//List related Product by category id (DTO)
	List<ProductPromotionDTO> findRelatedProductByCategory(Long id);
	
	Page<ProductPromotionDTO> listAllPromotionHasProduct(String categoryId, int page);
	
	List<ProductAdminDTO> allPromotionOfProductStore(Authentication auth);
	
	List<Image> allImageofPro(Long productid);
	
	ResponseEntity<?> changeStatusProduct(Long id);
	
	Page<ProductPromotionDTO> pageProductOfStore(
													String categoryId,
													float star, 
													String producerId,
													String materialId,
													String originId,
													Long storeId,
													int page,
													String filter
												);
	
	Page<ProductPromotionDTO> bestSeller(
											String categoryId,
											float star, 
											String producerId,
											String materialId,
											String originId,
											int page,
											String filter
										);
	
	Page<ProductPromotionDTO> pageRandomProduct(
												String plug,
												String categoryId,
												float star, 
												String producerId,
												String materialId,
												String originId,
												int page,
												String filter
											);
	
	void updateSttImage(List<Image> images, Long productid);
}
