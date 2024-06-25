 const memberPhoneNumberInput = () => {

     let val = phone.value.replace(/\D/g, "");
     let len = val.length;
     let result = '';
     if (len < 4) {
         result = val;
     } else if (len < 8) {
         result += val.substring(0, 3);
         result += "-";
         result += val.substring(3);
     } else if (len <= 13) {
         result += val.substring(0, 3);
         result += "-";
         result += val.substring(3, 7);
         result += "-";
         result += val.substring(7, 13);
     } else {
         result = val;
     }
     phone.value = result;
 }

 function findEmail() {
                var name = $("#name").val();
                var phone = $("#phone").val();

                if (!name || !phone) {
                    alert("이름과 전화번호를 모두 입력해주세요.");
                    return;
                }

                $.ajax({
                    url: "/member/find-email",
                    type: "post",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify({ "name": name, "phone": phone }),
                    success: function(response) {
                        if (response.success) {
                            console.log(response);
                            alert("이메일: " + response.email);
                            alert("이메일 찾기에 성공했습니다. 로그인 페이지로 이동합니다.");
                            window.location.href = "/"; // 로그인 페이지로 리디렉션
                        } else {
                            alert("해당 정보로 등록된 이메일이 없습니다.");
                        }
                    },
                    error: function() {
                        alert("오류가 발생했습니다. 다시 시도해주세요.");
                    }
                });
            }

