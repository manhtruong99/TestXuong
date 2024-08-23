package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacilityRepository extends JpaRepository<Facility, String> {
    @Query("select a from Facility a where a.name=:name")
    Facility findByName(@Param("name") String name);
}
