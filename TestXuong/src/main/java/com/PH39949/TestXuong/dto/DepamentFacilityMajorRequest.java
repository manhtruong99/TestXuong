package com.PH39949.TestXuong.dto;

import com.PH39949.TestXuong.entity.Department;
import com.PH39949.TestXuong.entity.Facility;
import com.PH39949.TestXuong.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepamentFacilityMajorRequest {
    private String idDepartment;
    private String idFacility;
    private String idMajor;
}
