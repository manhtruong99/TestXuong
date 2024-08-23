package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MajorRepository extends JpaRepository<Major, String> {
    @Query("select a from Major a where a.name=:name")
    Major findByName(@Param("name") String name);
}
