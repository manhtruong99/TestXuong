package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.StaffMajorFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffMajorFacilityREpository extends JpaRepository<StaffMajorFacility, Integer> {
    @Query("select a from StaffMajorFacility a where a.majorFacility.id =:id")
    StaffMajorFacility findByStaffId(@Param("id") String id);
}
