package com.PH39949.TestXuong.repository;

import com.PH39949.TestXuong.entity.ImportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportRepository extends JpaRepository<ImportHistory,String> {
}
