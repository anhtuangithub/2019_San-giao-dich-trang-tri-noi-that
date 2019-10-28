package com.luanvan.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luanvan.model.Category;
import com.luanvan.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategorysId(Long id);
	List<Product> findByCategorysIdAndStatus(Long id, int status);
	
	Page<Product> findByCategorysIn(List<Category> categories,Pageable pageable);
	
	// find product where in list id
	List<Product> findByIdIn(List<Long> id);
	
	List<Product> findByStoresId(Long storeid);
	
	@Query(value = "Select * from product where product.status = 1  order by rand()", nativeQuery=true)
	List<Product> ProductRandom();
	
	Page<Product> findByCategorysId(Long id,Pageable pageable);
	Page<Product> findByPlugContaining(String plug,Pageable pageable);
	
	@Query(value = "select pro from product pro join pro.unitPrices pri on pri.product.id = pro.id" + 
			" left join pro.orderDetail orD "+
			" where" + 
				" pri.id" + 
				" in" + 
					" (select price.id from unitprice price WHERE price.end_time > CURRENT_DATE AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.categorys.id in (select cate.id from category cate where cate.parentId = :category )" + 
				" and pro.plug like %:plug% " +
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :categoryChild " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
			" or pri.id in " + 
					" (select price.id from unitprice price where price.product.id not in (select price.product.id from unitprice price WHERE price.end_time > CURRENT_DATE AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.plug like %:plug% " +
				" and pro.status = 1 " +
				" and pro.categorys.id in (select cate.id from category cate where cate.parentId = :category )" + 
				" and CAST(pro.categorys.id as string) like :categoryChild " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
				" group by price.product.id having max(price.id) > 0)"+
			"group by pro.id ")
	Page<Product> pageSearchParentCategory(@Param("plug") String plug ,@Param("category")Long category, @Param("origin") String origin, @Param("material") String material, @Param("star") float star, @Param("producer") String producer,@Param("categoryChild") String categoryChild, Pageable pageable);
	
	
	@Query(value = "select pro from product pro join pro.unitPrices pri on pri.product.id = pro.id "+
			" left join pro.orderDetail orD" +
			" left join pro.promotions ppro "+
			" where" + 
				" pri.id in" + 
					" (select price.id from unitprice price WHERE price.end_time > CURRENT_DATE  AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.plug like %:plug% " +
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
			" or pri.id in " + 
					" (select price.id from unitprice price where price.product.id not in (select price.product.id from unitprice price WHERE price.end_time > CURRENT_DATE AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.plug like %:plug% " +
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
				" group by price.product.id having max(price.id) > 0)" +
			"group by pro.id ")
	Page<Product> pageSearchProduct(@Param("plug") String plug ,@Param("category")String category, @Param("origin") String origin, @Param("material") String material, @Param("star") float star, @Param("producer") String producer, Pageable pageable);
	
	
	@Query(value = "select * from product "
			+ " join product_promotion on product_promotion.product_id = product.id "
			+ " join promotion on promotion.id = product_promotion.promotion_id "
			+ " join category on category.id = product.category_id "
			+ " where promotion.start_time < CURRENT_DATE and promotion.end_time > CURRENT_DATE and product.status =1 "
			+ " and category.id like :category"
			+ " group by product.id ",nativeQuery = true)
	Page<Product> pagesAllPromotionProduct(@Param("category")String category,Pageable pageable);
	
	@Query(value = "select pro from product pro join pro.unitPrices pri on pri.product.id = pro.id" + 
			" left join pro.orderDetail orD " +
			" where" + 
				" pri.id" + 
				" in" + 
					" (select price.id from unitprice price WHERE price.end_time > CURRENT_DATE  AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
				" and pro.stores.id = :store " +
			" or pri.id in " + 
					" (select price.id from unitprice price where price.product.id not in (select price.product.id from unitprice price WHERE price.end_time > CURRENT_DATE AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
				" and pro.stores.id = :store " +
				" group by price.product.id having max(price.id) > 0)"+
			" group by pro.id ")
	Page<Product> pageProductStore(@Param("category")String category, @Param("origin") String origin, @Param("material") String material, @Param("star") float star, @Param("producer") String producer,@Param("store") Long store ,Pageable pageable);
	
	@Query(value = "select pro from product pro join pro.unitPrices pri on pri.product.id = pro.id "+
			" left join pro.orderDetail orD " + 
			" where" + 
				" pri.id" + 
				" in" + 
					" (select price.id from unitprice price WHERE price.end_time > CURRENT_DATE  AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
			" or pri.id in " + 
					" (select price.id from unitprice price where price.product.id not in (select price.product.id from unitprice price WHERE price.end_time > CURRENT_DATE AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
				" group by price.product.id having max(price.id) > 0 )"+
			" group by pro.id"+
			" order by count(orD.product.id) desc")
	Page<Product> bestSellerProduct(@Param("category")String category, @Param("origin") String origin, @Param("material") String material, @Param("star") float star, @Param("producer") String producer, Pageable pageable);
	
	
	@Query(value = "select pro from product pro join pro.unitPrices pri on pri.product.id = pro.id" + 
			" left join pro.orderDetail orD " +
			" where" + 
				" pri.id" + 
				" in" + 
					" (select price.id from unitprice price WHERE price.end_time > CURRENT_DATE  AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.plug like %:plug% " +
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
			" or pri.id in " + 
					" (select price.id from unitprice price where price.product.id not in (select price.product.id from unitprice price WHERE price.end_time > CURRENT_DATE AND price.start_time < CURRENT_DATE GROUP by price.id HAVING max(price.id) > 0)" + 
				" and pro.plug like %:plug% " +
				" and pro.status = 1 " +
				" and CAST(pro.categorys.id as string) like :category " + 
				" and CAST(pro.origins.id as string) like :origin " + 
				" and CAST(pro.materials.id string) like :material " + 
				" and pro.avgstart >= :star " +
				" and CAST(pro.producers.id as string) like :producer " +
				" group by price.product.id having max(price.id) > 0)"+
			" group by pro.id "+
			" order by rand()")
	Page<Product> pageRandomProduct(@Param("plug") String plug ,@Param("category")String category, @Param("origin") String origin, @Param("material") String material, @Param("star") float star, @Param("producer") String producer, Pageable pageable);
	
}
