// console.log("연결 확인");

const memberId = document.querySelector("#memberId");   // 아이디 input
const memberEmail = document.querySelector("#memberEmail"); // 이메일 input
const authBtn = document.querySelector("#auth"); // 이메일 인증번호 발급 버튼

const authNumber = document.querySelector("#authNumber"); // 인증번호 input
const checkBtn = document.querySelector("#check");  // 인증 버튼

const findBtn = document.querySelector("#find-btn"); // 찾기 버튼

const crossBtn = document.querySelector("#crossBtn");  // 모달창 close 버튼
const modal = document.querySelector("#modal"); // 모달창

const newPw = document.querySelector("#newPw"); // 새 비밀번호
const newPwCh = document.querySelector("#newPwCh"); // 새 비밀번호 확인

const changeBtn = document.querySelector("#changeBtn"); // 변경하기 버튼
const checkSpan = document.querySelector("#check_span"); // 유효성 검사 결과 span

const count = document.querySelector("#count"); // 카운트다운

let authTimer; // 타이머 역할을 할 setInterval을 저장할 변수


const initMin = 4; // 타이머 초기값 (분)
const initSec = 59; // 타이머 초기값 (초)
const initTime = "05:00";

// 실제 줄어드는 시간을 저장할 변수
let min = initMin;
let sec = initSec;

const checkObj = {
    "authBtn" : false, // 인증번호를 발급 받았는지 확인
    "authKey" : false,   // 인증번호가 맞는지 확인
    "authTime" : true
}


// 이메일 인증번호 발급 버튼 클릭 이벤트
authBtn.addEventListener("click",() => {

    // 재클릭시 처리
    checkObj.authKey = false;
    count.innerText = "";


    if(memberEmail.value == ""){
        alert("이메일을 입력해주세요");
        return;
    }

    // 클릭 시 타이머 숫자 초기화
    min = initMin;
    sec = initSec;

    // 이전 동작중인 인터벌 클리어
    clearInterval(authTimer);

    checkObj.authBtn = true;

    fetch("/member/sendEmail",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0){
            console.log("인증 번호 발송");
            authNumber.focus();
        }else{
            console.log("인증 번호 발송 실패");
        }

    });

    count.innerText = initTime; // 05:00 세팅
    count.classList.remove("confirm", "error"); // 검정색 글씨
    
    alert("인증번호가 발송되었습니다.");



    // 인증 시간 출력(1초 마다 동작)
    authTimer = setInterval( () => {

        count.innerText = `${addZero(min)}:${addZero(sec)}`;

        // 0분 0초인 경우 ("00:00" 출력 후)
        if(min == 0 && sec == 0) {
            checkObj.authTime = false; // 인증 못함
            clearInterval(authTimer); // interval 멈춤
            count.classList.add('error');
            count.classList.remove('confirm');
            return;
        }

        // 0초인 경우(0초를 출력한 후)
        if(sec == 0){
            sec = 60;
            min--;
        }

        sec--; // 1초 감소
        

    }, 1000); // 1초 지연시간


});


// 전달 받은 숫자가 10 미만인 경우(한자리) 앞에 0 붙여서 반환
function addZero(number) {
    if(number < 10 ) return "0" + number;
    else return number;
}


// 인증 버튼을 눌렀을 때
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

    if(!checkObj.authTime){
        alert("시간이 초과되었습니다.");
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


// 찾기 버튼을 눌렀을 때
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

    const member = {
        "memberId" : memberId.value ,
        "memberEmail" : memberEmail.value
    }

    fetch("/member/findResult",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(member)
    })
    .then(resp => resp.text())
    .then(result => {
        
        // 존재하는 회원 정보가 없을때
        if(result == 0){
            alert("존재하지 않는 회원 입니다.");
            return;
        }

        // 존재하는 회원 일 때
        modal.style.display = "block";



    })

});




crossBtn.addEventListener("click", () => {
    modal.style.display = "none";
});



const newPwCheck = {
    "pw" : false,
    "pwCh" : false
}

// 모달창에서 변경 버튼을 누를때
changeBtn.addEventListener("click", (e) => {


    if(newPw.value == ""){
        alert("비밀번호를 입력해주세요");
        newPw.focus();
        e.preventDefault();
        return;
    }

    if(newPwCh.value == ""){
        alert("비밀번호를 확인해주세요");
        newPwCh.focus();
        e.preventDefault();
        return;
    }

    if(!newPwCheck.pw || !newPwCheck.pwCh){
        alert("비밀번호를 확인해주세요");
        e.preventDefault();
        return;
    }

    const pwObj = {
        //아이디
        "memberId" : memberId.value ,
        //비밀번호
        "memberPw" : newPw.value
    }

    fetch("/member/findPwCh", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(pwObj)
    })
    .then(resp => resp.text())
    .then(result => {

        console.log(result);

        if(result == 0){
            alert("비밀번호 변경 오류...");
            return;
        }else if(result == -1){
            alert("기존과 같은 비밀번호 입니다...");
            return;
        }
        alert("비밀번호가 변경되었습니다.");
        location.href = "/member/login";
    });
    
});

// 비밀번호 유효성 검사
const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

newPw.addEventListener("keyup", () => {
    
    if(newPw.value == ""){
        checkSpan.innerText = "";
        newPwCheck.pw = false;
    }else{
        if(regExp.test(newPw.value)){
            checkSpan.innerText = "사용 가능한 비밀번호 입니다."
            newPwCheck.pw = true;
        }else{
            checkSpan.innerText = "사용 불가능한 비밀번호 입니다."
            newPwCheck.pw = false;
        }
    }

    

});


// 비밀번호를 입력 하지 않고 확인입력 할 때, 사용 불가능한 비밀번호를 입력하고 확인입력 할 때
newPwCh.addEventListener("focus", () => {
    
    if(newPw.value == ""){
        alert("비밀번호를 입력해주세요");
        newPw.focus();
        return;
    }

    if(!newPwCheck.pw){
        alert("사용 불가능한 비밀번호 입니다. 다시 입력해주세요");
        newPw.focus();
        return;
    }

});


// 비밀번호 확인 
newPwCh.addEventListener("keyup", () => {
    
    if(newPwCh.value == ""){
        checkSpan.innerText = "";
        newPwCheck.pwCh = false;
    }else{
        if(newPw.value == newPwCh.value){
            checkSpan.innerText = "비밀번호가 일치합니다."
            newPwCheck.pwCh = true;
        }else{
            checkSpan.innerText = "비밀번호가 일치하지 않습니다."
            newPwCheck.pwCh = false;
        }
    }
});