const data = {}; // 전송 데이터
let webSocket; // 웹소켓 URL
const userId = document.querySelector('#userId'); // 접속자
const connectBtn = document.querySelector('#connectBtn'); // 접속이벤트
const sendBtn = document.querySelector('#sendBtn'); //메시지전송
const chatWindowCon = document.querySelector('.chatWindow-con'); // 채팅창
const msg = document.querySelector('#msg'); // 전송보낼 메세지
const id = document.querySelector('#id').val; // 전송보낼 메세지


const url = "localhost:8095";


// 1. 웹소켓 접속
connectBtn.addEventListener('click', () => {
    // 웹소켓 접속
    webSocket = new WebSocket("ws://" + url + "/chat");
    console.log(webSocket);
    if (userId.value.length <= 0 || userId.value == "") {
        alert("접속자를 입력하세요");
        userId.focus();
        return false;
    }
    alert(userId.value + "님 접속했습니다.");
    // 메시지
    webSocket.onmessage = function (msg) {
        // 소켓 메시지 수신
        const data = JSON.parse(msg.data);

        // div 생성
        const divTag = document.createElement('div');
        let className;
        // 전송 아이디와 채팅 대상이 같으면
        if (data.userId == userId.value) {
            // 본인글이면
            className = "chat-main";
        } else {
            // 본인글이 아니면
            className = "chat-sub"
        }
        console.log(data.date + " <<")
        let item;
        item = "<div class= '" + className + "' >";
        item += "<p><span> [작성자] " + data.userId + " </span> <span> [작성시간] " + data.date + " </span></p>";
        item += "<p><span> 내용: " + data.msg + "</span></p>";
        item += "</div>";
        divTag.innerHTML = item; // item -> 생성한 div 태그에 추가
        chatWindowCon.append(divTag); // 생성된 div 태그를 chatWindowCon 뒤에 순서대로 추가
    }
});


// 1. 메시지 전송
sendBtn.addEventListener('click', () => {
    sendMessageFn();
});


function sendMessageFn() {
    if (msg.value.trim().length >= 0) {
        // const data = {};
        // 소켓 서버에 전송 data
        data.userId = userId.value; // 작성자
        data.msg = msg.value; // 메시지
        data.date = new Date().toLocaleDateString(); // 시간

        let sendData = JSON.stringify(data); // JSON -> String 으로 변환
        webSocket.send(sendData); // data 전송
    }
}