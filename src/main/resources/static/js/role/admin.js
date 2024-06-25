
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
        = hours + "시 " + minutes + "분 " + seconds + "초";
}

setInterval(updateClock, 1000);
document.addEventListener('DOMContentLoaded', updateClock);
