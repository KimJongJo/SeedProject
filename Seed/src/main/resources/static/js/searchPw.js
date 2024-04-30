// console.log("연결 확인");

const memberId = document.querySelector("#memberId");   // 아이디 input
const memberEmail = document.querySelector("#memberEmail"); // 이메일 input
const authBtn = document.querySelector("#auth"); // 이메일 인증번호 발급 버튼

const authNumber = document.querySelector("#authNumber"); // 인증번호 input
const checkBtn = document.querySelector("#check");  // 인증 버튼

const findBtn = document.querySelector("#find-btn"); // 찾기 버튼

const crossBtn = document.querySelector("#crossBtn");  // 모달창 close 버튼
const modal = document.querySelector("#modal"); // 모달창

const checkObj = {
    "authBtn" : false, // 인증번호를 발급 받았는지 확인
    "authKey" : false   // 인증번호가 맞는지 확인
}


// 이메일 인증번호 발급 버튼 클릭 이벤트
authBtn.addEventListener("click",() => {
    if(memberEmail.value == ""){
        alert("이메일을 입력해주세요");
        return;
    }

    checkObj.authBtn = true;

    fetch("/member/sendEmail",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0){
            alert("인증번호가 발송 되었습니다.");
            authNumber.focus();
        }else{
            alert("인증번호 발송 오류...");
        }

    });

});

checkBtn.addEventListener("click", () => {
    if(memberEmail.value == ""){
        alert("이메일을 입력해주세요");
        return;
    }
    if(authNumber.value == ""){
        alert("인증번호를 입력해주세요");
        return;
    }

    if(!checkObj.authBtn){
        alert("이메일을 입력하고 인증번호를 발급받으세요");
        return;
    }

    fetch("/member/authCheck", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : authNumber.value
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            alert("인증번호가 틀립니다.");
            return;
        }
        alert("인증되었습니다.");
        checkObj.authKey = true;
    });
});



findBtn.addEventListener("click", () => {
    if(memberId.value == ""){
        alert("아이디를 입력해주세요");
        return;
    }

    if(memberEmail.value == ""){
        alert("이메일을 입력해주세요");
        return;
    }

    if(authNumber.value == ""){
        alert("인증번호를 입력해주세요");
        return;
    }

    if(!checkObj.authKey){
        alert("인증번호를 확인 해주세요");
        return;
    }


    modal.style.display = "block";

    
});




crossBtn.addEventListener("click", () => {
    modal.style.display = "none";
});