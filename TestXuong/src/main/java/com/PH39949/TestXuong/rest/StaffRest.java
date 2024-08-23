package com.PH39949.TestXuong.rest;

import com.PH39949.TestXuong.dto.ApiResponse;
import com.PH39949.TestXuong.dto.DepamentFacilityMajorRequest;
import com.PH39949.TestXuong.dto.DeparmentFacilityResponse;
import com.PH39949.TestXuong.entity.Staff;
import com.PH39949.TestXuong.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("staff")
public class StaffRest {

    @Autowired
    private StaffService staffService;


    @GetMapping("view-all")
    public ApiResponse<?> viewAll() {
        return staffService.getAll();
    }

    @PostMapping("add")
    public ApiResponse<?> add(@RequestBody Staff staff) {
       return staffService.addNew(staff);
    }

    @GetMapping("/details/{id}")
    public ApiResponse<?> details(@PathVariable(name = "id") String id) {
        return staffService.getOne(id);
    }

    @PutMapping("change-status/{id}")
    public ApiResponse<?> changeStatus(@PathVariable(name = "id") String id){
        return staffService.changeStt(id);
    }

    @PutMapping("change-staff/{id}")
    public ApiResponse<?> changeStaff(@PathVariable(name = "id") String id, @RequestBody Staff staff){
        return staffService.changeStaff(id, staff);
    }

    @GetMapping("detail/{id}")
    public ApiResponse<?> detailsStaff(@PathVariable(name ="id") String id){
        return staffService.getStaff(id);
    }

    @GetMapping("typical/{id}")
    public ApiResponse<?> typicalStaff(@PathVariable(name = "id") String id){
        return staffService.getTypicalStaff(id);
    }

    @DeleteMapping("del-typical/{id}")
    public ApiResponse<?> delTypicalStaff(@PathVariable(name = "id") String id){
        return staffService.delTypicalStaff(id);
    }

    @GetMapping("get-facility/{id}")
    public ApiResponse<?> getFacility(@PathVariable(name = "id") String id){
        return staffService.getFacility(id);
    }

    @PostMapping("apply-typical/{id}")
    public ApiResponse<?> applyTypicalStaff(@PathVariable(name = "id") String id, @RequestBody DepamentFacilityMajorRequest request){
        return staffService.addNewTypical(request,id);
    }

    @PostMapping("upload")
    public ApiResponse<?> uploadFile(@RequestParam("file") MultipartFile file){
        return staffService.uploadFileAddBase(file);
    }

    @GetMapping("import-history")
    public ApiResponse<?> importHistory(){
        return staffService.getAllHistory();
    }
}
