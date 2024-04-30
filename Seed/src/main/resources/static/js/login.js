console.log("연결 확인");

const memberId = document.querySelector("#memberId");   // 아이디
const memberPw = document.querySelector("#memberPw");   // 비밀번호
const loginBtn = document.querySelector("#loginBtn");   // 로그인 버튼

loginBtn.addEventListener("click", (e) => {
    if(memberId.value == ""){
        alert("아이디를 입력해주세요");
        e.preventDefault();
        return;
    }if(memberPw.value == ""){
        alert("비밀번호를 입력해주세요");
        e.preventDefault();
        return;
    }
});

