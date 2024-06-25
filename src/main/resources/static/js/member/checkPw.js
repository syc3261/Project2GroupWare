
// 비번찾기
function findPassword() {
                var userEmail = $("#userEmail").val();
                var name = $("#name").val();

                if (!userEmail || !name) {
                    alert("이메일과 이름을 모두 입력해주세요.");
                    return;
                }

                $.ajax({
                    url: "/member/find-password",
                    type: "post",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify({ "userEmail": userEmail, "name": name }),
                    success: function(response) {
                        if (response.success) {
                            console.log(response);
                            alert("비밀번호를 찾았습니다. 안전을 위해 비밀번호를 변경해주세요");
                            window.location.href = "/member/changePw/" + userEmail; // 로그인 페이지로 리디렉션
                        } else {
                            alert("해당 정보로 등록된 사용자를 찾을 수 없습니다");
                        }
                    },
                    error: function() {
                        alert("오류가 발생했습니다. 다시 시도해주세요.");
                    }
                });
            }

function changePassword() {
            var userEmail = $("#userEmail").val();
            var name = $("#name").val();
            var newPassword = $("#newPassword").val();

            console.log($("#userEmail").val())
            console.log($("#name").val())

            if (!newPassword) {
                alert("새 비밀번호를 입력해주세요.");
                return;
            }

            $.ajax({
                url: "/member/change-password",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({ "newPassword": newPassword ,"name": name ,"userEmail": userEmail }),
                success: function(response) {
                    console.log(response); // 응답을 콘솔에 출력하여 확인
                    if (response.success) {
                        alert("비밀번호 변경에 성공했습니다. 로그인 페이지로 이동합니다.");
                        window.location.href = "/"; // 로그인 페이지로 리디렉션
                    } else {
                        alert(response.message || "비밀번호 변경에 실패했습니다.");
                    }
                },
                error: function() {
                    alert("오류가 발생했습니다. 다시 시도해주세요.");
                }
            });
        }