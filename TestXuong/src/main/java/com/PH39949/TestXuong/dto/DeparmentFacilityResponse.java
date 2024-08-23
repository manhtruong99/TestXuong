package com.PH39949.TestXuong.dto;

import com.PH39949.TestXuong.entity.Department;
import com.PH39949.TestXuong.entity.Facility;
import com.PH39949.TestXuong.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeparmentFacilityResponse {
    private List<Department> departmentList;
    private List<Facility> facilityList;
    private List<Major> majorList;
}
