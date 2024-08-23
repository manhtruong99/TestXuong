$(document).ready(function () {
    let pathArray = window.location.pathname.split('/');

    let staffId = pathArray[pathArray.length - 1];
    loadPage();

    function loadPage() {
        loadForm();
        loadTable();
        loadModal();
    }


    function loadForm() {
        $.getJSON('http://localhost:8080/staff/detail/' + staffId, function (response) {
            let staff = response.data;
            $('#staffCode').val(staff["staffCode"]);
            $('#name').val(staff["name"]);
            $('#accountFpt').val(staff["accountFpt"]);
            $('#accountFe').val(staff["accountFe"]);
        });
    }

    function loadTable() {
        $.getJSON('http://localhost:8080/staff/typical/' + staffId, function (response) {
            if (response.code !== 200) {
                return;
            }
            let list = response.data;

            let tbody = '';
            list.forEach((dcm, index) => {
                let departmentFacility = dcm["departmentFacility"];
                let department = departmentFacility["department"];
                let facility = departmentFacility["facility"];
                let major = dcm["major"];
                tbody += `
                    <tr>
                        <td>${index + 1}</td>
                        <td >${facility["name"]}</td>
                        <td >${department["name"]}</td>
                        <td >${major["name"]}</td>
                        <td >
                            <i class="fas fa-edit" style="margin-right: 10px;" data-id="${dcm['id']}" data-action="edit"></i>
                            <i class="fas fa-trash" style="margin-right: 10px;" data-id="${dcm['id']}" data-action="del"></i>
                </td>                `;
                $('#tbodyTypical').off('click').on('click', 'i[data-action]', function () {
                    let id = $(this).data('id');
                    let action = $(this).data('action');
                    let row = $(this).closest('tr');

                    switch (action) {
                        case 'edit':
                            window.location.href = "http://localhost:8080/staff/";
                            break;

                        case 'del':
                            $.ajax({
                                url: 'http://localhost:8080/staff/del-typical/' + id,
                                type: 'DELETE',
                                success: function (response) {
                                    if (response["code"] === 200) {
                                        loadPage();
                                        row.remove(); // Xóa dòng khỏi bảng mà không cần tải lại
                                    } else {
                                        alert("Có lỗi xảy ra! Code:" + response["code"] + " " + response["message"])
                                    }
                                }
                            });
                            break;
                    }
                });
            });
            $('#tbodyTypical').html(tbody);

        });
    }


    function loadModal() {
        $.getJSON('http://localhost:8080/staff/get-facility/' + staffId, function (response) {
            if (response.code !== 200) {
                return;
            }
            let deparmentFacilityList = response.data;
            let deparmentList = deparmentFacilityList["departmentList"];
            let facilityList = deparmentFacilityList["facilityList"];
            let majorList = deparmentFacilityList["majorList"];

            $('#facility').empty();
            $('#facility').append(`<option value="" disabled selected>Select a facility</option>`);
            facilityList.forEach((facility) => {
                $('#facility').append(`<option value="${facility.id}">${facility.name}</option>`);
            });

            $('#facility').change(() => {
                $('#department').prop('disabled', false);
                $('#department').empty();
                $('#department').append(`<option value="" disabled selected>Select a department</option>`);
                deparmentList.forEach((department) => {
                    $('#department').append(`<option value="${department.id}">${department.name}</option>`);
                })
            });
            $('#department').change(() => {
                $('#major').prop('disabled', false);
                $('#major').empty();
                $('#major').append(`<option value="" disabled selected>Select a major</option>`);
                majorList.forEach((major) => {
                    $('#major').append(`<option value="${major.id}">${major.name}</option>`);
                })
            })

        });

        $('#applyTypical').off('click');
        $('#applyTypical').click(function () {
            let data = {
                idDepartment: $('#department').val(),
                idFacility: $('#facility').val(),
                idMajor: $('#major').val(),
            }
            $.ajax({
                url: ' http://localhost:8080/staff/apply-typical/' + staffId,
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

});