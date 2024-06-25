function myAirplane() {
    airplane(true);
}

function todayMyAirplane() {
    airplane(false);
}

function airplane(isTrue) {
    if (isTrue) {
        $('#myAirplane-con').show();
        document.getElementById("myAirplane").style.border = '1px solid #3767a6';
        document.getElementById("myAirplane").style.color = '#3767a6';
        $('#todayMyAirplane-con').hide();
        document.getElementById("todayMyAirplane").style.border = '1px solid #ccc';
        document.getElementById("todayMyAirplane").style.color = '#ccc';

    } else {
        $('#myAirplane-con').hide();
        document.getElementById("myAirplane").style.border = '1px solid #ccc';
        document.getElementById("myAirplane").style.color = '#ccc';
        $('#todayMyAirplane-con').show();
        document.getElementById("todayMyAirplane").style.border = '1px solid #3767a6';
        document.getElementById("todayMyAirplane").style.color = '#3767a6';
    }
}