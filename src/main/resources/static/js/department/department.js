// 선택한 부서의 ID
var selectedDepartmentId = document.getElementById('#topId');

// select 태그
var selectElement = document.getElementById('topDepartmentEntity');

// select 태그의 옵션 수
var optionsCount = selectElement.options.length;

// 각 옵션을 순회하며 선택한 부서와 일치하는 옵션을 찾음
for (var i = 0; i < optionsCount; i++) {
    var option = selectElement.options[i];
    if (option.value === selectedDepartmentId) {
        // 선택한 부서와 일치하는 경우 해당 옵션을 선택 상태로 설정
        option.selected = true;
        break;
    }
}

