
// JavaScript 코드
const memberIdInput = document.querySelector('#id');
const userPwInput = document.querySelector('#userPw');
const userEmailInput = document.querySelector('#userEmail');
const roleInput = document.querySelector('#role');
const addressInput = document.querySelector('#address');
const phoneInput = document.querySelector('#phone');
const nameInput = document.querySelector('#name');
const employeeDateInput = document.querySelector('#employeeDate');
const resignationDateInput = document.querySelector('#resignationDate');
const memberDetail = document.querySelector('#memberDetail');

function memberDetailBtnFn(event, id) {
  //event.preventDefault();
//  console.log("Member ID:", id);
  let url = "/member/memberDetail/" + id;
  fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      memberIdInput.value = data.id;
      userPwInput.value = data.userPw;
      userEmailInput.value = data.userEmail;
      roleInput.value = data.role;
      nameInput.value = data.name;
      addressInput.value = data.address;
      phoneInput.value = data.phone;
      employeeDateInput.value = data.employeeDate;
      resignationDateInput.value = data.resignationDate;

      memberDetail.style.display = 'block';
    }).catch((error) => {
      console.log(error);
    });
}

// 수정
function memberUpdateBtnFn() {

    let data = {
      id: memberIdInput.value,
      userPw: userPwInput.value,
      userEmail: userEmailInput.value,
      phone:phoneInput.value,
      name: nameInput.value,
      address: addressInput.value,
      employeeDate: employeeDateInput.value,
      resignationDate: resignationDateInput.value,
      role: roleInput.value,
    }

  let url = "/member/memberUpdate2";
  fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data)
    })
    .then((response) => response.json())
    .then((data) => {
          memberDetailBtnFn(null, data);

    }).catch((error) => {
      console.log(error);
    });
}


// 삭제
function memberDelete2BtnFn() {

    let data = {
      id: memberIdInput.value,
    }

  //event.preventDefault();
  let url = "/member/memberDelete2";
  fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data)
    })
    .then((response) => response.json())
    .then((data) => {
          location.href = location.href;
    }).catch((error) => {
      console.log(error);
    });
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


function hidePopup() {
    document.getElementById('memberDetail').style.display = 'none';
     location.href = location.href;

    }