//실행되자마자 window가 로드될때 즉시 실행//
//(function(){   함수명()  })();

//익명
//(()=>{  함수명() })()

//(()=>{
//  replyList();
//})();

//즉시실행함수는 끝에 ; 안하면 에러
//////////////////////////////////////

function replyList(){
//reply_id는 가져올수 없음
//board_id를 가지고 board_tb1의 덧글을 가져와야
//select * from reply_tb1 where board_id=2;
// replyRepository.findAllByBoardEntity 와 같음
const boardId=$('input#id').val(); //id는 #으로 가져옴
alert(boardId);

  $.ajax({
    type: 'get',
    url:"/reply/replyList/"+boardId,
    success:function(res){
    alert(`응답 : ${res}`);

    },
    error:function(){

    }
  });

}

//id가 replyBtn
const replyBtn=$('#replyBtn');

//replyBtn을 클릭하면 ajaxWrite함수를 실행해라
replyBtn.on('click' ,ajaxWrite);
function ajaxWrite(){

  const boardId=$('#boardId').val();
  const replyContent=$('#replyContent').val();
  const replyWriter=$('#replyWriter').val();
  console.log(`ajaxwrite함수 호출`);

const dataVal={
//왼쪽은 dto와 일치
'boardId':boardId,
'replyContent':replyContent,
'replyWriter':replyWriter


};
console.log(dataVal);

$.ajax({
  type: 'post',
  url: '/reply/ajaxWrite',
  data: dataVal, //data type form
  success:function(res){
      alert(`덧글 작성 success`);  //서버에서 응답
      console.log(`게시글 번호: ${res.boardEntity.id}`)
      console.log(`덧글 번호: ${res.id}`)
      console.log(`덧글 내용: ${res.replyContent}`)
      console.log(`덧글 작성자: ${res.replyWriter}`)
      console.log(`덧글 작성시간: ${res.createTime}`)
      console.log(`덧글 수정시간: ${res.updateTime}`)
      let deleteUrl = `/reply/boardReplyDelete/${res.id}`;

      let htmlData=`<tr>
                       <td>${res.boardEntity.id}</td>
                       <td>${res.id}</td>
                       <td>${res.replyContent}</td>
                       <td>${res.replyWriter}</td>
                       <td>${res.createTime}</td>
                        <td><a href="${deleteUrl}">삭제</a></td>
                    </tr>`;

      $(".tData").append(htmlData);

      },
      error:function(){
       alert("fail!")
      }
});

}


/*
 $(document).ready(function() {
      const myUserDetails = */
/*[[${myUserDetails}]]*//*
 {}; // JSON 데이터로 변환된 사용자 정보
      $('#replyWriter').val(myUserDetails.name);
    });*/
