package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.MajorFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorFacilityRepository extends JpaRepository<MajorFacility,String> {

    @Query("select a from MajorFacility a where a.departmentFacility.id =:id")
    MajorFacility findByDeparmentFacility(@Param("id") String id);
}
