package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, String> {
}
