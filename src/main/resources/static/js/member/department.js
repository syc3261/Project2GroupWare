document.addEventListener('DOMContentLoaded', function () {
    var topDepartmentSelect = document.getElementById('topDepartmentSelect');
    var departmentSelect = document.getElementById('departmentSelect');

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
        }
    });
});