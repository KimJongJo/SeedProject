// console.log("연결 확인");

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

// 아이디 저장
const loginId = document.querySelector("#login-area input[name='memberId']");

// 로그인이 안된 상태일 때
if(loginId != null) {

    // 쿠키 key값이 saveId인 값 가져오기
    const saveId = getCookie("saveId");

    // saveId 값이 있을 경우
    if(saveId != undefined) {
        loginId.value = saveId; // 쿠키에서 얻어온 값을 input에 넣기

        // 아이디 저장 체크박스 체크 해두기
        document.querySelector("input[name='saveId']").checked = true;
    }

}