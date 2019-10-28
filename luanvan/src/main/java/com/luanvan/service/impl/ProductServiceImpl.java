package com.luanvan.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luanvan.dto.request.CartDTO;
import com.luanvan.dto.request.ProductDTO;
import com.luanvan.dto.response.ProductAdminDTO;
import com.luanvan.dto.response.ProductDetailDTO;
import com.luanvan.dto.response.ProductPromotionDTO;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Image;
import com.luanvan.model.Product;
import com.luanvan.model.Store;
import com.luanvan.model.UnitPrice;
import com.luanvan.repo.ImageRepository;
import com.luanvan.repo.ProductRepository;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UnitPriceRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.ProductService;

@Service
public class ProductServiceImpl  implements ProductService{

	private ProductRepository productRepository;
	private UnitPriceRepository unitPriceRepository;
	private UsersRepository userRepository;
	private StoreRepository storeRepository;
	private ImageRepository imageRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository,
			UnitPriceRepository unitPriceRepository,
			UsersRepository userRepository,
			StoreRepository storeRepository,
			ImageRepository imageRepository) {
		this.productRepository = productRepository;
		this.unitPriceRepository = unitPriceRepository;
		this.userRepository = userRepository;
		this.storeRepository = storeRepository;
		this.imageRepository = imageRepository;
	}

	@Override
	public List<ProductAdminDTO> findAllProduct() {
		List<Product> product = productRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		List<ProductAdminDTO> productDetail = mapper.map(product,new TypeToken<List<ProductAdminDTO>>(){}.getType());
		return productDetail;
	}

	@Override
	@Transactional
	public Product save(ProductDTO productDTO) {
		Product product = new Product();
		product = productDTO.getProduct();
		product.setColors(productDTO.getColors());
		return productRepository.save(product); 
	}

	@Override
	public void delete(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product findProductById(Long id) {
		return productRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public List<Product> findProductByCategory(Long id) {
		return productRepository.findByCategorysId(id);
	}

	
	
	@Override
	public ProductPromotionDTO PromotionOfProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
		ModelMapper mapper = new ModelMapper();
		ProductPromotionDTO productPromotion = mapper.map(product,ProductPromotionDTO.class);
		return productPromotion;
	}

	@Override
	public List<ProductPromotionDTO> allPromotionOfProduct() {
		List<Product> product = productRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		List<ProductPromotionDTO> productPromotion = mapper.map(product,new TypeToken<List<ProductPromotionDTO>>(){}.getType());
		return productPromotion.stream()
				.filter(pro -> pro.getMaxPromotion().getId()!= null && pro.getStatus() ==1)
				.limit(10).collect(Collectors.toList());
	}

	@Override
	public ProductDetailDTO findProductDetailDTO(Long id) {
		Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
		ModelMapper mapper = new ModelMapper();
		ProductDetailDTO productDetail = mapper.map(product,ProductDetailDTO.class);
		return productDetail;
	}

	@Override
	public List<ProductPromotionDTO> findProductInId(List<CartDTO> cart) {
		List<Long> id = new ArrayList<Long>();
		cart.forEach(pro->{
			id.add(pro.getId());
		});
		List<Product> product = productRepository.findByIdIn(id);
		ModelMapper mapper = new ModelMapper();
		List<ProductPromotionDTO> productPromotion = mapper.map(product,new TypeToken<List<ProductPromotionDTO>>(){}.getType());
		cart.forEach(item->{
			productPromotion.forEach(pro ->{
				if(pro.getId() == item.getId()) {
					pro.setBuyQuantity(item.getQuantity());
				}
			});
		});
		
		return productPromotion.stream()
				.filter(pro -> pro.getStatus() == 1).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<?> newProduct(String product,String unitPrice,String priceRoot, MultipartFile uploadfile, Authentication auth) {
		try {
		    // Get the filename and build the local file path (be sure that the 
		    // application have write permissions on such directory)
			 if(!uploadfile.isEmpty()) {
				 String file = uploadfile.getOriginalFilename();
				 	Long userid = userRepository.findByEmail(auth.getName()).getId();
				    Store store = storeRepository.findByUsersId(userid);
				    String filename = store.getId() +"_"+System.currentTimeMillis()+"_"+file;
				    String directory = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\uploads\\";
				    String filepath = directory+filename;
				    ObjectMapper mapper = new ObjectMapper();
				    ProductDTO map = mapper.readValue(product, ProductDTO.class); 
				    String  plug = covertToString(map.getProduct().getName());
				    map.getProduct().setPlug(plug);
				    map.getProduct().setStores(store);
				    map.getProduct().setAvatar(filename);
				    if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				    	map.getProduct().setStatus(1);
				    }
				    
				    Product newPro = save(map);
				    
				    UnitPrice mapPriceRoot = mapper.readValue(priceRoot, UnitPrice.class);
				    mapPriceRoot.setRoot(1);
				    mapPriceRoot.setProduct(newPro);
				    unitPriceRepository.save(mapPriceRoot);
				    
				    
				    if(unitPrice.length() > 4) {
				    	 UnitPrice mapUnitPrice = mapper.readValue(unitPrice, UnitPrice.class);
				    	 mapUnitPrice.setProduct(newPro);
				    	 unitPriceRepository.save(mapUnitPrice);
				    }
				 
				    BufferedOutputStream stream =
				        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
				    stream.write(uploadfile.getBytes());
				    stream.close();
			 } 

		  }
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateProduct(String product,String unitPrice,String priceRoot, MultipartFile uploadfile, Authentication auth) {
		 try {
			  	// Get the filename and build the local file path (be sure that the 
			  	// application have write permissions on such directory)
			  		ObjectMapper mapper = new ObjectMapper();
			  		ProductDTO map = mapper.readValue(product, ProductDTO.class);
			  		
			  // File not empty when update product
			  if(!uploadfile.isEmpty()) {
				  	// Path
				  	String directory = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\uploads\\";
				  	// Delete image old
				  	String deleteFilePath = directory + map.getProduct().getAvatar();
				  	File filePath = new File(deleteFilePath);
				  	Path path =  Paths.get(deleteFilePath);
				  	
				  	//check file path exits
				  	if(Files.exists(path)) {
				  		filePath.delete();
				  	}
				  	//Get name file image new
				  	String file = uploadfile.getOriginalFilename();				  
				    Long userid = userRepository.findByEmail(auth.getName()).getId();
					Long storeid = storeRepository.findByUsersId(userid).getId();
				  
				    String filename = storeid +"_"+System.currentTimeMillis()+"_"+file;
				    String filepath = directory+filename;
				    //Set avatar  == file name;
				    map.getProduct().setAvatar(filename);
				   
				//  Save the file locally
				    BufferedOutputStream stream =
					        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
					stream.write(uploadfile.getBytes());
					stream.close();
			    }
			  
			  	String  plug = covertToString(map.getProduct().getName());
			    map.getProduct().setPlug(plug);
			    map.getProduct().setStatus(0);
			    if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			    	map.getProduct().setStatus(1);
			    }
			    save(map);
			    
			    UnitPrice mapPriceRoot = mapper.readValue(priceRoot, UnitPrice.class);
			    mapPriceRoot.setProduct(map.getProduct());
			    unitPriceRepository.save(mapPriceRoot);
			    
			    
			    if(unitPrice.length() > 4) {
			    	UnitPrice mapUnitPrice = mapper.readValue(unitPrice, UnitPrice.class);
				    mapUnitPrice.setProduct(map.getProduct());
				    unitPriceRepository.save(mapUnitPrice);
			    }
			    
			   
		  }
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		  
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public List<UnitPrice> unitPriceApply(Long id) {
		return unitPriceRepository.findByProductIdOrderByRootAsc(id);
	}

	@Override
	public UnitPrice PriceRoot(Long id) {
		Long max = (long)0;
		UnitPrice price = new UnitPrice();
		List<UnitPrice> unitPrices = unitPriceRepository.findByProductIdAndRoot(id, 1);
		for(UnitPrice unitPrice : unitPrices) {
			if(max < unitPrice.getId()) {
				max = unitPrice.getId();
				price = unitPrice;
			}
		}
		return price;
	}

	@Override
	public List<ProductPromotionDTO> listProductRandom() {
		List<Product> products = productRepository.ProductRandom();
		ModelMapper mapper = new ModelMapper();
		List<ProductPromotionDTO> productPromotion = mapper.map(products,new TypeToken<List<ProductPromotionDTO>>(){}.getType());
		return productPromotion.stream()
				.limit(10).collect(Collectors.toList());
	}
	
	public static String covertToString(String value) {
	      try {
	            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
	            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
	      } catch (Exception ex) {
	            ex.printStackTrace();
	      }
	      return null;
	}

	@Override
	public Page<ProductPromotionDTO> searchProduct(String plug, int page) {
		
		String search = covertToString(plug) ;
		Page<Product> products = productRepository.findByPlugContaining(search,PageRequest.of(page, 4));
		Page<ProductPromotionDTO> dtoPage = products.map(new Function<Product, ProductPromotionDTO>() {
		    @Override
		    public ProductPromotionDTO apply(Product entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ProductPromotionDTO productDTO = mapper.map(entity,ProductPromotionDTO.class);
		        return productDTO;
		    }
		});
		
		return dtoPage;
	}

	@Override
	public ResponseEntity<?> uploadImage(int product,MultipartFile[] uploadfiles, Authentication auth) {
		try {
//			ObjectMapper mapper = new ObjectMapper();
//	  		Product productmap = mapper.readValue(product, Product.class);
			
			Product productmap = productRepository.getOne((long)product);
			for(MultipartFile uploadedFile : uploadfiles) {
					 String file = uploadedFile.getOriginalFilename();
					 	Long userid = userRepository.findByEmail(auth.getName()).getId();
						Long storeid = storeRepository.findByUsersId(userid).getId();
					    String filename = storeid +"_"+System.currentTimeMillis()+"_"+file;
					    String directory = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\uploads\\";
					    String filepath = directory+filename;

					    Image image = new Image();
					    image.setPath(filename);
					    image.setProduct_id(productmap);
					    imageRepository.save(image);
					 
					    BufferedOutputStream stream =
					        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
					    stream.write(uploadedFile.getBytes());
					    stream.close();
				 
	        }
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
		    System.out.println(e.getMessage());
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<ProductPromotionDTO> findRelatedProductByCategory(Long id) {
		List<Product> product = productRepository.findByCategorysIdAndStatus(id,1);
		ModelMapper mapper = new ModelMapper();
		List<ProductPromotionDTO> productPromotion = mapper.map(product,new TypeToken<List<ProductPromotionDTO>>(){}.getType());
		return productPromotion.stream()
				.limit(15).collect(Collectors.toList());
	}

	@Override
	public Page<ProductPromotionDTO> searchProductAdvanced(
															String plug,
															String categoryId,
															float star, 
															String producerId,
															String materialId, 
															String originId,
															int page,
															String filter
															) 
	{
		
		String search = covertToString(plug) ;
		Pageable sorted =  PageRequest.of(page, 5);
		if(filter.equalsIgnoreCase("gia-cao")) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("gia-thap")) {
			
			Sort sort = new Sort(Sort.Direction.ASC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("hang-moi")) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "created_at");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("ban-chay")) {
			
			Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "count(orD.product.id)");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("giam-gia")) {
			
			Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "COALESCE(max(ppro.sale_off),0)");
			sorted =  PageRequest.of(page, 5,sort);
		}
		Page<Product> products = 
				productRepository.pageSearchProduct( search , categoryId, originId, materialId, star, producerId, sorted);

		Page<ProductPromotionDTO> dtoPage = products.map(new Function<Product, ProductPromotionDTO>() {
		    @Override
		    public ProductPromotionDTO apply(Product entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ProductPromotionDTO productDTO = mapper.map(entity,ProductPromotionDTO.class);
		        return productDTO;
		    }
		});
		return dtoPage;
	}
	@Override
	public Page<ProductPromotionDTO> ProductByCategoryParent(Long categoryId,String producerid,String materialId,String originId,float star,int page, String filter, String categoryChild ,String plug) {
//		List<Category> categories = categoryrepository.findByParentId(id);
		
		String search = covertToString(plug) ;
		Pageable sorted =  PageRequest.of(page, 5);
		if(filter.equalsIgnoreCase("gia-cao")) {
			Sort sort = new Sort(Sort.Direction.DESC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("gia-thap")) {
			
			Sort sort = new Sort(Sort.Direction.ASC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("hang-moi")) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "created_at");
			sorted =  PageRequest.of(page, 5,sort);
		}
		
		else if(filter.equalsIgnoreCase("ban-chay")) {
			
			Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "count(orD.product.id)");
			sorted =  PageRequest.of(page, 5,sort);
		}
		
		Page<Product> products = productRepository.pageSearchParentCategory
				( search , categoryId, originId, materialId, star, producerid, categoryChild, sorted);
		Page<ProductPromotionDTO> dtoPage = products.map(new Function<Product, ProductPromotionDTO>() {
		    @Override
		    public ProductPromotionDTO apply(Product entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ProductPromotionDTO productDTO = mapper.map(entity,ProductPromotionDTO.class);
		        return productDTO;
		    }
		});
		return dtoPage;
	}

	@Override
	public Page<ProductPromotionDTO> listAllPromotionHasProduct(String categoryId, int page) {
		Pageable sorted =  PageRequest.of(page, 1);	
		Page<Product> products = productRepository.pagesAllPromotionProduct( categoryId, sorted);
		Page<ProductPromotionDTO> dtoPage = products.map(new Function<Product, ProductPromotionDTO>() {
		    @Override
		    public ProductPromotionDTO apply(Product entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ProductPromotionDTO productDTO = mapper.map(entity,ProductPromotionDTO.class);
		        return productDTO;
		    }
		});
		return dtoPage;
	}

	@Override
	public List<ProductAdminDTO> allPromotionOfProductStore(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		List<Product> product = productRepository.findByStoresId(storeid);
		ModelMapper mapper = new ModelMapper();
		List<ProductAdminDTO> productDetail = mapper.map(product,new TypeToken<List<ProductAdminDTO>>(){}.getType());
		return productDetail;
	}

	@Override
	public List<Image> allImageofPro(Long productid) {
		return imageRepository.imageOfPro(productid);
	}

	@Override
	public ResponseEntity<?> changeStatusProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
		if(product.getStatus()==1) {
			product.setStatus(0);
		}
		else product.setStatus(1);
		productRepository.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public Page<ProductPromotionDTO> pageProductOfStore(String categoryId, float star, String producerId,
			String materialId, String originId, Long storeId, int page, String filter) {
		Pageable sorted =  PageRequest.of(page, 5);
		if(filter.equalsIgnoreCase("gia-cao")) {
			Sort sort = new Sort(Sort.Direction.DESC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("gia-thap")) {
			
			Sort sort = new Sort(Sort.Direction.ASC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("hang-moi")) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "created_at");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("ban-chay")) {
			
			Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "count(orD.product.id)");
			sorted =  PageRequest.of(page, 5,sort);
		}
		
		Page<Product> products = 
				productRepository.pageProductStore(categoryId, originId, materialId, star, producerId, storeId, sorted);

		Page<ProductPromotionDTO> dtoPage = products.map(new Function<Product, ProductPromotionDTO>() {
		    @Override
		    public ProductPromotionDTO apply(Product entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ProductPromotionDTO productDTO = mapper.map(entity,ProductPromotionDTO.class);
		        return productDTO;
		    }
		});
		return dtoPage;
	}

	@Override
	public Page<ProductPromotionDTO> bestSeller(String categoryId, float star, String producerId, String materialId,
			String originId, int page, String filter) {
		Pageable sorted =  PageRequest.of(page, 5);
		if(filter.equalsIgnoreCase("gia-cao")) {
			Sort sort = new Sort(Sort.Direction.DESC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("gia-thap")) {
			
			Sort sort = new Sort(Sort.Direction.ASC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("hang-moi")) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "created_at");
			sorted =  PageRequest.of(page, 5,sort);
		}
		
		else if(filter.equalsIgnoreCase("ban-chay")) {
			
			Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "count(orD.product.id)");
			sorted =  PageRequest.of(page, 5,sort);
		}
		Page<Product> products = 
				productRepository.bestSellerProduct(categoryId, originId, materialId, star, producerId, sorted);

		Page<ProductPromotionDTO> dtoPage = products.map(new Function<Product, ProductPromotionDTO>() {
		    @Override
		    public ProductPromotionDTO apply(Product entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ProductPromotionDTO productDTO = mapper.map(entity,ProductPromotionDTO.class);
		        return productDTO;
		    }
		});
		return dtoPage;
	}

	@Override
	public Page<ProductPromotionDTO> pageRandomProduct(String plug, String categoryId, float star, String producerId,
			String materialId, String originId, int page, String filter) {
		String search = covertToString(plug) ;
		Pageable sorted =  PageRequest.of(page, 5);
		if(filter.equalsIgnoreCase("gia-cao")) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("gia-thap")) {
			
			Sort sort = new Sort(Sort.Direction.ASC, "pri.price");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("hang-moi")) {
			
			Sort sort = new Sort(Sort.Direction.DESC, "created_at");
			sorted =  PageRequest.of(page, 5,sort);
		}
		else if(filter.equalsIgnoreCase("ban-chay")) {
			
			Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "count(orD.product.id)");
			sorted =  PageRequest.of(page, 5,sort);
		}
		Page<Product> products = 
				productRepository.pageRandomProduct( search , categoryId, originId, materialId, star, producerId, sorted);

		Page<ProductPromotionDTO> dtoPage = products.map(new Function<Product, ProductPromotionDTO>() {
		    @Override
		    public ProductPromotionDTO apply(Product entity) {
		    	ModelMapper mapper = new ModelMapper();
		    	ProductPromotionDTO productDTO = mapper.map(entity,ProductPromotionDTO.class);
		        return productDTO;
		    }
		});
		return dtoPage;
	}
	
	
	
}