$('#workIn').on('click', workInFn);
$('#workOut').on('click', workOutFn);


function workInFn() {

    var time = new Date();

    var month = time.getMonth();
    var date = time.getDate();
    var day = time.getDay();

    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();

    $('#workIn').text(
        `${month + 1}월 ${date}일 ` +
        `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds} 출근`);
}

function workOutFn() {
    var time = new Date();

    var month = time.getMonth();
    var date = time.getDate();
    var day = time.getDay();

    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();

    $('#workOut').text(
        `${month + 1}월 ${date}일 ` +
        `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds} 퇴근`);
}

function updateClock() {
    var now = new Date();
    var hours = now.getHours();
    var minutes = now.getMinutes();
    var seconds = now.getSeconds();

    /* 시간, 분, 초가 한 자리 숫자인 경우 앞에 0을 붙여줌 */
    hours = (hours < 10 ? "0" : "") + hours;
    minutes = (minutes < 10 ? "0" : "") + minutes;
    seconds = (seconds < 10 ? "0" : "") + seconds;

    /* HTML 요소에 시계 시간을 업데이트 */
    document.getElementById('clock').innerHTML
        = "현재시간 : " + hours + "시 " + minutes + "분 " + seconds + "초";
}

setInterval(updateClock, 1000);
document.addEventListener('DOMContentLoaded', updateClock);
