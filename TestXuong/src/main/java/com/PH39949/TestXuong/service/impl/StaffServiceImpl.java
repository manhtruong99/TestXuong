package com.PH39949.TestXuong.service.impl;

import com.PH39949.TestXuong.dto.ApiResponse;
import com.PH39949.TestXuong.dto.DepamentFacilityMajorRequest;
import com.PH39949.TestXuong.dto.DeparmentFacilityResponse;
import com.PH39949.TestXuong.entity.*;
import com.PH39949.TestXuong.repository.*;
import com.PH39949.TestXuong.service.StaffService;
import com.PH39949.TestXuong.utils.ApiResponseCode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DepartmentFacilityRepository departmentFacilityRepository;

    @Autowired
    private MajorFacilityRepository majorFacilityRepository;

    @Autowired
    private StaffMajorFacilityREpository staffMajorFacilityRepository;

    @Autowired
    private DeparmentRepository deparmentRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private ImportRepository importRepository;

    @Override
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(ApiResponseCode.SUCCESS, staffRepository.findAll());
    }

    @Override
    public ApiResponse<?> addNew(Staff staff) {

        ApiResponseCode check = validate(staff);
        if (check != null) {
            importRepository.save(new ImportHistory(generateUUID(), check.getCode(), "Add new Staff. Valid error:"+check.getMessage(), "POST", false));
            return new ApiResponse<>(check, null);
        }
        staff.setId(generateUUID());
        staff.setStatus(true);
        staffRepository.save(staff);

        importRepository.save(new ImportHistory(generateUUID(), 200, "Add new Staff", "POST", true));
        return new ApiResponse<>(ApiResponseCode.SUCCESS, null);
    }


    @Override
    public ApiResponse<?> changeStt(String id) {
        Staff staff = staffRepository.findById(id).orElse(null);
        if (staff != null) {
            boolean stt = staff.getStatus();
            staff.setStatus(!stt);
            staffRepository.save(staff);
            importRepository.save(new ImportHistory(generateUUID(), 200, "Change status success", "PUT", true));
            return new ApiResponse<>(ApiResponseCode.SUCCESS, null);
        }
        importRepository.save(new ImportHistory(generateUUID(), ApiResponseCode.BAD_REQUEST.getCode(),"Change status fail!", "PUT", false));
        return new ApiResponse<>(ApiResponseCode.BAD_REQUEST, null);
    }

    @Override
    public ApiResponse<?> changeStaff(String id, Staff staff) {
        ApiResponseCode check = validate(staff);
        if (check != null) {
            importRepository.save(new ImportHistory(generateUUID(), check.getCode(), "Change Staff fail", "PUT", false));
            return new ApiResponse<>(check, null);
        }
        Staff staff1 = staffRepository.findById(id).orElse(null);
        if (staff1 != null) {
            staffRepository.save(staff);
            importRepository.save(new ImportHistory(generateUUID(), 200, "Change Staff Success", "PUT", true));
            return new ApiResponse<>(ApiResponseCode.SUCCESS, null);
        }
        importRepository.save(new ImportHistory(generateUUID(), ApiResponseCode.ERROR.getCode(), "Change Staff fail. Error: "+ApiResponseCode.ERROR.getMessage(), "PUT", false));
        return new ApiResponse<>(ApiResponseCode.ERROR, null);
    }

    @Override
    public ApiResponse<?> getStaff(String id) {
        Staff staff = staffRepository.findById(id).orElse(null);
        if (staff != null) {
            return new ApiResponse<>(ApiResponseCode.SUCCESS, staff);
        }
        return new ApiResponse<>(ApiResponseCode.ERROR, null);
    }

    @Override
    public ApiResponse<?> getTypicalStaff(String id) {
        List<DepartmentFacility> departmentFacilityList = departmentFacilityRepository.findByStaffId(id);
        if (departmentFacilityList.size() < 1) {
            return new ApiResponse<>(ApiResponseCode.NOT_FOUND, null);
        }
        List<MajorFacility> majorFacilityList = new ArrayList<>();

        for (DepartmentFacility der : departmentFacilityList) {
            MajorFacility majorFacility = majorFacilityRepository.findByDeparmentFacility(der.getId());
            if (majorFacility != null) {
                majorFacilityList.add(majorFacility);
            } else {
                if (majorFacilityList.size() < 1) {
                    return new ApiResponse<>(ApiResponseCode.NOT_FOUND, null);
                }
                break;
            }
        }

        return new ApiResponse<>(ApiResponseCode.SUCCESS, majorFacilityList);
    }

    @Override
    public ApiResponse<?> delTypicalStaff(String id) {
        MajorFacility majorFacility = majorFacilityRepository.findById(id).orElse(null);

        if (majorFacility != null) {
            StaffMajorFacility staffMajorFacility = staffMajorFacilityRepository.findByStaffId(majorFacility.getId());
            if (staffMajorFacility != null) {
                DepartmentFacility departmentFacility = departmentFacilityRepository.findById(majorFacility.getDepartmentFacility().getId()).orElse(null);
                staffMajorFacilityRepository.delete(staffMajorFacility);
                majorFacilityRepository.delete(majorFacility);
                departmentFacilityRepository.delete(departmentFacility);
                importRepository.save(new ImportHistory(generateUUID(), 200, "Del typical from Staff Success", "DELETE", true));
                return new ApiResponse<>(ApiResponseCode.SUCCESS, null);
            }
        }
        importRepository.save(new ImportHistory(generateUUID(), 200, "Del typical from Staff fail", "DELETE", false));
        return new ApiResponse<>(ApiResponseCode.ERROR, null);
    }

    @Override
    public ApiResponse<?> getFacility(String id) {
        List<DepartmentFacility> departmentFacilityList = departmentFacilityRepository.findByStaff(id);

        List<Major> allMajorList = majorRepository.findAll();
        List<Department> allDepartments = deparmentRepository.findAll();
        List<Facility> allFacilities = facilityRepository.findAll();
        if (departmentFacilityList.size() < 1) {
            return new ApiResponse<>(ApiResponseCode.SUCCESS, new DeparmentFacilityResponse(allDepartments, allFacilities, allMajorList));
        }
        List<String> departmentIds = departmentFacilityList.stream()
                .map(departmentFacility -> departmentFacility.getDepartment().getId())
                .collect(Collectors.toList());

        List<String> facilityIds = departmentFacilityList.stream()
                .map(departmentFacility -> departmentFacility.getFacility().getId())
                .collect(Collectors.toList());

        List<Department> departmentsNotInList = allDepartments.stream()
                .filter(department -> !departmentIds.contains(department.getId()))
                .collect(Collectors.toList());

        List<Facility> facilitiesNotInList = allFacilities.stream()
                .filter(facility -> !facilityIds.contains(facility.getId()))
                .collect(Collectors.toList());
        return new ApiResponse<>(ApiResponseCode.SUCCESS, new DeparmentFacilityResponse(departmentsNotInList, facilitiesNotInList, allMajorList));
    }

    @Override
    public ApiResponse<?> addNewTypical(DepamentFacilityMajorRequest request, String idStaff) {
        DepartmentFacility departmentFacility = new DepartmentFacility();
        Staff staff = staffRepository.findById(idStaff).orElse(null);
        if (staff != null) {

            Department department = deparmentRepository.findById(request.getIdDepartment()).orElse(null);
            Facility facility = facilityRepository.findById(request.getIdFacility()).orElse(null);
            Major major = majorRepository.findById(request.getIdMajor()).orElse(null);
            if (department != null && facility != null && major != null) {
                departmentFacility.setStaff(staff);
                departmentFacility.setDepartment(department);
                departmentFacility.setStatus(true);
                departmentFacility.setFacility(facility);
                String idDeparmentFacility = generateUUID();
                departmentFacility.setId(idDeparmentFacility);
                departmentFacilityRepository.save(departmentFacility);

                DepartmentFacility depaFaci = departmentFacilityRepository.findById(idDeparmentFacility).orElse(null);
                if (depaFaci != null) {
                    MajorFacility majorFacility = new MajorFacility();
                    majorFacility.setDepartmentFacility(depaFaci);
                    majorFacility.setStatus(true);
                    majorFacility.setMajor(major);
                    String idMajor = generateUUID();
                    majorFacility.setId(idMajor);
                    majorFacilityRepository.save(majorFacility);

                    MajorFacility majorFaci = majorFacilityRepository.findById(idMajor).orElse(null);
                    if (majorFaci != null) {
                        StaffMajorFacility staffMajorFacility = new StaffMajorFacility();
                        staffMajorFacility.setStatus(true);
                        staffMajorFacility.setMajorFacility(majorFaci);
                        staffMajorFacility.setStaff(staff);
                        staffMajorFacility.setId(generateUUID());
                        staffMajorFacilityRepository.save(staffMajorFacility);
                        importRepository.save(new ImportHistory(generateUUID(), 200, "Add new typical success", "POST", true));
                        return new ApiResponse<>(ApiResponseCode.SUCCESS, null);
                    }
                }
            }
        }
        importRepository.save(new ImportHistory(generateUUID(), 200, "Add new typical fail", "POST", false));
        return new ApiResponse<>(ApiResponseCode.ERROR, null);
    }

    @Override
    public ApiResponse<?> uploadFileAddBase(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            int skipRows = 1;
            for (int i = 0; i < skipRows; i++) {
                if (iterator.hasNext()) {
                    iterator.next();
                }
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Cell firstCell = currentRow.getCell(1);
                if (firstCell == null || firstCell.getCellType() == CellType.BLANK) {
                    break;
                }
                Staff staff = createStaffFormRow(currentRow);
                staffRepository.save(staff);
                DepamentFacilityMajorRequest request = createRequestFormRow(currentRow);
                if (request == null) {
                    importRepository.save(new ImportHistory(generateUUID(), ApiResponseCode.ERROR.getCode(), "Upload file fail", "POST", false));
                    return new ApiResponse<>(ApiResponseCode.ERROR, null);
                }
                ApiResponse<?> response = addNewTypical(request, staff.getId());
                if (response.getCode() != 200) {
                    importRepository.save(new ImportHistory(generateUUID(), ApiResponseCode.ERROR.getCode(), "Upload file fail", "POST", false));
                    return new ApiResponse<>(ApiResponseCode.ERROR, null);
                }
            }
            workbook.close();
            importRepository.save(new ImportHistory(generateUUID(), ApiResponseCode.SUCCESS.getCode(), "Upload file success", "POST", true));
            return new ApiResponse<>(ApiResponseCode.SUCCESS, null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public ApiResponse<?> getAllHistory() {
        return new ApiResponse<>(ApiResponseCode.SUCCESS, importRepository.findAll());
    }

    @Override
    public ApiResponse<?> getOne(String id) {
        Staff staff = staffRepository.findById(id).orElse(null);
        if (staff!=null){
            return new ApiResponse<>(ApiResponseCode.SUCCESS, staff);
        }
        return new ApiResponse<>(ApiResponseCode.ERROR, null);
    }

    private Staff createStaffFormRow(Row row) {
        Staff staff = new Staff();
        try {
            Cell cellStaffCode = row.getCell(1);
            Cell cellNameStaff = row.getCell(2);
            Cell cellMailFpt = row.getCell(3);
            Cell cellMailFe = row.getCell(4);

            staff.setId(generateUUID());
            staff.setStatus(true);
            staff.setStaffCode(cellStaffCode.getStringCellValue());
            staff.setName(cellNameStaff.getStringCellValue());
            staff.setAccountFpt(cellMailFpt.getStringCellValue());
            staff.setAccountFe(cellMailFe.getStringCellValue());

            return staff;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private DepamentFacilityMajorRequest createRequestFormRow(Row row) {
        DepamentFacilityMajorRequest request = new DepamentFacilityMajorRequest();
        try {
            Cell cellTypical = row.getCell(5);
            String typical = cellTypical.getStringCellValue();
            String[] parts = typical.split("-");
            Facility facility = facilityRepository.findByName(parts[0]);
            Department department = deparmentRepository.finByName(parts[1]);
            Major major = majorRepository.findByName(parts[2]);

            request.setIdDepartment(department.getId());
            request.setIdFacility(facility.getId());
            request.setIdMajor(major.getId());
            return request;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ApiResponseCode validate(Staff staff) {
        if (staff.getStaffCode().length() > 15) {
            return ApiResponseCode.MAX_CODE;
        }
        if (staffRepository.findAll().stream()
                .anyMatch(st -> st.getStaffCode().equals(staff.getStaffCode()))) {
            return ApiResponseCode.DUPLICATE_CODE;
        }
        if (staffRepository.findAll().stream()
                .anyMatch(st -> st.getAccountFe().equals(staff.getAccountFe()))) {
            return ApiResponseCode.DUPLICATE_FE;
        }
        if (staffRepository.findAll().stream()
                .anyMatch(st -> st.getAccountFpt().equals(staff.getAccountFpt()))) {
            return ApiResponseCode.DUPLICATE_FPT;
        }
        return null;
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
