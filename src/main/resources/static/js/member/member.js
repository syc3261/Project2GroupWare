//비밀번호
$('#showPw').on('click', ShowPwFn);

function ShowPwFn() {
    if ($("#userPw").attr("type") == "password") {
        $("#userPw").attr("type", "text");
    } else {
        $("#userPw").attr("type", "password");
    }
}
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

