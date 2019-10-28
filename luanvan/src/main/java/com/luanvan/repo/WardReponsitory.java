package com.luanvan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luanvan.model.Ward;

@Repository
public interface WardReponsitory extends JpaRepository<Ward, String>{
	List<Ward> findBywardid(String wardid);
	List<Ward> findByDistrictid(String id);
	@Query(value = "select * from ward where ward.districtid = :district",nativeQuery=true)
	List<Ward> showByDistrict(@Param("district") String district);
}
