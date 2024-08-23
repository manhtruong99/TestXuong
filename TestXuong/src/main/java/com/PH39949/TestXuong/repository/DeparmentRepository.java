package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeparmentRepository extends JpaRepository<Department,String> {
    @Query("select a from Department a where a.name=:name")
    Department finByName(@Param("name") String name);
}
