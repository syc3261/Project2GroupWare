
const memberIdInput = document.querySelector('#id');
const addressInput = document.querySelector('#address');
const updateAddress = document.querySelector('#updateAddress');
//주소변경 버튼 눌렀을때
function updateAddressBtnFn(event) {
      updateAddress.style.display = 'block';
    }

//우편번호찾기
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }


            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}
//합쳐진 주소 저장
    const addressInput11 = document.getElementById("sample6_address");
    const detailAddressInput = document.getElementById("sample6_detailAddress");
    const combinedAddressInput = document.getElementById("address");

    // 주소와 상세주소가 변경될 때마다 합쳐진 주소를 업데이트합니다.
    addressInput.addEventListener("input", updateCombinedAddress);
    detailAddressInput.addEventListener("input", updateCombinedAddress);

    function updateCombinedAddress() {
        const address = addressInput11.value.trim();
        const detailAddress = detailAddressInput.value.trim();

        // 주소와 상세주소가 모두 존재하는 경우에만 합쳐진 주소를 업데이트합니다.
        if (address && detailAddress) {
            combinedAddressInput.value = `${address} ${detailAddress}`;
        } else if (address) {
            combinedAddressInput.value = address;
        } else if (detailAddress) {
            combinedAddressInput.value = detailAddress;
        } else {
            combinedAddressInput.value = ""; // 주소와 상세주소가 모두 없는 경우 빈 값으로 설정합니다.
        }
    }

//수정주소 수정값에 넣기
document.addEventListener('DOMContentLoaded', (event) => {
    const updateBtn = document.querySelector('#updateBtn');
    const combinedAddress = document.querySelector('#combinedAddress');
    const address1 = document.querySelector('#address');
    const sample6_postcode = document.querySelector('#sample6_postcode');
    const sample6_address = document.querySelector('#sample6_address');
    const sample6_detailAddress = document.querySelector('#sample6_detailAddress');

    updateBtn.addEventListener('click', (event) => {
        event.preventDefault();

        // 결합된 주소를 생성
        const fullAddress = ` ${sample6_address.value} ${sample6_detailAddress.value}`;

        // 결합된 주소를 hidden input에 설정
        combinedAddress.value = fullAddress;

        // 결합된 주소를 address1에 설정
        address1.value = combinedAddress.value;

        // 주소 입력창을 숨김
        document.getElementById('updateAddress').style.display = 'none';
    });
});



