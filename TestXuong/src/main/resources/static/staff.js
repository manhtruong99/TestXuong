$(document).ready(function() {
    let apiUrl = 'http://localhost:8080/staff/view-all';

    loadPage();

    function loadPage(){
        loadTable();
        addStaff();
        uploadFile();
        loadTableHistory();
        updateStaff();
    }

    function loadTable(){
        $.getJSON(apiUrl,function (response) {
            let list = response.data;

            let tbody = '';
            list.forEach((staff, index)=>{
                tbody += `<tr>
                <td>${index + 1}</td>
                <td >${staff["staffCode"]}</td>
                <td >${staff["name"]}</td>
                <td >${staff["accountFpt"]}</td>
                <td >${staff["accountFe"]}</td>
                <td >${staff["status"] ? "Đang hoạt động" : "Dừng hoạt động"}</td>
                <td >
                    <i class="fas fa-edit" style="margin-right: 10px;" data-id="${staff['id']}" data-bs-toggle="modal" data-bs-target="#staticBackdrop3" data-action="edit"></i>
                    <i class="fas fa-trash" style="margin-right: 10px;" data-id="${staff['id']}" data-action="del"></i>
                    <i class="fas fa-info-circle" data-id="${staff['id']}" data-action="detail"></i>
                </td>`;
                $('#tbodyStaff').off('click').on('click', 'i[data-action]', function() {
                    let id = $(this).data('id');
                    let action = $(this).data('action');


                    switch (action) {
                        case 'edit':
                            $.ajax({
                                url: 'http://localhost:8080/staff/details/' + id, // API endpoint để lấy thông tin nhân viên
                                type: 'GET',
                                success: function(response) {
                                    if (response.code === 200) {
                                        let staff = response.data;
                                        // Đẩy dữ liệu lên các ô input
                                        $('#idStaff').val(staff.id);
                                        $('#staffCodeUpdate').val(staff.staffCode);
                                        $('#staffNameUpdate').val(staff.name);
                                        $('#mailFptUpdate').val(staff.accountFpt);
                                        $('#mailFeUpdate').val(staff.accountFe);

                                    } else {
                                        alert("Có lỗi xảy ra! Code: " + response.code + " " + response.message);
                                    }
                                },
                                error: function(xhr, status, error) {
                                    alert("Có lỗi xảy ra khi lấy dữ liệu: " + error);
                                }
                            });
                            break;

                        case 'del':
                            $.ajax({
                                url: 'http://localhost:8080/staff/change-status/' + id,
                                type: 'PUT',
                                success: function(response) {
                                    if (response["code"] ===200){
                                        loadPage();
                                    } else {
                                        alert("Có lỗi xảy ra! Code:"+response["code"]+" "+ response["message"])
                                    }
                                }
                            });
                            break;

                        case 'detail':
                           window.location.href="http://localhost:8080/staff/"+ id;
                            break;
                    }
                });

            })
            $('#tbodyStaff').html(tbody);


        });
    }
    function loadTableHistory(){
        $.getJSON('http://localhost:8080/staff/import-history',function (response) {
            let list = response.data;

            let tbody = '';
            list.forEach((his, index)=>{
                tbody += `<tr>
                <td>${index + 1}</td>
                <td >${his["code"]}</td>
                <td >${his["message"]}</td>
                <td >${his["method"]}</td>
                <td >${his["status"] ? "Success" : "Fail"}</td>`;
            });
            $('#tbodyHistory').html(tbody);
        });
    }

    function addStaff(){
        $('#applyStaff').off('click');
        $('#applyStaff').click( () =>{
            let data = {
                staffCode: $('#staffCode').val(),
                name: $('#staffName').val(),
                accountFe: $('#mailFe').val(),
                accountFpt: $('#mailFpt').val()
            }
            $.ajax({
                url: 'http://localhost:8080/staff/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (response) {
                    if (response.code !== 200) {
                        alert(response.code + " " + response.message);
                        return;
                    }
                    loadPage();

                }
            });
        });
    }
    function uploadFile(){
        $('#fileUpload').on('change', function() {
            let file = this.files[0];
            if (file) {
                let formData = new FormData();
                formData.append('file', file);

                $.ajax({
                    url: '/staff/upload',
                    enctype: 'multipart/form-data',// URL của endpoint xử lý upload trên server
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 1000000,
                    success: function(response) {
                       if (response.code!=200){
                           alert(response.code+" "+response.message)  ;
                           return;
                       }
                       alert("Upload file success!");
                       loadPage();
                    }
                });
            }
        });
    }

    function updateStaff(){
        $('#applyStaffUpdate').off('click');
        let   id=$('#idStaff').val();
        $('#applyStaffUpdate').click(()=>{
            let data = {

                staffCode: $('#staffCode').val(),
                name: $('#staffName').val(),
                accountFe: $('#mailFe').val(),
                accountFpt: $('#mailFpt').val()
            }
            $.ajax({
                url: 'http://localhost:8080/staff/change-staff/'+id,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (response) {
                    if (response.code !== 200) {
                        alert(response.code + " " + response.message);
                        return;
                    }
                    loadPage();

                }
            });
        })

    }

});