package com.PH39949.TestXuong.controller;

import com.PH39949.TestXuong.entity.Staff;
import com.PH39949.TestXuong.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class test {

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("xoidoioi")
    public List<Staff> oidoioi() {
        return staffRepository.findAll();
    }

}
