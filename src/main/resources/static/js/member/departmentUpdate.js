
function updateDepartmentBtnFn(event) {
      updateDepartment.style.display = 'flex';
    }


document.addEventListener('DOMContentLoaded', function () {
   var departmentSelect = document.getElementById('departmentSelect');
    var departmentIdInput = document.getElementById('departmentId');
    var departmentNameInput = document.getElementById('departmentName');

    topDepartmentSelect.addEventListener('change', function () {
        var topDepartmentId = this.value;
        departmentSelect.innerHTML = ''; // 부서 선택 옵션 초기화

        if (topDepartmentId) {
            $.ajax({
                type: 'GET',
                url: '/department/subDepartments',
                data: {
                    topDepartmentId: topDepartmentId
                },
                success: function (data) {
                    // 부서 선택 옵션 추가
                    var defaultOption = document.createElement('option');
                    defaultOption.value = '';
                    defaultOption.textContent = '부서를 선택하세요';
                    departmentSelect.appendChild(defaultOption);

                    data.forEach(function (department) {
                        var option = document.createElement('option');
                        option.value = department.id;
                        option.textContent = department.departmentName;
                        departmentSelect.appendChild(option);
                    });

                    // 선택된 하위 부서 값 변경
              departmentSelect.addEventListener('change', function () {
                   var selectedDepartment = departmentSelect.options[departmentSelect.selectedIndex];
                   var selectedDepartmentId = departmentSelect.options[departmentSelect.selectedIndex].value;
                   console.log(selectedDepartmentId);
                   departmentNameInput.value = selectedDepartment.textContent;
                   departmentIdInput.value = selectedDepartmentId;
                  });
                },
                error: function () {
                    console.error('하위 부서를 가져오는 동안 오류가 발생했습니다.');
                }
            });
        } else {
            // 상위 부서가 선택되지 않은 경우
            var defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = '부서를 선택하세요';
            departmentSelect.appendChild(defaultOption);

            // 선택된 하위 부서 값 초기화
            departmentNameInput.value = '';
        }
    });
});



