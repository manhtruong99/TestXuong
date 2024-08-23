package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.DepartmentFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentFacilityRepository extends JpaRepository<DepartmentFacility,String> {

    @Query("select a from DepartmentFacility a where a.staff.id =:id ")
    List<DepartmentFacility> findByStaffId(@Param("id") String id);


    @Query("select a from DepartmentFacility a where a.staff.id=:id")
    List<DepartmentFacility> findByStaff(@Param("id") String id);
}
