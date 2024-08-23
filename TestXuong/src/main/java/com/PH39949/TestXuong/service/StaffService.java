package com.PH39949.TestXuong.service;

import com.PH39949.TestXuong.dto.ApiResponse;
import com.PH39949.TestXuong.dto.DepamentFacilityMajorRequest;
import com.PH39949.TestXuong.entity.Staff;
import org.springframework.web.multipart.MultipartFile;


public interface StaffService {

    ApiResponse<?> getAll();

    ApiResponse<?> addNew(Staff staff);

    ApiResponse<?> changeStt(String id);

    ApiResponse<?> changeStaff(String id, Staff staff);

    ApiResponse<?> getStaff(String id);

    ApiResponse<?> getTypicalStaff(String id);

    ApiResponse<?> delTypicalStaff(String id);

    ApiResponse<?> getFacility(String id);

    ApiResponse<?> addNewTypical(DepamentFacilityMajorRequest request,String id);

    ApiResponse<?> uploadFileAddBase(MultipartFile file);

    ApiResponse<?> getAllHistory();

    ApiResponse<?> getOne(String id);
}
